package ebuparser

// The annotated constructor parameters of Classification and of the classes in its lists
// make up the tool schema for the LLM; the name is also the CSV column header.
annotation class LlmField(
    val name: String,
    val description: String,
)

// A closed list of values, so that a spreadsheet can group by them. The label is what the LLM chooses and the CSV shows.
interface Labeled {
    val label: String
}

// A two-level list: the CSV gets the coarse class as an extra column, so a pivot table can use either level.
interface TwoLevel : Labeled {
    val coarse: String
}

enum class Tristate(
    override val label: String,
) : Labeled {
    YES("ja"),
    NO("nein"),
    UNKNOWN("unbekannt"),
}

// The level-crossing fields have a value for "no level crossing", so that the schema can require every field.
const val NO_LEVEL_CROSSING = "kein BÜ"

enum class LevelCrossingProtection(
    override val label: String,
) : Labeled {
    FULL_BARRIERS("Vollschranke"),
    HALF_BARRIERS("Halbschranke"),
    LIGHTS("Lichtzeichen"),
    CROSS_ONLY("nur Andreaskreuz"),
    ATTENDED("Posten"),
    NONE(NO_LEVEL_CROSSING),
    UNKNOWN("unbekannt"),
}

enum class RoadVehicleType(
    override val label: String,
) : Labeled {
    CAR("Pkw"),
    TRUCK("Lkw"),
    BUS("Bus"),
    AGRICULTURAL("landwirtschaftlich"),
    TWO_WHEELER("Zweirad"),
    PEDESTRIAN("Fußgänger"),
    OTHER("sonstige"),
    NONE(NO_LEVEL_CROSSING),
    UNKNOWN("unbekannt"),
}

enum class TrainProtection(
    override val label: String,
) : Labeled {
    NONE("keine"),
    PZB("PZB"),
    LZB("LZB"),
    ETCS("ETCS"),
    UNKNOWN("unbekannt"),
}

enum class InfrastructureOperator(
    override val label: String,
) : Labeled {
    DB("DB"),
    NON_FEDERAL("NE-Bahn"),
    UNKNOWN("unbekannt"),
}

enum class Currency(
    override val label: String,
) : Labeled {
    EUR("EUR"),
    DM("DM"),
    NONE("kein Betrag"),
}

enum class SignalBoxDesign(
    override val label: String,
) : Labeled {
    MECHANICAL("mechanisch"),
    ELECTROMECHANICAL("elektromechanisch"),
    RELAY("Relais"),
    ELECTRONIC("elektronisch"),
    REMOTE("ferngesteuert"),
    NONE("keines"),
    UNKNOWN("unbekannt"),
}

data class SignalBox(
    @LlmField(
        "Bauform",
        "Relais umfasst Spurplan- und Gleisbildstellwerke (SpDrS 60, SpDrL 60, GS II DR), elektronisch umfasst ESTW. " +
            "ferngesteuert, wenn die Fahrstraßen von einer entfernten Stelle aus gestellt werden (z.B. Betriebszentrale). " +
            "keines, wenn es auf der Strecke kein Stellwerk gibt (z.B. Zugleitbetrieb).",
    )
    val design: SignalBoxDesign?,
    @LlmField("Stellwerkstyp", "Typbezeichnung wie in der Zusammenfassung, z.B. \"SpDrS 60\" oder \"Dr S2\"; leer, wenn keine genannt ist.")
    val type: String,
)

enum class TrainKind(
    override val label: String,
    override val coarse: String,
) : TwoLevel {
    LONG_DISTANCE("Fernverkehr", "Reisezug"),
    REGIONAL("Nahverkehr", "Reisezug"),
    S_BAHN("S-Bahn", "Reisezug"),
    FREIGHT("Güterzug", "Güterzug"),
    SHUNTING("Rangierfahrt", "Rangierfahrt"),
    WORK_TRAIN("Bau- oder Arbeitszug", "Bauzug"),
    LIGHT_ENGINE("Lok- oder Leerfahrt", "sonstige"),
    OTHER("sonstige", "sonstige"),
    UNKNOWN("unbekannt", "unbekannt"),
}

enum class VehicleKind(
    override val label: String,
    override val coarse: String,
) : TwoLevel {
    ELECTRIC_LOCOMOTIVE("Elektrolok", "Triebfahrzeug"),
    DIESEL_LOCOMOTIVE("Diesellok", "Triebfahrzeug"),
    ELECTRIC_MULTIPLE_UNIT("Elektrotriebwagen", "Triebfahrzeug"),
    DIESEL_MULTIPLE_UNIT("Dieseltriebwagen", "Triebfahrzeug"),
    HIGH_SPEED_TRAIN("Hochgeschwindigkeitszug", "Triebfahrzeug"),
    PASSENGER_CAR("Reisezugwagen", "Wagen"),
    FREIGHT_WAGON("Güterwagen", "Wagen"),
    ON_TRACK_MACHINE("Nebenfahrzeug oder Baumaschine", "Nebenfahrzeug"),
    OTHER("sonstige", "sonstige"),
    UNKNOWN("unbekannt", "unbekannt"),
}

data class RailVehicle(
    @LlmField("Zugart", "Art der Fahrt, in der das Fahrzeug unterwegs war.")
    val trainKind: TrainKind?,
    @LlmField(
        "Fahrzeugart",
        "Art des Fahrzeugs; z.B. ist BR 612 ein Dieseltriebwagen, BR 185 eine Elektrolok, ICE ein Hochgeschwindigkeitszug.",
    )
    val vehicleKind: VehicleKind?,
    @LlmField(
        "Baureihe",
        "Baureihe oder Typ wie in der Zusammenfassung, z.B. \"BR 612\", \"Kesselwagen\", \"Stopfmaschine\"; leer, wenn nicht genannt.",
    )
    val series: String,
)

enum class FactorRole(
    override val label: String,
) : Labeled {
    CAUSAL("ursächlich"),
    CONTRIBUTING("beitragend"),
    SYSTEMIC("systemisch"),
}

enum class Actor(
    override val label: String,
) : Labeled {
    DRIVER("Tf"),
    SIGNALLER("Fdl"),
    POINTSMAN("Ww oder Schrankenwärter"),
    SHUNTER("Rangierpersonal"),
    TRAIN_CREW("Zugpersonal"),
    CONSTRUCTION("Bauunternehmen oder Bauüberwachung"),
    MAINTENANCE("Instandhaltung oder Werkstatt"),
    RAILWAY_UNDERTAKING("EVU"),
    INFRASTRUCTURE_MANAGER("EIU"),
    VEHICLE_KEEPER("Fahrzeughalter oder Hersteller"),
    ROAD_USER("Straßenverkehrsteilnehmer"),
    OTHER_PERSON("sonstige Person"),
    NOBODY("keiner (Technik oder Natur)"),
    UNKNOWN("unbekannt"),
}

const val HUMAN_ERROR = "Fehlhandlung"

// The coarse class is the part of the label before the colon.
enum class CauseCategory(
    override val label: String,
) : TwoLevel {
    ERROR_SIGNAL("$HUMAN_ERROR: Signal oder BÜ missachtet"),
    ERROR_SPEED("$HUMAN_ERROR: Geschwindigkeit"),
    ERROR_ROUTE("$HUMAN_ERROR: Fahrstraße oder Weiche"),
    ERROR_ORDER("$HUMAN_ERROR: Befehl oder Ersatzsignal"),
    ERROR_OTHER("$HUMAN_ERROR: sonstige"),
    VEHICLE_BRAKE("Fahrzeug: Bremse"),
    VEHICLE_WHEELSET("Fahrzeug: Radsatz"),
    VEHICLE_RUNNING_GEAR("Fahrzeug: Fahrwerk"),
    VEHICLE_LOAD("Fahrzeug: Ladung"),
    VEHICLE_OTHER("Fahrzeug: sonstige"),
    INFRASTRUCTURE_TRACK_GEOMETRY("Infrastruktur: Gleislage"),
    INFRASTRUCTURE_RAIL_BREAK("Infrastruktur: Schienenbruch"),
    INFRASTRUCTURE_SWITCH("Infrastruktur: Weiche"),
    INFRASTRUCTURE_STRUCTURE("Infrastruktur: Bauwerk"),
    INFRASTRUCTURE_OTHER("Infrastruktur: sonstige"),
    SIGNALLING_INTERLOCKING("LST: Stellwerk"),
    SIGNALLING_SIGNAL("LST: Signal"),
    SIGNALLING_LEVEL_CROSSING("LST: BÜ-Technik"),
    SIGNALLING_TRAIN_PROTECTION("LST: Zugbeeinflussung"),
    SIGNALLING_OTHER("LST: sonstige"),
    ENVIRONMENT_STORM("Umwelt: Sturm oder Baum"),
    ENVIRONMENT_FLOOD("Umwelt: Hochwasser"),
    ENVIRONMENT_HEAT("Umwelt: Hitze"),
    ENVIRONMENT_LANDSLIDE("Umwelt: Hangrutsch"),
    ENVIRONMENT_ANIMALS("Umwelt: Tiere"),
    ENVIRONMENT_OTHER("Umwelt: sonstige"),
    ORGANISATION_RULES("Organisation: Regelwerk"),
    ORGANISATION_LA_BETRA("Organisation: La oder Betra"),
    ORGANISATION_TRAINING("Organisation: Ausbildung"),
    ORGANISATION_MAINTENANCE("Organisation: Instandhaltung"),
    ORGANISATION_OTHER("Organisation: sonstige"),
    ;

    override val coarse: String
        get() = label.substringBefore(':')
}

data class Factor(
    @LlmField(
        "Rolle",
        "Wie im Bericht: ursächlich, beitragend oder systemisch. Ohne Angabe im Bericht: ursächlich für den Hauptgrund, sonst beitragend.",
    )
    val role: FactorRole?,
    @LlmField(
        "Akteur",
        "Wer gehandelt oder etwas unterlassen hat; keiner (Technik oder Natur) bei technischem Versagen oder Naturereignis ohne Beteiligten.",
    )
    val actor: Actor?,
    @LlmField("Kategorie", "Art des Faktors.")
    val category: CauseCategory?,
    @LlmField("Beleg", "Kurzes wörtliches Zitat aus der Zusammenfassung, das den Faktor belegt (höchstens ein Satz).")
    val evidence: String,
)

data class Classification(
    @LlmField("Bahnübergang", "Der Unfall geschah an einem Bahnübergang (BÜ).")
    val levelCrossing: Tristate?,
    @LlmField("BÜ-Sicherung", "Art der Sicherung des Bahnübergangs; \"$NO_LEVEL_CROSSING\", wenn der Unfall nicht an einem BÜ geschah.")
    val levelCrossingProtection: LevelCrossingProtection?,
    @LlmField("Straßenfahrzeug", "Beteiligtes Straßenfahrzeug am BÜ; \"$NO_LEVEL_CROSSING\", wenn der Unfall nicht an einem BÜ geschah.")
    val roadVehicle: RoadVehicleType?,
    @LlmField(
        "Kollision Schienenfahrzeuge",
        "Zwei Schienenfahrzeuge stießen zusammen (auch Rangierfahrten und Baufahrzeuge, nicht Straßenfahrzeuge oder Hindernisse).",
    )
    val collision: Tristate?,
    @LlmField("Brand", "Es kam zu einem Brand.")
    val fire: Tristate?,
    @LlmField("Gefahrgut", "Gefahrgut war beteiligt (befördert, freigesetzt oder gefährdet).")
    val dangerousGoods: Tristate?,
    @LlmField(
        "Bauarbeiten",
        "Der Unfall stand im Zusammenhang mit Bauarbeiten, z.B. eingeschränkter Betrieb, Kollision mit Baufahrzeugen oder Baumaterialien.",
    )
    val constructionWork: Tristate?,
    @LlmField(
        "Störbetrieb",
        "Eine Betriebsstelle oder Strecke war im Störbetrieb, oder die Signalabhängigkeit war ganz oder teilweise aufgehoben.",
    )
    val degradedMode: Tristate?,
    @LlmField(
        "Ersatzsignal oder Befehl",
        "Ein unfallbeteiligter Zug fuhr auf Ersatzsignal (Zs 1, Zs 7, Zs 8) oder schriftlichen oder mündlichen Befehl.",
    )
    val substituteSignalOrOrder: Tristate?,
    @LlmField("Zugbeeinflussung", "Die wirksamste Zugbeeinflussung an der Unfallstelle.")
    val trainProtection: TrainProtection?,
    @LlmField("Zugleitbetrieb", "Die Strecke wurde im Zugleitbetrieb betrieben.")
    val trainDispatching: Tristate?,
    @LlmField("eingleisig", "Der Unfall geschah auf einer eingleisigen Strecke.")
    val singleTrack: Tristate?,
    @LlmField(
        "Infrastrukturbetreiber",
        "DB für die Infrastruktur der Deutschen Bahn (DB Netz, DB InfraGO, DB Station&Service), NE-Bahn für nichtbundeseigene Eisenbahnen.",
    )
    val infrastructureOperator: InfrastructureOperator?,
    @LlmField("Geschwindigkeit km/h", "Geschwindigkeit des unfallauslösenden Zuges unmittelbar vor dem Unfall; null, wenn nicht genannt.")
    val speedKmh: Int?,
    @LlmField("Tote", "Anzahl der beim Unfall getöteten Personen; null, wenn die Zusammenfassung keine Anzahl nennt.")
    val deaths: Int?,
    @LlmField("Verletzte", "Anzahl aller Verletzten (schwer und leicht zusammen); null, wenn die Zusammenfassung keine Anzahl nennt.")
    val injured: Int?,
    @LlmField("Schwerverletzte", "Anzahl der Schwerverletzten; null, wenn die Zusammenfassung schwer und leicht Verletzte nicht trennt.")
    val seriouslyInjured: Int?,
    @LlmField("Leichtverletzte", "Anzahl der Leichtverletzten; null, wenn die Zusammenfassung schwer und leicht Verletzte nicht trennt.")
    val slightlyInjured: Int?,
    @LlmField(
        "Sachschaden Betrag",
        "Gesamthöhe des Sachschadens als Zahl, z.B. 50000000 für \"ca. 50.000.000.- DM\". " +
            "Wenn nur Einzelbeträge genannt sind, deren Summe. null, wenn kein Betrag genannt ist.",
    )
    val propertyDamageAmount: Double?,
    @LlmField("Sachschaden Währung", "Währung des Betrags; \"kein Betrag\", wenn kein Betrag genannt ist.")
    val propertyDamageCurrency: Currency?,
    @LlmField("Sachschaden geschätzt", "Der Betrag ist eine Schätzung (\"ca.\", \"geschätzt\", \"mindestens\", \"mehr als\").")
    val propertyDamageEstimated: Tristate?,
    @LlmField("Unfallfaktoren", "Je ursächlichem, beitragendem oder systemischem Faktor ein Eintrag.")
    val factors: List<Factor>,
    @LlmField("Stellwerke", "Je beteiligtem Stellwerk ein Eintrag; leer, wenn die Zusammenfassung kein Stellwerk nennt.")
    val signalBoxes: List<SignalBox>,
    @LlmField("Schienenfahrzeuge", "Je beteiligtem Schienenfahrzeug ein Eintrag; leer, wenn die Zusammenfassung keines nennt.")
    val railVehicles: List<RailVehicle>,
)

// The model's answer for one report, as stored in CLASSIFICATIONS_JSONL.
data class ClassificationRecord(
    val report: String,
    val title: String,
    val model: String,
    val response: Map<String, Any?>,
    val thinking: String? = null,
)
