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
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

private val SUMMARY_DATE_REGEX = Regex("""\d\d\.\d\d\.\d\d\d\d""")

private data class ClassificationFieldMeta(
    val propertyName: String,
    val type: KType,
    val llmField: LlmField,
)

private val classificationPrompt = File("src/main/resources/classification-prompt.txt").readText()

private val classificationFieldMetadata: List<ClassificationFieldMeta> =
    Classification::class.primaryConstructor!!.parameters.mapNotNull { parameter ->
        val llmField = parameter.findAnnotation<LlmField>() ?: return@mapNotNull null
        ClassificationFieldMeta(
            propertyName = parameter.name!!,
            type = parameter.type,
            llmField = llmField,
        )
    }

private const val TOOL_NAME = "classify"

private fun buildFieldSchema(field: ClassificationFieldMeta): Map<String, Any> =
    buildMap {
        put("title", field.llmField.name)
        put("description", field.llmField.description)
        put("type", "string")

        if (field.type.jvmErasure == Boolean::class) {
            put("enum", listOf("ja", "nein"))
        }

        val enumValues =
            field.type.jvmErasure.java
                .takeIf { it.isEnum }
                ?.enumConstants
                ?.map { (it as Enum<*>).name }
                .orEmpty()

        if (enumValues.isNotEmpty()) {
            put("enum", enumValues)
        }
    }

private fun buildToolSchemaProperties(): Map<String, Map<String, Any>> =
    buildMap {
        put(
            "reasoning",
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
        add("reasoning")
        classificationFieldMetadata
            .filter { it.llmField.required }
            .forEach { add(it.propertyName) }
    }

private fun categoryFromHeading(heading: String): SectionCategory {
    val normalized = heading.lowercase()
    return when {
        normalized.contains("hergang") -> SectionCategory.WHAT_HAPPENED
        normalized.contains("ursachen") -> SectionCategory.CAUSES
        normalized.contains("schaden") -> SectionCategory.DAMAGES
        else -> SectionCategory.MEASURES
    }
}

private fun parseSummarySection(rawSection: String): Summary? {
    val lines = normalizeSectionHeadings(rawSection).lines()
    val fileLine = lines.firstOrNull { it.startsWith("file:") } ?: return null
    val filename = fileLine.substringAfter(':').trim()
    val date = SUMMARY_DATE_REGEX.find(rawSection)?.value.orEmpty()

    val parsedSections = mutableListOf<Section>()
    var currentHeading: String? = null
    val currentBody = mutableListOf<String>()

    fun flushCurrentSection() {
        val heading = currentHeading ?: return
        parsedSections +=
            Section(
                title = heading,
                category = categoryFromHeading(heading),
                text = currentBody.joinToString("\n").trim(),
            )
        currentBody.clear()
    }

    lines.forEach { line ->
        val trimmed = line.trim()
        if (trimmed.startsWith("# ")) {
            flushCurrentSection()
            currentHeading = trimmed.removePrefix("# ").trim()
        } else if (currentHeading != null) {
            currentBody += line
        }
    }
    flushCurrentSection()

    val title =
        parsedSections
            .firstOrNull { it.title == TITLE_HEADING }
            ?.text
            ?.lines()
            ?.map { it.trim('#', '*', ' ') }
            ?.firstOrNull { it.isNotEmpty() }
            .orEmpty()

    return Summary(filename = filename, date = date, title = title, sections = parsedSections)
}

private fun buildClassificationInput(summary: Summary): String {
    val relevantSections =
        summary.sections
            .filter { section ->
                section.category == SectionCategory.WHAT_HAPPENED ||
                    section.category == SectionCategory.CAUSES
            }.ifEmpty { summary.sections }

    val sectionsText =
        relevantSections.joinToString("\n\n") { section ->
            "# ${section.title}\n\n${section.text.trim()}"
        }

    return listOf(summary.title.trim(), sectionsText.trim())
        .filter { it.isNotBlank() }
        .joinToString("\n\n")
}

private fun parseBooleanValue(value: Any?): Boolean =
    when (value) {
        is Boolean -> value
        is String -> value.trim().lowercase() in setOf("ja", "true", "x", "1")
        else -> false
    }

private fun parseOptionalStringValue(value: Any?): String? = value?.toString()?.trim()?.takeIf { it.isNotEmpty() }

private fun parseRoadVehicleType(value: String?): RoadVehicleType? {
    if (value.isNullOrBlank()) {
        return null
    }

    RoadVehicleType.entries
        .firstOrNull { entry ->
            entry.name.equals(value, ignoreCase = true)
        }?.let { return it }

    val normalized = value.lowercase()
    return when {
        normalized.contains("pkw") || normalized.contains("car") || normalized.contains("auto") -> RoadVehicleType.CAR
        normalized.contains("lkw") || normalized.contains("truck") || normalized.contains("liefer") -> RoadVehicleType.TRUCK
        normalized.contains("bus") -> RoadVehicleType.BUS
        else -> RoadVehicleType.OTHER
    }
}

private fun parseClassification(toolInput: Map<String, Any?>): Classification {
    println("Reasoning: ${toolInput["reasoning"]}")
    println()
    return Classification(
        railwayCrossing = parseBooleanValue(toolInput["railwayCrossing"]),
        roadVehicleType = parseRoadVehicleType(parseOptionalStringValue(toolInput["roadVehicleType"])),
        constructionWork = parseBooleanValue(toolInput["constructionWork"]),
        degradedMode = parseBooleanValue(toolInput["degradedMode"]),
        naturalDisaster = parseBooleanValue(toolInput["naturalDisaster"]),
        signallerError = parseBooleanValue(toolInput["signallerError"]),
        driverError = parseBooleanValue(toolInput["driverError"]),
        infrastructureDeficiency = parseBooleanValue(toolInput["infrastructureDeficiency"]),
        infrastructureDeficiencyDescription = parseOptionalStringValue(toolInput["infrastructureDeficiencyDescription"]).orEmpty(),
        vehicleDeficiency = parseBooleanValue(toolInput["vehicleDeficiency"]),
        vehicleDeficiencyDescription = parseOptionalStringValue(toolInput["vehicleDeficiencyDescription"]).orEmpty(),
        insufficientBrakes = parseBooleanValue(toolInput["insufficientBrakes"]),
        fire = parseBooleanValue(toolInput["fire"]),
        collision = parseBooleanValue(toolInput["collision"]),
        otherCause = parseBooleanValue(toolInput["otherCause"]),
    )
}

private fun classificationValueByProperty(classification: Classification): Map<String, Any?> {
    val propertiesByName = Classification::class.memberProperties.associateBy { it.name }
    return classificationFieldMetadata.associate { field ->
        field.propertyName to propertiesByName.getValue(field.propertyName).getter.call(classification)
    }
}

private fun renderCsvValue(value: Any?): String =
    when (value) {
        null -> ""
        is Boolean -> if (value) "x" else ""
        is Enum<*> -> value.name
        else -> value.toString()
    }

private fun escapeCsv(value: String): String {
    val escaped = value.replace("\"", "\"\"")
    return if (escaped.contains(',') || escaped.contains('"') || escaped.contains('\n')) {
        "\"$escaped\""
    } else {
        escaped
    }
}

private fun writeClassificationsCsv(
    classifications: List<Pair<Summary, Classification>>,
    outputPath: String,
) {
    val outputFile = File(outputPath)
    outputFile.parentFile?.mkdirs()

    val header =
        buildList {
            add("Bericht")
            add("Datum")
            classificationFieldMetadata.forEach { field -> add(field.llmField.name) }
        }

    val rows =
        classifications.map { (summary, classification) ->
            val valueByProperty = classificationValueByProperty(classification)

            buildList {
                add(summary.filename.substringBeforeLast('.'))
                add(summary.date)
                classificationFieldMetadata.forEach { field ->
                    add(renderCsvValue(valueByProperty[field.propertyName]))
                }
            }
        }

    val csv =
        (listOf(header) + rows)
            .joinToString("\n") { row -> row.joinToString(",") { value -> escapeCsv(value) } } + "\n"

    outputFile.writeText(csv)
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

    fun classify(summary: Summary): Classification {
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

        return parseClassification(jacksonObjectMapper().readValue<Map<String, Any?>>(arguments))
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

    val summaries = parseSummaries("results/bedrock/summaries.md")
    val classifications =
        summaries
            .filter { summary -> testReports.contains(summary.filename.take(3)) }
            .map { summary -> summary to classifier.classify(summary) }

    val outputPath = "results/bedrock/classifications.csv"
    writeClassificationsCsv(classifications, outputPath)
    println("Wrote ${classifications.size} classifications to $outputPath")
}
