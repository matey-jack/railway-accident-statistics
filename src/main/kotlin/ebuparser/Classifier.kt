package ebuparser

import software.amazon.awssdk.core.document.Document
import software.amazon.awssdk.services.bedrockruntime.model.*

class Classifier {
    val toolConfig = ToolConfiguration.builder()
        .tools(
            Tool.fromToolSpec(
                ToolSpecification.builder()
                    .name("classify")
                    .description("Unfallkategorisierung")
                    .inputSchema(
                        ToolInputSchema.fromJson(
                            Document.mapBuilder()
                                .putString("type", "object")
                                .putMap(
                                    "properties", mapOf(
                                        // TODO: iterate over properties of class Classification and pass name and description from the LlmField annotation, using "ja" and "nein" as values for boolean fields
                                    )
                                )
                                .putList(
                                    "required", listOf(
                                        Document.fromString("reasoning"),
                                        // TODO: iterate over properties of class Classification and pass the ones that have the required property set in their LlmField annotation.
                                    )
                                )
                                .build()
                        )
                    )
                    .build()
            )
        )
        .toolChoice(
            ToolChoice.fromTool(
                SpecificToolChoice.builder().name("classify").build()
            )
        )
        .build()

    fun classify(summary: Summary): Classification {
        // TODO call Nova 2 like in ExtractSummaries.kt, but using "classification-prompt.txt" and the above toolConfig
        // pass the title and the content sections "Hergang" and "Ursachen" (including headings) as it appears in summaries.md as input to the model.
        // use "maxReasoningEffort": "low"
    }
}

fun parseSummaries(path: String): List<Summary> {
// TODO: implement similar to how summaries.md is parsed in ExtractDamagesAndCauses.kt
}

val testReports = listOf("114", "023", "002", "007", "231", "238", "199", "009", "047", "034")

fun main() {
    val classifier = Classifier()
    val summaries = parseSummaries("results/bedrock/summaries.md")
    val classifications = summaries
        .filter { testReports.contains(it.filename.take(3)) }
        .map { Pair(it, classifier.classify(it)) }
    // TODO: write classifications to file in CSV format, using the LlmField.name as heading for each property.
    //       The first columns should be summary.filename (without .txt suffix; header name: "Bericht") and summary.date (header name "Datum").
    //      Boolean fields should be written using "x" for true and empty for false.
}