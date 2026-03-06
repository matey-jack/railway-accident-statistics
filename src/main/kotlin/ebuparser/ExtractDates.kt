package ebuparser

import java.io.File

private const val SECTION_SEPARATOR = "\n---\n"
private const val INPUT_FILE = "results/bedrock/summaries.md"
private const val OUTPUT_FILE = "results/dates.csv"
private val DATE_REGEX = Regex("""\d\d\.\d\d\.\d\d\d\d""")

object ExtractDates {
    fun run(
        inputPath: String = INPUT_FILE,
        outputPath: String = OUTPUT_FILE,
    ) {
        val input = File(inputPath)
        val output = File(outputPath)

        val normalizedContent = input.readText().replace("\r\n", "\n")
        val sections =
            normalizedContent
                .split(SECTION_SEPARATOR)
                .map { it.trim() }
                .filter { it.isNotBlank() }

        val rows = sections.mapNotNull(::extractRow).sortedBy { it.first }
        output.parentFile?.mkdirs()
        output.writeText(
            buildString {
                appendLine("Bericht,Datum")
                rows.forEach { (bericht, datum) -> appendLine("$bericht,$datum") }
            },
        )

        println("Extracted ${rows.size} dates to ${output.path}")
    }

    private fun extractRow(section: String): Pair<String, String>? {
        val lines = section.lines()
        val fileLine = lines.firstOrNull { it.startsWith("file:") } ?: return null
        val filename = fileLine.removePrefix("file:").trim().removeSuffix(".txt")

        val sectionText = section.substringAfter(fileLine)
        val date = DATE_REGEX.find(sectionText)?.value ?: return null

        return filename to date
    }
}

fun main() {
    ExtractDates.run()
}
