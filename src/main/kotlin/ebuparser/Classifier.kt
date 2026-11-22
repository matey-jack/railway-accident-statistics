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
import java.io.File
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

val CLASSIFICATION_INPUT = "${SUMMARIZATION_MODEL.resultsDir}/$OUTPUT_FILENAME"
const val CLASSIFICATION_DIR = "results/classification"
const val CLASSIFICATIONS_JSONL = "$CLASSIFICATION_DIR/classifications.jsonl"
const val CLASSIFICATION_COST_LOG = "$CLASSIFICATION_DIR/cost-log.txt"

val CLASSIFICATION_MODEL = haiku55Model

// The abbreviations are kept in the extraction prompt, which needs them as well.
private val classificationPrompt =
    File("src/main/resources/classification-prompt.txt")
        .readText()
        .replace("{{ABBREVIATIONS}}", markdownSection(summarizationPrompt, "# Abkürzungen in den Berichten"))

private const val TOOL_NAME = "classify"
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

class Classifier(
    private val client: AnthropicClient,
    private val costLog: CostLog,
) {
    val tool: Tool =
        Tool
            .builder()
            .name(TOOL_NAME)
            .description("Unfallkategorisierung")
            .inputSchema(toolInputSchema(classificationSchema))
            .strict(true)
            .build()

    fun classify(summary: Summary): ClassificationRecord {
        println("Classifying ${summary.filename}...")
        // No forced tool choice: with a forced tool call, Claude skips thinking. The prompt asks for the tool instead.
        val params =
            MessageCreateParams
                .builder()
                .model(CLASSIFICATION_MODEL.inferenceId)
                .maxTokens(MAX_TOKENS.toLong())
                .system(classificationPrompt)
                .addUserMessage(buildClassificationInput(summary))
                .addTool(tool)
                .thinking(ThinkingConfigAdaptive.builder().display(ThinkingConfigAdaptive.Display.SUMMARIZED).build())
                .outputConfig(OutputConfig.builder().effort(OutputConfig.Effort.MEDIUM).build())
                .build()

        val message = client.messages().create(params)
        message.usage().let { costLog.add(it.inputTokens(), it.outputTokens()) }
        val content = message.content()
        val thinking = content.mapNotNull { it.thinking().getOrNull()?.thinking() }.joinToString("\n\n")
        val toolInput =
            content.firstNotNullOfOrNull { block ->
                block
                    .toolUse()
                    .getOrNull()
                    ?.takeIf { it.name() == TOOL_NAME }
                    ?._input()
            }
                ?: error(
                    "No $TOOL_NAME tool output for ${summary.filename}, " +
                        "stop reason ${message.stopReason().getOrNull()?.asString()}.",
                )

        val response = toolInput.convert(object : TypeReference<Map<String, Any?>>() {})!!
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
    require(CLASSIFICATION_MODEL.api == LlmApi.ANTHROPIC) {
        "The classifier only uses the Anthropic API, but ${CLASSIFICATION_MODEL.name} uses ${CLASSIFICATION_MODEL.api}."
    }

    val costLog = CostLog(File(CLASSIFICATION_COST_LOG), printedTokenPrices(CLASSIFICATION_MODEL, null))
    costLog.start(CLASSIFICATION_MODEL)
    val client = AnthropicOkHttpClient.fromEnv()

    val classifier = Classifier(client, costLog)
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
    client.close()
}
