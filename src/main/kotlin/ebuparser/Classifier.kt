package ebuparser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.core.document.Document
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest
import software.amazon.awssdk.services.bedrockruntime.model.InferenceConfiguration
import software.amazon.awssdk.services.bedrockruntime.model.Message
import software.amazon.awssdk.services.bedrockruntime.model.SystemContentBlock
import software.amazon.awssdk.services.bedrockruntime.model.Tool
import software.amazon.awssdk.services.bedrockruntime.model.ToolConfiguration
import software.amazon.awssdk.services.bedrockruntime.model.ToolInputSchema
import software.amazon.awssdk.services.bedrockruntime.model.ToolSpecification
import java.io.File
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

const val CLASSIFICATION_INPUT = "results/bedrock/summaries.md"
const val CLASSIFICATION_DIR = "results/classification"
const val CLASSIFICATIONS_JSONL = "$CLASSIFICATION_DIR/classifications.jsonl"

val CLASSIFICATION_MODEL = haiku55Model

internal data class ClassificationFieldMeta(
    val propertyName: String,
    val type: KType,
    val llmField: LlmField,
)

private val classificationPrompt = File("src/main/resources/classification-prompt.txt").readText()

internal val classificationFieldMetadata: List<ClassificationFieldMeta> =
    Classification::class.primaryConstructor!!.parameters.mapNotNull { parameter ->
        val llmField = parameter.findAnnotation<LlmField>() ?: return@mapNotNull null
        ClassificationFieldMeta(
            propertyName = parameter.name!!,
            type = parameter.type,
            llmField = llmField,
        )
    }

private const val TOOL_NAME = "classify"
internal const val REASONING_PROPERTY = "reasoning"

// The recommendations and measures were made after the accident, so they say nothing about the accident itself.
private val classificationInputSections =
    SectionCategory.entries - SectionCategory.TITLE - SectionCategory.RECOMMENDATIONS - SectionCategory.MEASURES_TAKEN

private val jsonMapper = jacksonObjectMapper()

private fun buildFieldSchema(field: ClassificationFieldMeta): Map<String, Any> =
    buildMap {
        put("title", field.llmField.name)
        put("description", field.llmField.description)

        when (field.type.jvmErasure) {
            Boolean::class -> {
                put("type", "string")
                put("enum", listOf("ja", "nein"))
            }

            Int::class -> {
                put("type", "integer")
            }

            List::class -> {
                put("type", "array")
                put("items", mapOf("type" to "string"))
            }

            else -> {
                put("type", "string")
                val enumConstants = field.type.jvmErasure.java.enumConstants
                if (enumConstants != null) {
                    put("enum", enumConstants.map { (it as Enum<*>).name })
                }
            }
        }
    }

private fun buildToolSchemaProperties(): Map<String, Map<String, Any>> =
    buildMap {
        put(
            REASONING_PROPERTY,
            mapOf(
                "type" to "string",
                "description" to "Kurze Begründung der Kategorisierung.",
            ),
        )
        classificationFieldMetadata.forEach { field ->
            put(field.propertyName, buildFieldSchema(field))
        }
    }

private fun buildRequiredProperties(): List<String> =
    buildList {
        add(REASONING_PROPERTY)
        classificationFieldMetadata
            .filter { it.llmField.required }
            .forEach { add(it.propertyName) }
    }

private fun parseSummarySection(rawSection: String): Summary? {
    val lines = normalizeSectionHeadings(rawSection).lines()
    val fileLine = lines.firstOrNull { it.startsWith("file:") } ?: return null
    val filename = fileLine.substringAfter(':').trim()

    val parsedSections = mutableListOf<Section>()
    var currentCategory: SectionCategory? = null
    val currentBody = mutableListOf<String>()

    fun flushCurrentSection() {
        val category = currentCategory ?: return
        parsedSections += Section(category = category, text = currentBody.joinToString("\n").trim())
        currentBody.clear()
    }

    lines.forEach { line ->
        val trimmed = line.trim()
        if (trimmed.startsWith("# ")) {
            flushCurrentSection()
            val heading = trimmed.removePrefix("# ").trim()
            // normalizeSectionHeadings has turned every other H1 heading into a subheading.
            currentCategory = SectionCategory.entries.single { it.heading == heading }
        } else if (currentCategory != null) {
            currentBody += line
        }
    }
    flushCurrentSection()

    val title =
        parsedSections
            .firstOrNull { it.category == SectionCategory.TITLE }
            ?.text
            ?.lines()
            ?.map { it.trim('#', '*', ' ') }
            ?.firstOrNull { it.isNotEmpty() }
            .orEmpty()

    return Summary(filename = filename, title = title, sections = parsedSections)
}

private fun buildClassificationInput(summary: Summary): String {
    val sectionsText =
        summary.sections
            .filter { it.category in classificationInputSections }
            .joinToString("\n\n") { section -> "# ${section.title}\n\n${section.text}" }

    return listOf(summary.title, sectionsText)
        .filter { it.isNotBlank() }
        .joinToString("\n\n")
}

private fun toDocument(value: Any?): Document =
    when (value) {
        null -> Document.fromNull()
        is String -> Document.fromString(value)
        is Boolean -> Document.fromBoolean(value)
        is Int -> Document.fromNumber(value)
        is List<*> -> Document.fromList(value.map(::toDocument))
        is Map<*, *> -> Document.fromMap(value.entries.associate { (key, item) -> key as String to toDocument(item) })
        else -> error("No Document conversion for ${value::class}.")
    }

private fun fromDocument(document: Document): Any? =
    when {
        document.isNull() -> null
        document.isString() -> document.asString()
        document.isBoolean() -> document.asBoolean()
        document.isNumber() -> {
            val number = document.asNumber().bigDecimalValue()
            if (number.stripTrailingZeros().scale() <= 0) number.longValueExact() else number.toDouble()
        }
        document.isList() -> document.asList().map(::fromDocument)
        document.isMap() -> document.asMap().mapValues { (_, item) -> fromDocument(item) }
        else -> error("Unknown Document type: $document")
    }

// Claude-specific settings, passed through Converse unchanged.
private val claudeRequestFields =
    toDocument(
        mapOf(
            "thinking" to mapOf("type" to "adaptive", "display" to "summarized"),
            "output_config" to mapOf("effort" to "medium"),
        ),
    )

class Classifier(
    private val bedrockClient: BedrockRuntimeClient,
) {
    val toolSpec: ToolSpecification =
        ToolSpecification
            .builder()
            .name(TOOL_NAME)
            .description("Unfallkategorisierung")
            .inputSchema(
                ToolInputSchema.fromJson(
                    toDocument(
                        mapOf(
                            "type" to "object",
                            "properties" to buildToolSchemaProperties(),
                            "required" to buildRequiredProperties(),
                        ),
                    ),
                ),
            ).build()

    // No forced tool choice: with a forced tool call, Claude skips thinking. The prompt asks for the tool instead.
    private val toolConfig =
        ToolConfiguration
            .builder()
            .tools(Tool.fromToolSpec(toolSpec))
            .build()

    fun classify(summary: Summary): ClassificationRecord {
        println("Classifying ${summary.filename}...")
        val request =
            ConverseRequest
                .builder()
                .modelId(CLASSIFICATION_MODEL.inferenceId)
                .system(SystemContentBlock.builder().text(classificationPrompt).build())
                .messages(
                    Message
                        .builder()
                        .role(ConversationRole.USER)
                        .content(ContentBlock.builder().text(buildClassificationInput(summary)).build())
                        .build(),
                ).toolConfig(toolConfig)
                .inferenceConfig(
                    InferenceConfiguration
                        .builder()
                        .maxTokens(BEDROCK_MAX_TOKENS)
                        .build(),
                ).additionalModelRequestFields(claudeRequestFields)
                .build()

        val converseResponse = bedrockClient.converse(request)
        val content = converseResponse.output().message().content()
        val thinking = content.mapNotNull { it.reasoningContent()?.reasoningText()?.text() }.joinToString("\n\n")
        val toolInput =
            content.firstNotNullOfOrNull { block -> block.toolUse()?.takeIf { it.name() == TOOL_NAME }?.input() }
                ?: error(
                    "No $TOOL_NAME tool output for ${summary.filename}, " +
                        "stop reason ${converseResponse.stopReasonAsString()}.",
                )

        @Suppress("UNCHECKED_CAST")
        val response = fromDocument(toolInput) as Map<String, Any?>
        println("Thinking: $thinking")
        println("Reasoning: ${response[REASONING_PROPERTY]}")
        println()
        return ClassificationRecord(
            report = summary.reportNumber,
            title = summary.title,
            model = CLASSIFICATION_MODEL.inferenceId,
            response = response,
            thinking = thinking,
        )
    }
}

fun parseSummaries(path: String): List<Summary> {
    val normalizedContent = File(path).readText().replace("\r\n", "\n")
    return normalizedContent
        .split(SECTION_SEPARATOR)
        .map { section -> section.trim() }
        .filter { section -> section.isNotBlank() }
        .mapNotNull(::parseSummarySection)
}

fun readClassificationRecords(path: String): List<ClassificationRecord> {
    val file = File(path)
    if (!file.exists()) return emptyList()
    return file
        .readLines()
        .filter { it.isNotBlank() }
        .map { jsonMapper.readValue<ClassificationRecord>(it) }
}

fun appendClassificationRecord(
    record: ClassificationRecord,
    path: String,
) {
    val file = File(path)
    file.parentFile?.mkdirs()
    file.appendText(jsonMapper.writeValueAsString(record) + "\n")
}

fun main() {
    require(CLASSIFICATION_MODEL.api == BedrockApi.CONVERSE) {
        "The classifier only uses the Converse API, but ${CLASSIFICATION_MODEL.name} uses ${CLASSIFICATION_MODEL.api}."
    }

    val bedrockClient =
        converseClient(resolveAwsRegion(), ProfileCredentialsProvider.builder().profileName(AWS_PROFILE).build())

    val classifier = Classifier(bedrockClient)
    println("INFO tool: ${classifier.toolSpec}")

    val alreadyClassified = readClassificationRecords(CLASSIFICATIONS_JSONL).map { it.report }.toSet()
    val pending =
        parseSummaries(CLASSIFICATION_INPUT)
            .filter { summary -> testReports.contains(summary.reportNumber.take(3)) }
            .filterNot { summary -> summary.reportNumber in alreadyClassified }
    println("${alreadyClassified.size} reports already in $CLASSIFICATIONS_JSONL, ${pending.size} to classify.")

    // A failed report is just skipped: it is retried on the next run, like the reports never attempted.
    var failures = 0
    pending.forEach { summary ->
        try {
            appendClassificationRecord(classifier.classify(summary), CLASSIFICATIONS_JSONL)
        } catch (e: Exception) {
            failures++
            println("ERROR classifying ${summary.filename}: $e")
        }
    }
    println("Classified ${pending.size - failures} reports, $failures failed.")
}
