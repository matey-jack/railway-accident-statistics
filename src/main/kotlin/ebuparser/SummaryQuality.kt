package ebuparser

// The PDF-to-Markdown conversion pads table columns to the widest cell, so a table line can have thousands of
// spaces or dashes. They cost input tokens, and the model copying such a table can get stuck emitting padding
// until it runs out of output tokens.
fun squeezeTablePadding(text: String): String =
    text.lines().joinToString("\n") { line ->
        if (line.trimStart().startsWith("|")) {
            line.replace(Regex(" {2,}"), " ").replace(Regex("-{4,}"), "---")
        } else {
            line
        }
    }

fun editDistance(
    a: String,
    b: String,
): Int {
    var previous = IntArray(b.length + 1) { it }
    for (i in 1..a.length) {
        val current = IntArray(b.length + 1)
        current[0] = i
        for (j in 1..b.length) {
            val substitution = previous[j - 1] + if (a[i - 1] == b[j - 1]) 0 else 1
            current[j] = minOf(substitution, previous[j] + 1, current[j - 1] + 1)
        }
        previous = current
    }
    return previous[b.length]
}

const val NO_INFO = "keine Angabe im Bericht"

private const val MIN_SENTENCE_LENGTH = 25
private val SENTENCE_BREAK = Regex("""(?<=[a-zäöüß)][.!?])\s+(?=[A-ZÄÖÜ])""")

// Markup and quote characters differ between report and summary without changing the wording;
// footnote markers are dropped because the model leaves them out.
fun normalizeForComparison(text: String): String =
    text
        .replace(Regex("<sup>.*?</sup>"), "")
        .replace(Regex("</?[a-zA-Z][^<>]{0,30}>"), " ")
        .replace(Regex("""[*_`»«„“”"']"""), "")
        .replace("­", "")
        .replace(Regex("""\s+"""), " ")
        .trim()
        .lowercase()

data class SummaryAssessment(
    val fileName: String,
    val sentenceCount: Int,
    val notVerbatim: List<String>,
    val sectionsWithoutInfo: List<String>,
    val longestOutputRepeat: Int,
    val longestInputRepeat: Int,
) {
    val verbatimCount get() = sentenceCount - notVerbatim.size
    val verbatimPercent get() = if (sentenceCount == 0) 100 else 100 * verbatimCount / sentenceCount

    // A verbatim copy can't repeat a character more often in a row than the original report does,
    // so this means the model got stuck emitting padding.
    // Line length would be no criterion, because some models join paragraphs into one line.
    // The minimum keeps the headings, which aren't from the report, from counting.
    val isRunaway get() = longestOutputRepeat > maxOf(longestInputRepeat, 10)

    fun logLines(): List<String> =
        buildList {
            add("Assessment $fileName: $verbatimCount/$sentenceCount sentences verbatim ($verbatimPercent%)")
            notVerbatim.forEach { add("  not verbatim: ${it.take(200)}") }
            if (sectionsWithoutInfo.isNotEmpty()) add("  $NO_INFO: $sectionsWithoutInfo")
            if (isRunaway) {
                add(
                    "  WARNING: the output repeats a character $longestOutputRepeat times in a row, " +
                        "the input at most $longestInputRepeat times",
                )
            }
        }
}

// Everything but the title is supposed to be copied verbatim from the report.
// Tables are left out, because the model may legitimately pick single rows from them.
fun assessSummary(
    fileName: String,
    summary: String,
    report: String,
): SummaryAssessment {
    val sections = summarySections(summary)
    val sentences = sections.filterKeys { it != TITLE_HEADING }.values.flatMap(::sentencesOf)
    val normalizedReport = normalizeForComparison(report)
    return SummaryAssessment(
        fileName = fileName,
        sentenceCount = sentences.size,
        notVerbatim = sentences.filterNot { normalizedReport.contains(normalizeForComparison(it)) },
        sectionsWithoutInfo = sections.filterValues { it.trim() == NO_INFO }.keys.toList(),
        longestOutputRepeat = longestRepeat(summary),
        longestInputRepeat = longestRepeat(report),
    )
}

private fun longestRepeat(text: String): Int {
    var longest = 0
    var current = 0
    text.forEachIndexed { i, c ->
        current = if (i > 0 && text[i - 1] == c) current + 1 else 1
        longest = maxOf(longest, current)
    }
    return longest
}

fun sentencesOf(sectionText: String): List<String> =
    sectionText
        .lines()
        .map { it.trim().replace(Regex("""^[-•*]\s+"""), "") }
        .filter { it.isNotEmpty() && !it.startsWith("#") && !it.startsWith("|") && it != NO_INFO }
        .flatMap { it.split(SENTENCE_BREAK) }
        .filter { it.length >= MIN_SENTENCE_LENGTH }

fun summarySections(summary: String): Map<String, String> {
    val sections = linkedMapOf<String, StringBuilder>()
    var current: StringBuilder? = null
    for (line in summary.lines()) {
        if (line.startsWith("# ")) {
            current = StringBuilder().also { sections[line.removePrefix("# ").trim()] = it }
        } else {
            current?.appendLine(line)
        }
    }
    return sections.mapValues { it.value.toString() }
}
