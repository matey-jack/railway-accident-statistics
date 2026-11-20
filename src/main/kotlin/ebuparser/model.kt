package ebuparser

import java.time.LocalDate

const val SECTION_SEPARATOR = "\n---\n"

data class Summary(
    val filename: String,
    val date: String, // format dd.mm.yyyy
    val title: String,
    val sections: List<Section>,
)

data class Section(
    val title: String,
    val category: SectionCategory,
    val text: String,
)

data class DamagesSummary(
    val fileName: String,
    val title: String,
    val published: LocalDate,
    val totalDamage: Int,
    val totalInjured: Int,
    val totalCasualties: Int,
    val sections: List<Section>,
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
