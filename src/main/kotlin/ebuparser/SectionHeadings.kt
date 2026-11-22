package ebuparser

private val HEADING_REGEX = Regex("""^(#+)[ \t]+(.+?)[ \t]*$""", RegexOption.MULTILINE)

// The model occasionally misspells a prescribed heading (e.g. "Verlette", "Verletjekte", "Verhidung").
// The section headings differ from each other far more than this.
private const val MAX_HEADING_TYPOS = 3

private fun sectionHeadingOf(headingText: String): String? {
    val bare = headingText.trim('*', ' ', ':').lowercase()
    return SUMMARY_SECTION_HEADINGS.firstOrNull { editDistance(it.lowercase(), bare) <= MAX_HEADING_TYPOS }
}

// The parsers split summaries at H1 headings, but the model doesn't reliably keep the prescribed levels:
// it sometimes nests all sections under a heading copied from the report.
// So the known section headings become H1, and every other heading becomes at least H2.
fun normalizeSectionHeadings(
    text: String,
    onHeadingFix: (String) -> Unit = { println("Fixing heading: $it") },
): String =
    HEADING_REGEX.replace(text) { match ->
        val sectionHeading = sectionHeadingOf(match.groupValues[2])
        val replacement =
            if (sectionHeading != null) {
                "# $sectionHeading"
            } else {
                "#".repeat(maxOf(match.groupValues[1].length, 2)) + " " + match.groupValues[2]
            }
        if (replacement != match.value) onHeadingFix(match.value)
        replacement
    }

fun missingSectionHeadings(summary: String): List<String> {
    val present =
        HEADING_REGEX
            .findAll(summary)
            .filter { it.groupValues[1] == "#" }
            .map { it.groupValues[2] }
            .toSet()
    return SUMMARY_SECTION_HEADINGS - present
}
