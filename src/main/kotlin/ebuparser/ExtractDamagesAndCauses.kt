package ebuparser

import java.io.File

private const val INPUT_FILE = "results/bedrock/summaries.md"
private const val DAMAGES_OUTPUT_FILE = "results/damages.md"
private const val CAUSES_OUTPUT_FILE = "results/causes.md"
private val DATE_REGEX = Regex("""\d\d\.\d\d\.\d\d\d\d""")

object ExtractDamages {
    fun run(
        inputPath: String = INPUT_FILE,
        damagesOutputPath: String = DAMAGES_OUTPUT_FILE,
        causesOutputPath: String = CAUSES_OUTPUT_FILE,
    ) {
        val input = File(inputPath)

        val normalizedContent = input.readText().replace("\r\n", "\n")
        val sections =
            normalizedContent
                .split(SECTION_SEPARATOR)
                .map { it.trim() }
                .filter { it.isNotBlank() }

        writeExtracted(sections, ::isDamagesHeading, damagesOutputPath)
        writeExtracted(sections, ::isCausesHeading, causesOutputPath)
    }

    private fun writeExtracted(
        sections: List<String>,
        headingMatcher: (String) -> Boolean,
        outputPath: String,
    ) {
        val output = File(outputPath)
        val extractedSections = sections.mapNotNull { extractSection(it, headingMatcher) }
        output.parentFile?.mkdirs()
        output.writeText(extractedSections.joinToString(SECTION_SEPARATOR, postfix = "\n"))
        println("Extracted ${extractedSections.size} sections to ${output.path}")
    }

    private fun extractSection(
        section: String,
        headingMatcher: (String) -> Boolean,
    ): String? {
        val lines = section.lines()
        val fileLine = lines.firstOrNull { it.startsWith("file:") } ?: return null

        val sectionText = section.substringAfter(fileLine)
        val date = DATE_REGEX.find(sectionText)?.value

        val contentStart = lines.indexOfFirst(headingMatcher)

        val contentLines = mutableListOf<String>()
        if (contentStart != -1) {
            var index = contentStart + 1
            while (index < lines.size) {
                val currentLine = lines[index]
                if (currentLine.startsWith("#")) {
                    break
                }
                contentLines.add(currentLine)
                index++
            }
        }

        return buildString {
            appendLine(fileLine)
            if (date != null) {
                appendLine("date: $date")
            }
            if (contentLines.isNotEmpty()) {
                appendLine()
                append(contentLines.joinToString("\n"))
            }
        }
    }

    private fun isDamagesHeading(line: String): Boolean {
        val trimmed = line.trim()
        if (!trimmed.startsWith("#")) return false
        val headingText = trimmed.trimStart('#').trim().lowercase()
        return headingText.contains("schaden") &&
            headingText.contains("tote") &&
            headingText.contains("verletzte")
    }

    private fun isCausesHeading(line: String): Boolean {
        val trimmed = line.trim()
        if (!trimmed.startsWith("#")) return false
        val headingText = trimmed.trimStart('#').trim().lowercase()
        return headingText.contains("ursachen") && headingText.contains("unfall")
    }
}

fun main() {
    ExtractDamages.run()
}
