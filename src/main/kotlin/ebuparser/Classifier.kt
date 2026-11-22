package ebuparser

import com.anthropic.client.AnthropicClient
import com.anthropic.client.okhttp.AnthropicOkHttpClient
import com.anthropic.core.JsonValue
import com.anthropic.models.messages.MessageCreateParams
import com.anthropic.models.messages.OutputConfig
import com.anthropic.models.messages.ThinkingConfigAdaptive
import com.anthropic.models.messages.Tool
import com.fasterxml.jackson.core.type.TypeReference
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
import software.amazon.awssdk.services.bedrockruntime.model.ServiceTier
import software.amazon.awssdk.services.bedrockruntime.model.ServiceTierType
import software.amazon.awssdk.services.bedrockruntime.model.SystemContentBlock
import software.amazon.awssdk.services.bedrockruntime.model.ToolConfiguration
import software.amazon.awssdk.services.bedrockruntime.model.ToolInputSchema
import software.amazon.awssdk.services.bedrockruntime.model.ToolSpecification
import java.io.File
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

val CLASSIFICATION_INPUT = "${SUMMARIZATION_MODEL.resultsDir}/$OUTPUT_FILENAME"
const val CLASSIFICATION_DIR = "results/classification"
const val CLASSIFICATIONS_JSONL = "$CLASSIFICATION_DIR/classifications.jsonl"
const val CLASSIFICATION_COST_LOG = "$CLASSIFICATION_DIR/cost-log.txt"

val CLASSIFICATION_MODEL = novaModel

// The abbreviations are kept in the extraction prompt, which needs them as well.
private val classificationPrompt =
    File("src/main/resources/classification-prompt.txt")
        .readText()
        .replace("{{ABBREVIATIONS}}", markdownSection(summarizationPrompt, "# Abkürzungen in den Berichten"))

private const val TOOL_NAME = "classify"
private const val TOOL_DESCRIPTION = "Unfallkategorisierung"
internal const val REASONING_PROPERTY = "reasoning"

// The recommendations and measures were made after the accident, so they say nothing about the accident itself.
private val classificationInputSections =
    SectionCategory.entries - SectionCategory.TITLE - SectionCategory.RECOMMENDATIONS - SectionCategory.MEASURES_TAKEN

private val jsonMapper = jacksonObjectMapper()

// The section from its heading up to the next heading of the same or a higher level.
internal fun markdownSection(
    text: String,
    heading: String,
): String {
    val lines = text.lines()
    val start = lines.indexOfFirst { it.trim() == heading }
    require(start >= 0) { "No heading '$heading'." }
    val level = heading.takeWhile { it == '#' }.length
    val end =
        (start + 1 until lines.size).firstOrNull { index ->
            val hashes = lines[index].takeWhile { it == '#' }.length
            hashes in 1..level && lines[index].getOrNull(hashes) == ' '
        } ?: lines.size
    return lines.subList(start, end).joinToString("\n").trim()
}

// Strict tool use needs every property required and no additional ones,
// so "not known" is a value of its own: `unbekannt` in the lists and null for numbers.
internal fun objectSchema(
    kClass: KClass<*>,
    extraProperties: Map<String, Map<String, Any>> = emptyMap(),
): Map<String, Any> {
    val properties = extraProperties + llmFields(kClass).associate { it.propertyName to fieldSchema(it) }
    return mapOf(
        "type" to "object",
        "properties" to properties,
        "required" to properties.keys.toList(),
        "additionalProperties" to false,
    )
}

private fun fieldSchema(field: LlmFieldMeta): Map<String, Any> =
    typeSchema(field.type) + ("description" to "${field.llmField.name}: ${field.llmField.description}")

private fun typeSchema(type: KType): Map<String, Any> {
    val kClass = type.jvmErasure
    return when {
        kClass == Int::class -> mapOf("type" to listOf("integer", "null"))
        kClass == Double::class -> mapOf("type" to listOf("number", "null"))
        kClass == String::class -> mapOf("type" to "string")
        kClass == List::class -> mapOf("type" to "array", "items" to typeSchema(type.arguments.single().type!!))
        isLabeledEnum(kClass) -> mapOf("type" to "string", "enum" to labeledEntries(kClass).map { it.label })
        else -> objectSchema(kClass)
    }
}

internal val classificationSchema: Map<String, Any> =
    objectSchema(
        Classification::class,
        mapOf(REASONING_PROPERTY to mapOf("type" to "string", "description" to "Kurze Begründung der Einordnung.")),
    )

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

// Strict tool use needs `additionalProperties: false` at the top level as well, which InputSchema has no field for.
private fun toolInputSchema(schema: Map<String, Any>): Tool.InputSchema {
    @Suppress("UNCHECKED_CAST")
    val properties = schema.getValue("properties") as Map<String, Any>

    @Suppress("UNCHECKED_CAST")
    val required = schema.getValue("required") as List<String>
    return Tool.InputSchema
        .builder()
        .properties(
            Tool.InputSchema.Properties
                .builder()
                .putAllAdditionalProperties(properties.mapValues { (_, value) -> JsonValue.from(value) })
                .build(),
        ).required(required)
        .putAdditionalProperty("additionalProperties", JsonValue.from(schema.getValue("additionalProperties")))
        .build()
}

internal val anthropicTool: Tool =
    Tool
        .builder()
        .name(TOOL_NAME)
        .description(TOOL_DESCRIPTION)
        .inputSchema(toolInputSchema(classificationSchema))
        .strict(true)
        .build()

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

internal fun fromDocument(document: Document): Any? =
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

// Bedrock rejects the strict field for models that don't support it, such as Nova.
internal fun bedrockToolSpec(strict: Boolean): ToolSpecification =
    ToolSpecification
        .builder()
        .name(TOOL_NAME)
        .description(TOOL_DESCRIPTION)
        .inputSchema(ToolInputSchema.fromJson(toDocument(classificationSchema)))
        .apply { if (strict) strict(true) }
        .build()

// Claude on Bedrock takes the Anthropic API's settings, passed through Converse unchanged.
private val claudeRequestFields =
    toDocument(
        mapOf(
            "thinking" to mapOf("type" to "adaptive", "display" to "summarized"),
            "output_config" to mapOf("effort" to "medium"),
        ),
    )

private val LlmModel.isClaude: Boolean
    get() = name.startsWith("anthropic.")

class ModelAnswer(
    val toolInput: Map<String, Any?>?,
    val thinking: String,
    val stopReason: String?,
)

abstract class Classifier(
    protected val costLog: CostLog,
) : AutoCloseable {
    abstract val tool: Any

    // No forced tool choice: with a forced tool call, Claude skips thinking. The prompt asks for the tool instead.
    protected abstract fun ask(input: String): ModelAnswer

    fun classify(summary: Summary): ClassificationRecord {
        println("Classifying ${summary.filename}...")
        val answer = ask(buildClassificationInput(summary))
        val response =
            answer.toolInput
                ?: error("No $TOOL_NAME tool output for ${summary.filename}, stop reason ${answer.stopReason}.")
        println("Thinking: ${answer.thinking}")
        println("Reasoning: ${response[REASONING_PROPERTY]}")
        println()
        return ClassificationRecord(
            report = summary.reportNumber,
            title = summary.title,
            model = CLASSIFICATION_MODEL.inferenceId,
            response = response,
            thinking = answer.thinking,
        )
    }
}

class AnthropicClassifier(
    private val client: AnthropicClient,
    costLog: CostLog,
) : Classifier(costLog) {
    override val tool = anthropicTool

    override fun ask(input: String): ModelAnswer {
        val params =
            MessageCreateParams
                .builder()
                .model(CLASSIFICATION_MODEL.inferenceId)
                .maxTokens(MAX_TOKENS.toLong())
                .system(classificationPrompt)
                .addUserMessage(input)
                .addTool(tool)
                .thinking(ThinkingConfigAdaptive.builder().display(ThinkingConfigAdaptive.Display.SUMMARIZED).build())
                .outputConfig(OutputConfig.builder().effort(OutputConfig.Effort.MEDIUM).build())
                .build()

        val message = client.messages().create(params)
        message.usage().let { costLog.add(it.inputTokens(), it.outputTokens()) }
        val content = message.content()
        return ModelAnswer(
            toolInput =
                content
                    .firstNotNullOfOrNull { block ->
                        block
                            .toolUse()
                            .getOrNull()
                            ?.takeIf { it.name() == TOOL_NAME }
                            ?._input()
                    }?.convert(object : TypeReference<Map<String, Any?>>() {}),
            thinking = content.mapNotNull { it.thinking().getOrNull()?.thinking() }.joinToString("\n\n"),
            stopReason = message.stopReason().getOrNull()?.asString(),
        )
    }

    override fun close() = client.close()
}

class BedrockClassifier(
    private val client: BedrockRuntimeClient,
    costLog: CostLog,
) : Classifier(costLog) {
    override val tool = bedrockToolSpec(strict = CLASSIFICATION_MODEL.isClaude)

    private val toolConfig =
        ToolConfiguration
            .builder()
            .tools(
                software.amazon.awssdk.services.bedrockruntime.model.Tool
                    .fromToolSpec(tool),
            ).build()

    override fun ask(input: String): ModelAnswer {
        val request =
            ConverseRequest
                .builder()
                .modelId(CLASSIFICATION_MODEL.inferenceId)
                .system(SystemContentBlock.builder().text(classificationPrompt).build())
                .messages(
                    Message
                        .builder()
                        .role(ConversationRole.USER)
                        .content(ContentBlock.builder().text(input).build())
                        .build(),
                ).toolConfig(toolConfig)
                .inferenceConfig(
                    InferenceConfiguration
                        .builder()
                        .maxTokens(MAX_TOKENS)
                        .build(),
                ).apply {
                    // The price list entries are those of the tier each model is called with:
                    // Bedrock has no flex tier for Claude.
                    if (CLASSIFICATION_MODEL.isClaude) {
                        additionalModelRequestFields(claudeRequestFields)
                    } else {
                        additionalModelRequestFields(enableReasoning("medium"))
                        serviceTier(ServiceTier.builder().type(ServiceTierType.FLEX).build())
                    }
                }.build()

        val response = client.converse(request)
        response.usage()?.let { costLog.add(it.inputTokens().toLong(), it.outputTokens().toLong()) }
        val content = response.output().message().content()

        @Suppress("UNCHECKED_CAST")
        return ModelAnswer(
            toolInput =
                content
                    .firstNotNullOfOrNull { block -> block.toolUse()?.takeIf { it.name() == TOOL_NAME }?.input() }
                    ?.let { fromDocument(it) as Map<String, Any?> },
            thinking = content.mapNotNull { it.reasoningContent()?.reasoningText()?.text() }.joinToString("\n\n"),
            stopReason = response.stopReasonAsString(),
        )
    }

    override fun close() = client.close()
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
    val region = if (CLASSIFICATION_MODEL.api == LlmApi.ANTHROPIC) null else resolveAwsRegion()
    val costLog = CostLog(File(CLASSIFICATION_COST_LOG), printedTokenPrices(CLASSIFICATION_MODEL, region))
    costLog.start(CLASSIFICATION_MODEL)

    val classifier =
        when (CLASSIFICATION_MODEL.api) {
            LlmApi.ANTHROPIC -> AnthropicClassifier(AnthropicOkHttpClient.fromEnv(), costLog)
            LlmApi.CONVERSE ->
                BedrockClassifier(
                    converseClient(region!!, ProfileCredentialsProvider.builder().profileName(AWS_PROFILE).build()),
                    costLog,
                )
            LlmApi.MANTLE -> error("The classifier has no bedrock-mantle client, which ${CLASSIFICATION_MODEL.name} needs.")
        }
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
    costLog.writeTotals()
    classifier.close()
}
