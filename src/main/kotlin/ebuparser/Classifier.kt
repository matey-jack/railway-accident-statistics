package ebuparser

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.core.document.Document
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient
import software.amazon.awssdk.services.bedrockruntime.model.*
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

private fun buildFieldSchema(field: ClassificationFieldMeta): Document {
    val builder =
        Document
            .mapBuilder()
            .putString("title", field.llmField.name)
            .putString("description", field.llmField.description)
            .putString("type", "string")

    if (field.type.jvmErasure == Boolean::class) {
        builder.putList(
            "enum",
            listOf(
                Document.fromString("ja"),
                Document.fromString("nein"),
            ),
        )
    }

    val enumValues =
        field.type.jvmErasure.java
            .takeIf { it.isEnum }
            ?.enumConstants
            ?.map { (it as Enum<*>).name }
            .orEmpty()

    if (enumValues.isNotEmpty()) {
        builder.putList("enum", enumValues.map(Document::fromString))
    }

    return builder.build()
}

private fun buildToolSchemaProperties(): Map<String, Document> {
    val properties = mutableMapOf<String, Document>()
    properties["reasoning"] =
        Document
            .mapBuilder()
            .putString("type", "string")
            .putString("description", "Kurze Begründung der Kategorisierung.")
            .build()

    classificationFieldMetadata.forEach { field ->
        properties[field.propertyName] = buildFieldSchema(field)
    }

    return properties
}

private fun buildRequiredProperties(): List<Document> =
    buildList {
        add(Document.fromString("reasoning"))
        classificationFieldMetadata
            .filter { it.llmField.required }
            .forEach { add(Document.fromString(it.propertyName)) }
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

private fun parseBooleanValue(document: Document?): Boolean {
    if (document == null || document.isNull) {
        return false
    }

    if (document.isBoolean) {
        return document.asBoolean()
    }

    if (document.isString) {
        return when (document.asString().trim().lowercase()) {
            "ja", "true", "x", "1" -> true
            "nein", "false", "", "0" -> false
            else -> false
        }
    }

    return false
}

private fun parseOptionalStringValue(document: Document?): String? {
    if (document == null || document.isNull) {
        return null
    }

    val value =
        when {
            document.isString -> document.asString()
            else -> document.unwrap().toString()
        }.trim()

    return value.takeIf { it.isNotEmpty() }
}

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

private fun parseClassification(toolInput: Map<String, Document>): Classification {
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
    private val bedrockClient: BedrockRuntimeClient,
) {
    val toolConfig =
        ToolConfiguration
            .builder()
            .tools(
                Tool.fromToolSpec(
                    ToolSpecification
                        .builder()
                        .name("classify")
                        .description("Unfallkategorisierung")
                        .inputSchema(
                            ToolInputSchema.fromJson(
                                Document
                                    .mapBuilder()
                                    .putString("type", "object")
                                    .putMap("properties", buildToolSchemaProperties())
                                    .putList("required", buildRequiredProperties())
                                    .build(),
                            ),
                        ).build(),
                ),
            ).toolChoice(
                ToolChoice.fromTool(
                    SpecificToolChoice
                        .builder()
                        .name("classify")
                        .build(),
                ),
            ).build()

    fun classify(summary: Summary): Classification {
        println("Classifying ${summary.filename}...")
        val modelInput = buildClassificationInput(summary)
        val request =
            ConverseRequest
                .builder()
                .modelId(BEDROCK_INFERENCE_PROFILE)
                .system(SystemContentBlock.builder().text(classificationPrompt).build())
                .messages(
                    Message
                        .builder()
                        .role(ConversationRole.USER)
                        .content(ContentBlock.builder().text(modelInput).build())
                        .build(),
                ).inferenceConfig(
                    InferenceConfiguration
                        .builder()
                        .maxTokens(BEDROCK_MAX_TOKENS)
                        .build(),
                ).serviceTier(
                    ServiceTier
                        .builder()
                        .type(ServiceTierType.FLEX)
                        .build(),
                ).additionalModelRequestFields(enableReasoning("low"))
                .toolConfig(toolConfig)
                .build()

        val response = bedrockClient.converse(request)
        val toolInput =
            response
                .output()
                .message()
                .content()
                .firstNotNullOfOrNull { contentBlock ->
                    contentBlock.toolUse()?.takeIf { it.name() == "classify" }?.input()
                } ?: error("No classify tool output for ${summary.filename}.")

        return parseClassification(toolInput.asMap())
    }
}

fun enableReasoning(effort: String): Document =
    Document
        .mapBuilder()
        .putMap(
            "reasoningConfig",
            mapOf(
                "type" to Document.fromString("enabled"),
                "maxReasoningEffort" to Document.fromString(effort),
            ),
        ).build()

fun parseSummaries(path: String): List<Summary> {
    val normalizedContent = File(path).readText().replace("\r\n", "\n")
    return normalizedContent
        .split(SECTION_SEPARATOR)
        .map { section -> section.trim() }
        .filter { section -> section.isNotBlank() }
        .mapNotNull(::parseSummarySection)
}

fun main() {
    val identity =
        getCallerIdentityForProfile(AWS_PROFILE)
            ?: error("AWS profile '$AWS_PROFILE' is not ready.")
    printCallerIdentity(identity)

    val region = resolveAwsRegion()
    val credentialsProvider =
        ProfileCredentialsProvider
            .builder()
            .profileName(AWS_PROFILE)
            .build()

    BedrockRuntimeClient
        .builder()
        .region(region)
        .credentialsProvider(credentialsProvider)
        .build()
        .use { bedrockClient ->
            val classifier = Classifier(bedrockClient)
            println("INFO tool config: ${classifier.toolConfig}")

            val summaries = parseSummaries("results/bedrock/summaries.md")
            val classifications =
                summaries
                    .filter { summary -> testReports.contains(summary.filename.take(3)) }
                    .map { summary -> summary to classifier.classify(summary) }

            val outputPath = "results/bedrock/classifications.csv"
            writeClassificationsCsv(classifications, outputPath)
            println("Wrote ${classifications.size} classifications to $outputPath")
        }
}
