package ebuparser

import java.io.File

data class ScoredSummary(
    val summary: String,
    val sections: Map<String, String>,
    val assessment: SummaryAssessment,
    val missingSections: List<String>,
)

// Older runs didn't keep the prescribed heading levels, so their summaries get the same cleaning as new ones.
fun readSummaryFile(file: File): Map<String, String> {
    val text = file.readText().replace("\r\n", "\n")
    val fileLine = Regex("""(?m)^file: (\S+)\s*$""")
    val names = fileLine.findAll(text).map { it.groupValues[1] }.toList()
    val bodies = fileLine.split(text).drop(1)
    return names.zip(bodies).associate { (name, body) -> name to cleanSummary(body, onHeadingFix = {}) }
}

fun scoreSummaryFile(file: File): Map<String, ScoredSummary> =
    readSummaryFile(file).mapValues { (name, summary) ->
        val report = File("documents/$name").readText()
        ScoredSummary(
            summary = summary,
            sections = summarySections(summary),
            assessment = assessSummary(name, summary, report),
            missingSections = missingSectionHeadings(summary),
        )
    }

// Writes the same assessment that ExtractSummaries logs, for summary files from earlier runs.
fun main(args: Array<String>) {
    val paths = args.ifEmpty { arrayOf("$BEDROCK_OUTPUT_DIR/$OUTPUT_FILENAME") }
    for (path in paths) {
        val file = File(path)
        val lines =
            scoreSummaryFile(file).values.flatMap { scored ->
                scored.assessment.logLines() +
                    scored.missingSections
                        .takeIf { it.isNotEmpty() }
                        ?.let { listOf("  missing sections: $it") }
                        .orEmpty()
            }
        val outputFile = File(file.parentFile, file.nameWithoutExtension + "-assessment.txt")
        outputFile.writeText(lines.joinToString("\n", postfix = "\n"))
        println("Assessment of $path written to $outputFile")
    }
}
