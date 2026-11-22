package ebuparser

const val SECTION_SEPARATOR = "\n---\n"

const val TITLE_HEADING = "Titel des Untersuchungsberichts"

// The headings must match those prescribed in extraction-prompt.txt, in the same order.
enum class SectionCategory(
    val heading: String,
) {
    TITLE(TITLE_HEADING),
    WHAT_HAPPENED("Hergang des Unfalls"),
    DAMAGES("Höhe des Schadens, Anzahl Tote und Verletzte"),
    CAUSES("Ursachen des Unfalls"),
    SIGNAL_BOXES("Bauart der beteiligten Stellwerke"),
    VEHICLES("Baureihe der beteiligten Schienenfahrzeuge"),
    RECOMMENDATIONS("Sicherheitsempfehlungen zur Verhinderung zukünftiger Unfälle"),
    MEASURES_TAKEN("bereits umgesetzte Maßnahmen"),
}

val SUMMARY_SECTION_HEADINGS = SectionCategory.entries.map { it.heading }

data class Summary(
    val filename: String,
    val title: String,
    val sections: List<Section>,
) {
    val reportNumber: String
        get() = filename.substringBeforeLast('.')
}

data class Section(
    val category: SectionCategory,
    val text: String,
) {
    val title: String
        get() = category.heading
}

// The title has the form "<Ereignisart>, <Ort oder Strecke>, <TT.MM.JJJJ>", as prescribed in extraction-prompt.txt.
data class ReportTitle(
    val eventType: String,
    val location: String,
    val date: String,
)

data class SimpleSummary(
    val filename: String,
    val summary: String,
) {
    val asOutput: String
        get() = "file: $filename\n\n$summary"
}

// just to test the new table handling
val testReportsWithWideTables = listOf("089", "114", "226", "231")

val testReports =
    listOf(
        // some famous or interesting reports
        "114",
        "023",
        "001",
        "002",
        "007",
        "008",
        "231",
        "238",
        "199",
        "009",
        "047",
        "034",
        // five shortest and five longest reports (minus the ones listed in other already)
        "179",
        "181",
        "191",
        "196",
        "012",
        "032",
        "201",
        "203",
        "116",
        "226",
        "089",
        "237",
        "015",
        "082",
        "125",
        "126",
        // some random ones to round it up
        "123",
        "234",
        "101",
        "055",
        "042",
        "180",
        "200",
        "096",
    )
