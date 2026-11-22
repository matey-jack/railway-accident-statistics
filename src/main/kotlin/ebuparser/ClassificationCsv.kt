package ebuparser

import ebustats.aliases
import java.io.File
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

const val CLASSIFICATIONS_CSV = "$CLASSIFICATION_DIR/classifications.csv"

private const val LIST_SEPARATOR = "; "
private val TITLE_DATE_REGEX = Regex("""\d\d\.\d\d\.\d\d\d\d""")

// The model doesn't always keep to the title format, so every part may be missing.
fun parseReportTitle(title: String): ReportTitle {
    val parts = title.split(',').map { it.trim() }.filter { it.isNotEmpty() }
    val date = parts.lastOrNull()?.takeIf { TITLE_DATE_REGEX.matches(it) }
    val rest = if (date != null) parts.dropLast(1) else parts
    val eventType = rest.firstOrNull().orEmpty()
    return ReportTitle(
        eventType = aliases[eventType] ?: eventType,
        location = rest.drop(1).joinToString(", "),
        date = date ?: TITLE_DATE_REGEX.find(title)?.value.orEmpty(),
    )
}

private fun parseBooleanValue(value: Any?): Boolean =
    when (value) {
        is Boolean -> value
        is String -> value.trim().lowercase() in setOf("ja", "true", "x", "1")
        else -> false
    }

private fun parseOptionalStringValue(value: Any?): String? = value?.toString()?.trim()?.takeIf { it.isNotEmpty() }

private fun parseIntValue(value: Any?): Int? =
    when (value) {
        is Number -> value.toInt()
        // e.g. "1.200" or "ca. 30"
        else -> parseOptionalStringValue(value)?.filter(Char::isDigit)?.toIntOrNull()
    }

private fun parseListValue(value: Any?): List<String> =
    when (value) {
        is List<*> -> value.mapNotNull(::parseOptionalStringValue)
        else -> listOfNotNull(parseOptionalStringValue(value))
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

private fun parseFieldValue(
    type: KType,
    value: Any?,
): Any? =
    when (type.jvmErasure) {
        Boolean::class -> parseBooleanValue(value)
        Int::class -> parseIntValue(value)
        List::class -> parseListValue(value)
        RoadVehicleType::class -> parseRoadVehicleType(parseOptionalStringValue(value))
        else -> parseOptionalStringValue(value) ?: if (type.isMarkedNullable) null else ""
    }

fun parseClassification(response: Map<String, Any?>): Classification {
    val constructor = Classification::class.primaryConstructor!!
    val arguments =
        constructor.parameters.associateWith { parameter ->
            parseFieldValue(parameter.type, response[parameter.name])
        }
    return constructor.callBy(arguments)
}

private fun renderCsvValue(value: Any?): String =
    when (value) {
        null -> ""
        is Boolean -> if (value) "x" else ""
        is Enum<*> -> value.name
        is List<*> -> value.joinToString(LIST_SEPARATOR)
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

fun classificationsCsv(records: List<ClassificationRecord>): String {
    val propertiesByName = Classification::class.memberProperties.associateBy { it.name }

    val header =
        buildList {
            add("Bericht")
            add("Ereignisart")
            add("Ort")
            add("Datum")
            classificationFieldMetadata.forEach { field -> add(field.llmField.name) }
            add("Begründung")
            add("Modell")
        }

    val rows =
        records.map { record ->
            val title = parseReportTitle(record.title)
            val classification = parseClassification(record.response)
            buildList {
                add(record.report)
                add(title.eventType)
                add(title.location)
                add(title.date)
                classificationFieldMetadata.forEach { field ->
                    add(renderCsvValue(propertiesByName.getValue(field.propertyName).getter.call(classification)))
                }
                add(parseOptionalStringValue(record.response[REASONING_PROPERTY]).orEmpty())
                add(record.model)
            }
        }

    return (listOf(header) + rows)
        .joinToString("\n") { row -> row.joinToString(",") { value -> escapeCsv(value) } } + "\n"
}

fun main() {
    // When a report was classified more than once, the latest answer counts.
    val records =
        readClassificationRecords(CLASSIFICATIONS_JSONL)
            .associateBy { it.report }
            .values
            .sortedBy { it.report }
    File(CLASSIFICATIONS_CSV).writeText(classificationsCsv(records))
    println("Wrote ${records.size} classifications to $CLASSIFICATIONS_CSV")
}
