package ebuparser

import java.io.File

private const val SECTION_SEPARATOR = "\n---\n"
private const val DAMAGE_HEADING = "# Höhe des Schadens, Anzahl Tote und Verletzte"
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

        val damageStart = lines.indexOfFirst { it.trim() == DAMAGE_HEADING }
        if (damageStart == -1) {
            return null
        }

        val damageLines = mutableListOf<String>()
        var index = damageStart
        while (index < lines.size) {
            val currentLine = lines[index]
            if (index > damageStart && currentLine.startsWith("# ")) {
                break
            }
            damageLines.add(currentLine)
            index++
        }

        return buildString {
            appendLine(fileLine)
            appendLine()
            append(damageLines.joinToString("\n").trimEnd())
        }
    }
}

fun main() {
    ExtractDamages.run()
}
