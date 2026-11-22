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

enum class RoadVehicleType {
    CAR,
    TRUCK,
    BUS,
    OTHER,
}

annotation class LlmField(
    val name: String,
    val description: String,
    val required: Boolean = true,
)

data class Classification(
    @LlmField(
        "Bahnübergang",
        "Der Unfall fand an einem Bahnübergang (BÜ) statt. (Meist Kollision mit einem Straßenfahrzeug.",
    )
    val railwayCrossing: Boolean,
    @LlmField(
        "Typ des Straßenfahrzeugs",
        "Nur auszufüllen, wenn es sich um einen Unfall am BÜ handelt.",
        required = false,
    )
    val roadVehicleType: RoadVehicleType?,
    @LlmField(
        "Bauarbeiten",
        "Der Unfall stand im Zusammenhang mit Bauarbeiten, z.B. eingeschränkter Betrieb, Kollision mit Baufahrzeugen oder Baumaterialien.",
    )
    val constructionWork: Boolean,
    @LlmField(
        "Störbetrieb",
        "Mindestens eine Betriebsstelle oder Strecke befand sich im Störbetrieb oder unter ganz oder teilweise aufgehobenen Signalabhängigkeit.",
    )
    val degradedMode: Boolean,
    @LlmField(
        "Naturkatastrophe",
        "Unfallursache war (teilweise oder hauptsächlich) eine Naturkatastrophe.",
    )
    val naturalDisaster: Boolean,
    @LlmField(
        "Fehlhandlung Fdl",
        "Unfallursache war (ganz oder teilweise) eine Fehlhandlung des Fahrdienstleiters (Fdl).",
    )
    val signallerError: Boolean,
    @LlmField(
        "Fehlhandlung Tf",
        "Unfallursache war (ganz oder teilweise) eine Fehlhandlung des Triebfahrzeugführers (Tf).",
    )
    val driverError: Boolean,
    @LlmField(
        "Mängel an Infrastruktur",
        "Unfallursache war ein Mangel an der Infrastruktur, z.B. Gleislage oder mangelnde Festigkeit.",
    )
    val infrastructureDeficiency: Boolean,
    @LlmField(
        "Art des Mangels an der Infrastruktur",
        "Nur auszufüllen, wenn es sich um einen Mangel an der Infrastruktur handelt.",
        required = false,
    )
    val infrastructureDeficiencyDescription: String,
    @LlmField(
        "Mängel am Fahrzeug",
        "Unfallursache war ein Mangel am Fahrzeug.",
    )
    val vehicleDeficiency: Boolean,
    @LlmField(
        "Art des Mangels am Fahrzeug",
        "Nur auszufüllen, wenn es sich um einen Mangel am Fahrzeug handelt.",
        required = false,
    )
    val vehicleDeficiencyDescription: String,
    @LlmField(
        "Unzureichende Bremsleistung",
        "Unfallursache war unzureichende Bremsleistung.",
    )
    val insufficientBrakes: Boolean,
    @LlmField("Brand", "Beim Unfall handelte es sich um einen Brand.")
    val fire: Boolean,
    @LlmField(
        "Kollision",
        "Es kam beim Unfall zu einer Kollision zweier Eisenbahnfahrzeuge. Explizit ausgenommen sind Kollisionen mit Straßenfahrzeugen.",
    )
    val collision: Boolean,
    @LlmField(
        "Sonstige Unfallursache",
        "Eine wesentliche Unfallursache ist nicht eine der vorgenannten Kategorien.",
    )
    val otherCause: Boolean,
    @LlmField(
        "Tote",
        "Anzahl der beim Unfall getöteten Personen. Weglassen, wenn die Zusammenfassung keine Anzahl nennt.",
        required = false,
    )
    val deaths: Int?,
    @LlmField(
        "Verletzte",
        "Anzahl der beim Unfall verletzten Personen (schwer und leicht verletzte zusammen). " +
            "Weglassen, wenn die Zusammenfassung keine Anzahl nennt.",
        required = false,
    )
    val injured: Int?,
    @LlmField(
        "Sachschaden",
        "Gesamthöhe des Sachschadens mit Währung, z.B. \"10.883.000 €\" oder \"50.000.000 DM\". " +
            "Wenn nur Einzelbeträge genannt sind, deren Summe. Weglassen, wenn die Zusammenfassung keinen Betrag nennt.",
        required = false,
    )
    val propertyDamage: String?,
    @LlmField(
        "Stellwerke",
        "Je beteiligtem Stellwerk ein Eintrag mit seiner Bauart, z.B. \"mechanisch\", \"SpDrS 60\" oder \"ESTW\". " +
            "Leer, wenn die Zusammenfassung kein Stellwerk nennt.",
    )
    val signalBoxes: List<String>,
    @LlmField(
        "Schienenfahrzeuge",
        "Je beteiligtem Schienenfahrzeug ein Eintrag mit Baureihe oder Fahrzeugtyp, " +
            "z.B. \"BR 612\", \"Kesselwagen\" oder \"Stopfmaschine\". Leer, wenn die Zusammenfassung keines nennt.",
    )
    val railVehicles: List<String>,
)

// The model's answer for one report, as stored in CLASSIFICATIONS_JSONL.
data class ClassificationRecord(
    val report: String,
    val title: String,
    val model: String,
    val response: Map<String, Any?>,
    val thinking: String? = null,
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
