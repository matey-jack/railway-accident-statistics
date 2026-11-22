package ebuparser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class ClassificationCsvKtTest {
    @Test
    fun testParseReportTitle() {
        assertEquals(
            ReportTitle("Entgleisung", "Strecke Berlin – Cottbus", "10.12.2022"),
            parseReportTitle("Zugentgleisung, Strecke Berlin – Cottbus, 10.12.2022"),
        )
    }

    @Test
    fun testParseReportTitle_CommaInLocation() {
        assertEquals(
            ReportTitle("Kollision", "Bf Köln-Kalk, Gleis 3", "15.03.2006"),
            parseReportTitle("Zusammenstoß, Bf Köln-Kalk, Gleis 3, 15.03.2006"),
        )
    }

    @Test
    fun testParseReportTitle_NoDate() {
        assertEquals(ReportTitle("Brand", "Bf Irgendwo", ""), parseReportTitle("Brand, Bf Irgendwo"))
    }

    private val bruehlResponse =
        mapOf(
            "reasoning" to "Übergeschwindigkeit",
            "levelCrossing" to "nein",
            "levelCrossingProtection" to NO_LEVEL_CROSSING,
            "roadVehicle" to NO_LEVEL_CROSSING,
            "collision" to "nein",
            "fire" to "unbekannt",
            "deaths" to 9,
            "injured" to 149L,
            "seriouslyInjured" to null,
            "slightlyInjured" to null,
            "propertyDamageAmount" to 1.0E7,
            "propertyDamageCurrency" to "DM",
            "propertyDamageEstimated" to "ja",
            "factors" to
                listOf(
                    mapOf(
                        "role" to "ursächlich",
                        "actor" to "Tf",
                        "category" to "Fehlhandlung: Geschwindigkeit",
                        "evidence" to "Der Tf fuhr mit 122 km/h statt 40 km/h.",
                    ),
                    mapOf(
                        "role" to "systemisch",
                        "actor" to "EIU",
                        "category" to "Organisation: La oder Betra",
                        "evidence" to "Die Betra war fehlerhaft.",
                    ),
                ),
            "signalBoxes" to listOf(mapOf("design" to "Relais", "type" to "Sp Dr S 60")),
            "railVehicles" to
                listOf(
                    mapOf("trainKind" to "Fernverkehr", "vehicleKind" to "Elektrolok", "series" to "Baureihe 101"),
                    mapOf("trainKind" to "Fernverkehr", "vehicleKind" to "Reisezugwagen", "series" to ""),
                ),
        )

    private val bruehlRecord = ClassificationRecord("002", "Entgleisung, Bf Brühl, 06.02.2000", "m", bruehlResponse)

    private fun csvValues(csv: String): Map<String, String> {
        val (header, row) = parseCsv(csv)
        return header.zip(row).toMap()
    }

    @Test
    fun testClassificationsCsv() {
        val ebuEntries = listOf(EbuEntry("06.02.2000", "Brühl", "Entgleisung", "2630", "10,4"))
        val values = csvValues(classificationsCsv(listOf(classifyReport(bruehlRecord, ebuEntries))))

        assertEquals("002", values["Bericht"])
        assertEquals("Brühl", values["Ort"])
        assertEquals("06.02.2000", values["Datum"])
        assertEquals("2630", values["Streckennummer"])
        assertEquals("0", values["Bahnübergang"])
        assertEquals("", values["Brand"])
        assertEquals("9", values["Tote"])
        assertEquals("149", values["Verletzte"])
        assertEquals("10000000", values["Sachschaden Betrag"])
        assertEquals("5112919", values["Sachschaden EUR"])
        assertEquals("ursächlich", values["Fehlhandlung Tf"])
        assertEquals("nein", values["Fehlhandlung Fdl"])
        assertEquals("beitragend", values["Ursache Organisation"])
        assertEquals("Relais / SpDrS 60", values["Stellwerke"])
        assertEquals("Fernverkehr / Elektrolok / BR 101; Fernverkehr / Reisezugwagen", values["Schienenfahrzeuge"])
        assertEquals("Übergeschwindigkeit", values["Begründung"])
        // The fields that the response leaves out are reported.
        assertTrue(values.getValue("Prüfhinweise").contains("Gefahrgut fehlt"))
        assertFalse(values.containsKey("Unfallfaktoren"))
    }

    @Test
    fun testListTableCsv() {
        val report = classifyReport(bruehlRecord, emptyList())
        val rows = parseCsv(listTableCsv(listOf(report), Classification::factors))
        val factors = rows.drop(1).map { rows.first().zip(it).toMap() }

        assertEquals(2, factors.size)
        assertEquals("Fehlhandlung", factors[0]["Kategorie grob"])
        assertEquals("Fehlhandlung: Geschwindigkeit", factors[0]["Kategorie"])
        assertEquals("Tf", factors[0]["Akteur"])
        assertEquals("9", factors[0]["Tote"])
        assertEquals("Entgleisung", factors[0]["Ereignisart"])
    }

    @Test
    fun testParseClassification_InvalidValuesAreReported() {
        val parsed =
            parseClassification(
                bruehlResponse + mapOf("collision" to "vielleicht", "deaths" to "neun", "extra" to 1),
            )

        assertNull(parsed.classification.collision)
        assertNull(parsed.classification.deaths)
        assertTrue(parsed.issues.contains("Kollision Schienenfahrzeuge: ungültiger Wert 'vielleicht'"))
        assertTrue(parsed.issues.contains("Tote: ungültiger Wert 'neun'"))
        assertTrue(parsed.issues.contains(": unbekanntes Feld 'extra'"))
    }

    @Test
    fun testConsistencyIssues() {
        val parsed =
            parseClassification(
                bruehlResponse +
                    mapOf(
                        "roadVehicle" to "Lkw",
                        "seriouslyInjured" to 100,
                        "slightlyInjured" to 100,
                        "propertyDamageCurrency" to "kein Betrag",
                    ),
            )

        assertTrue(parsed.issues.contains("kein Bahnübergang, aber Straßenfahrzeug 'Lkw'"))
        assertTrue(parsed.issues.contains("mehr Schwer- und Leichtverletzte als Verletzte insgesamt"))
        assertTrue(parsed.issues.contains("Sachschaden: Betrag und Währung passen nicht zusammen"))
    }

    @Test
    fun testCauseGrade_NoFactorsMeansUnknown() {
        assertNull(causeGrade(emptyList(), causeColumns.first()))
    }

    @Test
    fun testNormalizeSignalBoxType() {
        listOf("Sp Dr S 60", "SpDrS60", "SpDr S60", "Spurplan-Drucktastenstellwerk, Bauform Siemens 60").forEach {
            assertEquals("SpDrS 60", normalizeSignalBoxType(it), it)
        }
        assertEquals("SpDrS 59", normalizeSignalBoxType("Sp Dr S59"))
        assertEquals("DrS 2", normalizeSignalBoxType("Dr S2"))
        assertEquals("ESTW", normalizeSignalBoxType("ESTW"))
    }

    @Test
    fun testNormalizeVehicleSeries() {
        listOf("BR 612", "Baureihe 612", "BR612", "612 123-4", "95 80 0612 123-4").forEach {
            assertEquals("BR 612", normalizeVehicleSeries(it), it)
        }
        assertEquals("Stopfmaschine", normalizeVehicleSeries("Stopfmaschine"))
    }

    @Test
    fun testClassificationSchemaIsStrict() {
        fun check(schema: Map<*, *>) {
            if (schema["type"] == "object") {
                assertEquals(false, schema["additionalProperties"])
                assertEquals((schema["properties"] as Map<*, *>).keys.toList(), schema["required"])
                (schema["properties"] as Map<*, *>).values.forEach { check(it as Map<*, *>) }
            }
            (schema["items"] as Map<*, *>?)?.let(::check)
        }
        check(classificationSchema)
    }

    @Test
    fun testAbbreviationsFromExtractionPrompt() {
        val section = markdownSection(summarizationPrompt, "# Abkürzungen in den Berichten")

        assertTrue(section.contains("Fdl = Fahrdienstleiter"))
        assertFalse(section.contains("# Wo die Informationen stehen"))
    }

    @Test
    fun testFindEbuEntry_SameDateDecidedByLocation() {
        val entries =
            listOf(
                EbuEntry("08.04.2011", "Bf Hamburg-Harburg", "Entgleisung", "", ""),
                EbuEntry("08.04.2011", "Köln-Kalk Nord", "Kollision", "", ""),
            )

        assertEquals("Köln-Kalk Nord", findEbuEntry(entries, ReportTitle("Kollision", "Bf Köln-Kalk", "08.04.2011")).entry?.location)
        assertNull(findEbuEntry(entries, ReportTitle("Kollision", "Bf Irgendwo", "08.04.2011")).entry)
    }

    @Test
    fun testReadEbuList() {
        val entries = readEbuList()

        assertTrue(entries.size > 200)
        assertEquals(EbuEntry("26.04.2008", "Landrückentunnel", "Kollision", "1733", "251,325"), entries[2])
    }

    @Test
    fun testEvaluate() {
        val labels = "Bericht,Tote,Stellwerke\n002,9,\n003,1,mechanisch; ESTW\n"
        val results = "Bericht,Tote,Stellwerke\n002,9,x\n003,2,esTW; Mechanisch\n"

        val (deaths, signalBoxes) = evaluate(labels, results)

        assertEquals(2, deaths.compared)
        assertEquals(listOf("003: erwartet '1', erhalten '2'"), deaths.mismatches)
        assertEquals(1, signalBoxes.compared)
        assertEquals(1, signalBoxes.correct)
    }

    @Test
    fun testClassificationRecordRoundTrip(
        @TempDir tempDir: Path,
    ) {
        val path = tempDir.resolve("classification/classifications.jsonl").toString()
        val record = ClassificationRecord("001", "Titel", "m", mapOf("signalBoxes" to listOf("mechanisch"), "deaths" to 0))

        appendClassificationRecord(record, path)
        appendClassificationRecord(record.copy(report = "002"), path)

        assertEquals(listOf(record, record.copy(report = "002")), readClassificationRecords(path))
    }

    @Test
    fun testParseSummaries_SectionCategories(
        @TempDir tempDir: Path,
    ) {
        val file = tempDir.resolve("summaries.md").toFile()
        file.writeText(
            "\n---\nfile: 001.md\n\n" +
                SectionCategory.entries.joinToString("\n\n") { "# ${it.heading}\n${it.name}" } + "\n",
        )

        val summary = parseSummaries(file.path).single()

        assertEquals("001", summary.reportNumber)
        assertEquals("TITLE", summary.title)
        assertEquals(SectionCategory.entries.map { it.name }, summary.sections.map { it.text })
        assertEquals(SectionCategory.entries, summary.sections.map { it.category })
    }
}
