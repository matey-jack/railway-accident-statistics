package ebuparser

import java.time.LocalDate

data class SummaryFile(
    val summaries: List<Summary>,
)

data class Summary(
    val fileName: String,
    val title: String,
    val published: LocalDate,
    val totalDamage: Int,
    val totalInjured: Int,
    val totalCasualties: Int,
    val sections: List<Section>,
)

data class SimpleSummary(
    val filename: String,
    val summary: String,
) {
    val asOutput: String
        get() = "file: $filename\n\n$summary"
}

enum class SectionCategory {
    WHAT_HAPPENED,
    DAMAGES,
    CAUSES,
    MEASURES,
}

data class Section(
    val title: String,
    val category: SectionCategory,
    val text: String,
)
