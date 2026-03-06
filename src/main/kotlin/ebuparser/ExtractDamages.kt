package ebuparser

import java.io.File

private const val SECTION_SEPARATOR = "\n---\n"
private const val INPUT_FILE = "results/bedrock/summaries.md"
private const val OUTPUT_FILE = "results/damages.md"

object ExtractDamages {
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

        val extractedSections = sections.mapNotNull(::extractSection)
        output.parentFile?.mkdirs()
        output.writeText(extractedSections.joinToString(SECTION_SEPARATOR, postfix = "\n"))

        println("Extracted ${extractedSections.size} sections to ${output.path}")
    }

    private fun extractSection(section: String): String? {
        val lines = section.lines()
        val fileLine = lines.firstOrNull { it.startsWith("file:") } ?: return null

        val damageStart = lines.indexOfFirst(::isDamagesHeading)

        val damageLines = mutableListOf<String>()
        if (damageStart != -1) {
            var index = damageStart + 1
            while (index < lines.size) {
                val currentLine = lines[index]
                if (currentLine.startsWith("#")) {
                    break
                }
                damageLines.add(currentLine)
                index++
            }
        }

        return buildString {
            appendLine(fileLine)
            if (damageLines.isNotEmpty()) {
                appendLine()
                append(damageLines.joinToString("\n").trimEnd())
            }
        }.trimEnd()
    }

    private fun isDamagesHeading(line: String): Boolean {
        val headingText = line.trim().removePrefix("#").trim().lowercase()
        return headingText.contains("schad") &&
            headingText.contains("tote") &&
            headingText.contains("verletzte")
    }
}

fun main() {
    ExtractDamages.run()
}
