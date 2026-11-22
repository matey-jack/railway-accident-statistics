package ebuparser

import java.io.File

const val CLASSIFICATION_LABELS = "classification-labels.csv"

data class FieldAccuracy(
    val column: String,
    val compared: Int,
    val mismatches: List<String>,
) {
    val correct: Int
        get() = compared - mismatches.size
}

// Lists compare without regard to order; everything else ignores case and surrounding blanks.
private fun comparable(value: String): String =
    value
        .split(';')
        .map { it.trim().lowercase() }
        .filter { it.isNotEmpty() }
        .sorted()
        .joinToString("; ")

// An empty label cell means "not labelled", so a label file can cover any subset of reports and columns.
fun evaluate(
    labelsCsv: String,
    classificationsCsv: String,
): List<FieldAccuracy> {
    fun byReport(csv: String): Pair<List<String>, Map<String, Map<String, String>>> {
        val rows = parseCsv(csv)
        val header = rows.first()
        val reportIndex = header.indexOf("Bericht")
        require(reportIndex >= 0) { "No column 'Bericht'." }
        return header to rows.drop(1).associate { row -> row[reportIndex] to header.zip(row).toMap() }
    }

    val (labelHeader, labels) = byReport(labelsCsv)
    val (resultHeader, results) = byReport(classificationsCsv)
    (labelHeader - resultHeader.toSet()).forEach { println("WARN column '$it' is not in the classifications.") }
    (labels.keys - results.keys).forEach { println("WARN report $it is labelled but not classified.") }

    return (labelHeader - "Bericht").filter { it in resultHeader }.map { column ->
        val pairs =
            labels.mapNotNull { (report, labelRow) ->
                val label = labelRow[column]?.takeIf { it.isNotBlank() } ?: return@mapNotNull null
                val result = results[report] ?: return@mapNotNull null
                Triple(report, label, result[column].orEmpty())
            }
        FieldAccuracy(
            column = column,
            compared = pairs.size,
            mismatches =
                pairs
                    .filter { (_, label, result) -> comparable(label) != comparable(result) }
                    .map { (report, label, result) -> "$report: erwartet '$label', erhalten '$result'" },
        )
    }
}

fun main() {
    val labels = File(CLASSIFICATION_LABELS)
    require(labels.exists()) {
        "No $CLASSIFICATION_LABELS. Create this file with a column 'Bericht' and any of the columns of $CLASSIFICATIONS_CSV, with the expected values."
    }
    evaluate(labels.readText(), File(CLASSIFICATIONS_CSV).readText()).forEach { field ->
        println("${field.column}: ${field.correct} of ${field.compared} correct")
        field.mismatches.forEach { println("  $it") }
    }
}
