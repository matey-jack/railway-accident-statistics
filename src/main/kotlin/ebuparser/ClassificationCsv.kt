package ebuparser

import ebustats.aliases
import java.io.File
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

const val CLASSIFICATIONS_CSV = "$CLASSIFICATION_DIR/classifications.csv"

// One row per list entry, so that a pivot table can group by vehicle, signal box or factor.
val listTables: Map<KProperty1<Classification, List<*>>, String> =
    mapOf(
        Classification::factors to "$CLASSIFICATION_DIR/factors.csv",
        Classification::signalBoxes to "$CLASSIFICATION_DIR/signal-boxes.csv",
        Classification::railVehicles to "$CLASSIFICATION_DIR/rail-vehicles.csv",
    )

private const val LIST_SEPARATOR = "; "
private const val ITEM_SEPARATOR = " / "
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

// Everything the CSV files show about one report.
data class ClassifiedReport(
    val record: ClassificationRecord,
    val title: ReportTitle,
    val ebuEntry: EbuEntry?,
    val classification: Classification,
    val issues: List<String>,
)

fun classifyReport(
    record: ClassificationRecord,
    ebuEntries: List<EbuEntry>,
): ClassifiedReport {
    val title = parseReportTitle(record.title)
    val ebuMatch = findEbuEntry(ebuEntries, title)
    val parsed = parseClassification(record.response)
    return ClassifiedReport(record, title, ebuMatch.entry, parsed.classification, parsed.issues + listOfNotNull(ebuMatch.issue))
}

// 1 and 0, so that SUM and AVERAGE work directly; unknown stays empty, so that AVERAGE ignores it.
private fun renderValue(value: Any?): String =
    when (value) {
        null -> ""
        Tristate.YES -> "1"
        Tristate.NO -> "0"
        Tristate.UNKNOWN -> ""
        is Labeled -> value.label
        is Double -> if (value % 1.0 == 0.0) value.toLong().toString() else value.toString()
        else -> value.toString()
    }

private fun <T : Any> propertyValue(
    item: T,
    field: LlmFieldMeta,
): Any? =
    item::class
        .memberProperties
        .single { it.name == field.propertyName }
        .getter
        .call(item)

private fun renderListItem(item: Any): String =
    llmFields(item::class)
        .map { renderValue(propertyValue(item, it)) }
        .filter { it.isNotBlank() }
        .joinToString(ITEM_SEPARATOR)

private val reportColumns = listOf("Bericht", "Ereignisart", "Ort", "Datum")

private fun reportValues(report: ClassifiedReport): List<String> =
    listOf(
        report.record.report,
        report.ebuEntry?.eventType ?: report.title.eventType,
        report.ebuEntry?.location ?: report.title.location,
        report.title.date,
    )

// The cause columns summarise the factors, and factors.csv has them in full with their evidence.
private val summaryFields = llmFields(Classification::class).filter { it.propertyName != Classification::factors.name }

fun classificationsCsv(reports: List<ClassifiedReport>): String {
    val header =
        reportColumns + listOf("Streckennummer", "Streckenkilometer") +
            summaryFields.map { it.llmField.name } +
            "Sachschaden EUR" + causeColumns.map { it.name } +
            listOf("Begründung", "Prüfhinweise", "Modell")

    val rows =
        reports.map { report ->
            val classification = report.classification
            reportValues(report) +
                listOf(report.ebuEntry?.lineNumber.orEmpty(), report.ebuEntry?.kilometre.orEmpty()) +
                summaryFields.map { field ->
                    when (val value = propertyValue(classification, field)) {
                        is List<*> -> value.filterNotNull().joinToString(LIST_SEPARATOR, transform = ::renderListItem)
                        else -> renderValue(value)
                    }
                } +
                renderValue(propertyDamageEur(classification)) +
                causeColumns.map { renderValue(causeGrade(classification.factors, it)) } +
                listOf(
                    (report.record.response[REASONING_PROPERTY] as? String)?.trim().orEmpty(),
                    report.issues.joinToString(LIST_SEPARATOR),
                    report.record.model,
                )
        }
    return toCsv(listOf(header) + rows)
}

private fun isTwoLevel(field: LlmFieldMeta): Boolean =
    field.type.jvmErasure.java.enumConstants
        ?.firstOrNull() is TwoLevel

fun listTableCsv(
    reports: List<ClassifiedReport>,
    property: KProperty1<Classification, List<*>>,
): String {
    val itemClass =
        property.returnType.arguments
            .single()
            .type!!
            .jvmErasure
    val itemFields = llmFields(itemClass)
    val itemHeader =
        itemFields.flatMap { field ->
            val name = field.llmField.name
            if (isTwoLevel(field)) listOf("$name grob", name) else listOf(name)
        }
    val header = reportColumns + listOf("Tote", "Verletzte", "Sachschaden EUR") + itemHeader

    val rows =
        reports.flatMap { report ->
            val classification = report.classification
            val reportPart =
                reportValues(report) +
                    listOf(
                        renderValue(classification.deaths),
                        renderValue(classification.injured),
                        renderValue(propertyDamageEur(classification)),
                    )
            property.get(classification).filterNotNull().map { item ->
                reportPart +
                    itemFields.flatMap { field ->
                        val value = propertyValue(item, field)
                        if (isTwoLevel(field)) {
                            listOf((value as TwoLevel?)?.coarse.orEmpty(), renderValue(value))
                        } else {
                            listOf(renderValue(value))
                        }
                    }
            }
        }
    return toCsv(listOf(header) + rows)
}

fun main() {
    // When a report was classified more than once, the latest answer counts.
    val records =
        readClassificationRecords(CLASSIFICATIONS_JSONL)
            .associateBy { it.report }
            .values
            .sortedBy { it.report }
    val ebuEntries = readEbuList()
    val reports = records.map { classifyReport(it, ebuEntries) }

    reports.filter { it.issues.isNotEmpty() }.forEach { report ->
        println("${report.record.report}:")
        report.issues.forEach { println("  $it") }
    }

    File(CLASSIFICATIONS_CSV).writeText(classificationsCsv(reports))
    println("Wrote ${reports.size} classifications to $CLASSIFICATIONS_CSV")
    listTables.forEach { (property, path) ->
        File(path).writeText(listTableCsv(reports, property))
        println("Wrote $path")
    }
}
