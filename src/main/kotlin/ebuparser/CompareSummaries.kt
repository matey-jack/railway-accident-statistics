package ebuparser

import java.io.File

// The verbatim score can't show content that a run left out, so runs are also compared with each other:
// which sentences of one run the other run has in the same section, too.
private val COMPARED_IN_DETAIL = listOf("Hergang des Unfalls", "Ursachen des Unfalls")

private data class Run(
    val label: String,
    val summaries: Map<String, ScoredSummary>,
)

private fun labelOf(file: File) = file.nameWithoutExtension.removePrefix("summaries-")

private fun onlyInFirst(
    first: String?,
    second: String?,
): List<String> {
    val normalizedSecond = normalizeForComparison(second.orEmpty())
    return sentencesOf(first.orEmpty()).filterNot { normalizedSecond.contains(normalizeForComparison(it)) }
}

private fun percent(
    part: Int,
    whole: Int,
) = if (whole == 0) "–" else "${100 * part / whole}%"

private fun StringBuilder.overview(runs: List<Run>) {
    appendLine("## Overview\n")
    appendLine("| run | reports | sentences | verbatim | runaways | with missing sections | sections \"$NO_INFO\" |")
    appendLine("|---|---|---|---|---|---|---|")
    for (run in runs) {
        val assessments = run.summaries.values.map { it.assessment }
        val sentences = assessments.sumOf { it.sentenceCount }
        appendLine(
            "| ${run.label} | ${run.summaries.size} | $sentences | " +
                "${percent(assessments.sumOf { it.verbatimCount }, sentences)} | " +
                "${assessments.count { it.isRunaway }} | " +
                "${run.summaries.values.count { it.missingSections.isNotEmpty() }} | " +
                "${assessments.sumOf { it.sectionsWithoutInfo.size }} |",
        )
    }
    appendLine()
}

private fun StringBuilder.perReport(
    runs: List<Run>,
    reports: List<String>,
) {
    appendLine("## Per report\n")
    appendLine("Verbatim share of the sentences; R = runaway, −n = n sections missing.\n")
    appendLine("| report | " + runs.joinToString(" | ") { it.label } + " |")
    appendLine("|---|" + runs.joinToString("") { "---|" })
    for (report in reports) {
        val cells =
            runs.map { run ->
                val scored = run.summaries[report] ?: return@map "–"
                val a = scored.assessment
                buildString {
                    append("${a.verbatimPercent}% of ${a.sentenceCount}")
                    if (a.isRunaway) append(" R")
                    if (scored.missingSections.isNotEmpty()) append(" −${scored.missingSections.size}")
                }
            }
        appendLine("| $report | " + cells.joinToString(" | ") + " |")
    }
    appendLine()
}

private fun StringBuilder.pair(
    a: Run,
    b: Run,
) {
    val common = (a.summaries.keys intersect b.summaries.keys).sorted()
    appendLine("## ${a.label} vs. ${b.label}\n")
    appendLine("${common.size} reports in both. Sentences of one run that the other run has in the same section:\n")
    appendLine("| section | ${a.label} | also in ${b.label} | ${b.label} | also in ${a.label} |")
    appendLine("|---|---|---|---|---|")
    for (section in SUMMARY_SECTION_HEADINGS - TITLE_HEADING) {
        var countA = 0
        var countB = 0
        var onlyA = 0
        var onlyB = 0
        for (report in common) {
            val textA = a.summaries.getValue(report).sections[section]
            val textB = b.summaries.getValue(report).sections[section]
            countA += sentencesOf(textA.orEmpty()).size
            countB += sentencesOf(textB.orEmpty()).size
            onlyA += onlyInFirst(textA, textB).size
            onlyB += onlyInFirst(textB, textA).size
        }
        appendLine(
            "| $section | $countA | ${percent(countA - onlyA, countA)} | $countB | ${percent(countB - onlyB, countB)} |",
        )
    }
    appendLine()

    appendLine("### Sentences only in one run\n")
    for (report in common) {
        val differences =
            COMPARED_IN_DETAIL.mapNotNull { section ->
                val textA = a.summaries.getValue(report).sections[section]
                val textB = b.summaries.getValue(report).sections[section]
                val onlyA = onlyInFirst(textA, textB)
                val onlyB = onlyInFirst(textB, textA)
                if (onlyA.isEmpty() && onlyB.isEmpty()) return@mapNotNull null
                buildString {
                    appendLine("**$section**\n")
                    onlyA.forEach { appendLine("- only ${a.label}: $it") }
                    onlyB.forEach { appendLine("- only ${b.label}: $it") }
                }
            }
        if (differences.isEmpty()) continue
        appendLine("<details><summary>$report</summary>\n")
        differences.forEach { appendLine(it) }
        appendLine("</details>\n")
    }
}

// Arguments: two or more summary files. The comparison is written next to the first one.
fun main(args: Array<String>) {
    require(args.size >= 2) { "Give at least two summary files to compare." }
    val files = args.map(::File)
    val runs = files.map { Run(labelOf(it), scoreSummaryFile(it)) }
    val reports = runs.flatMap { it.summaries.keys }.distinct().sorted()

    val report =
        buildString {
            appendLine("# Comparison of summaries\n")
            files.forEach { appendLine("- ${labelOf(it)}: `${it.path}`") }
            appendLine()
            overview(runs)
            perReport(runs, reports)
            for (i in runs.indices) {
                for (j in i + 1 until runs.size) pair(runs[i], runs[j])
            }
        }

    val outputFile = File(files.first().parentFile, "comparison.md")
    outputFile.writeText(report)
    println("Comparison written to $outputFile")
}
