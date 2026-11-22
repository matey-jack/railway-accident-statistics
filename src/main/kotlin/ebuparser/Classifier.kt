package ebuparser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.openai.core.JsonValue
import com.openai.models.FunctionDefinition
import com.openai.models.FunctionParameters
import com.openai.models.chat.completions.ChatCompletionCreateParams
import com.openai.models.chat.completions.ChatCompletionNamedToolChoice
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import java.io.File
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

const val CLASSIFICATION_INPUT = "results/bedrock/summaries.md"
const val CLASSIFICATION_DIR = "results/classification"
const val CLASSIFICATIONS_JSONL = "$CLASSIFICATION_DIR/classifications.jsonl"

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

class Classifier(
    private val mantleClient: MantleClient,
) {
    val tool: FunctionDefinition =
        FunctionDefinition
            .builder()
            .name(TOOL_NAME)
            .description("Unfallkategorisierung")
            .parameters(
                FunctionParameters
                    .builder()
                    .putAdditionalProperty("type", JsonValue.from("object"))
                    .putAdditionalProperty("properties", JsonValue.from(buildToolSchemaProperties()))
                    .putAdditionalProperty("required", JsonValue.from(buildRequiredProperties()))
                    .build(),
            ).build()

    private val toolChoice =
        ChatCompletionNamedToolChoice
            .builder()
            .function(
                ChatCompletionNamedToolChoice.Function
                    .builder()
                    .name(TOOL_NAME)
                    .build(),
            ).build()

    fun classify(summary: Summary): ClassificationRecord {
        println("Classifying ${summary.filename}...")
        val params =
            ChatCompletionCreateParams
                .builder()
                .withBedrockSettings()
                .addSystemMessage(classificationPrompt)
                .addUserMessage(buildClassificationInput(summary))
                .addFunctionTool(tool)
                .toolChoice(toolChoice)
                .build()

        val chat = mantleClient.createChatCompletion(params)
        val arguments =
            chat
                .choices()
                .firstOrNull()
                ?.message()
                ?.toolCalls()
                ?.orElse(null)
                ?.firstNotNullOfOrNull { toolCall ->
                    toolCall
                        .function()
                        .orElse(null)
                        ?.function()
                        ?.takeIf { it.name() == TOOL_NAME }
                        ?.arguments()
                } ?: error("No $TOOL_NAME tool output for ${summary.filename}.")

        val response = jsonMapper.readValue<Map<String, Any?>>(arguments)
        println("Reasoning: ${response[REASONING_PROPERTY]}")
        println()
        return ClassificationRecord(
            report = summary.reportNumber,
            title = summary.title,
            model = BEDROCK_MODEL_ID,
            response = response,
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
    require(BEDROCK_MODEL.api == BedrockApi.MANTLE) {
        "The classifier only talks to the bedrock-mantle endpoint, but $BEDROCK_MODEL_ID uses ${BEDROCK_MODEL.api}."
    }

    val region = resolveAwsRegion()
    val credentialsProvider =
        ProfileCredentialsProvider
            .builder()
            .profileName(AWS_PROFILE)
            .build()

    val classifier = Classifier(MantleClient(region, credentialsProvider))
    println("INFO tool: ${classifier.tool}")

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
