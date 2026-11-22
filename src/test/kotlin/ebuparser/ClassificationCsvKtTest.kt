package ebuparser

import org.junit.jupiter.api.Assertions.assertEquals
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

    @Test
    fun testClassificationsCsv() {
        val record =
            ClassificationRecord(
                report = "002",
                title = "Entgleisung, Bf Brühl, 06.02.2000",
                model = "m",
                response =
                    mapOf(
                        "reasoning" to "Übergeschwindigkeit",
                        "driverError" to "ja",
                        "railwayCrossing" to "nein",
                        "deaths" to 9,
                        "injured" to "149",
                        "signalBoxes" to listOf("SpDrS 60"),
                        "railVehicles" to listOf("BR 101", "Reisezugwagen"),
                    ),
            )

        val (header, row) = classificationsCsv(listOf(record)).trim().lines()
        val values = header.split(',').zip(row.split(',')).toMap()

        assertEquals("002", values["Bericht"])
        assertEquals("Bf Brühl", values["Ort"])
        assertEquals("06.02.2000", values["Datum"])
        assertEquals("x", values["Fehlhandlung Tf"])
        assertEquals("", values["Bahnübergang"])
        assertEquals("9", values["Tote"])
        assertEquals("149", values["Verletzte"])
        assertEquals("", values["Sachschaden"])
        assertEquals("BR 101; Reisezugwagen", values["Schienenfahrzeuge"])
        assertEquals("Übergeschwindigkeit", values["Begründung"])
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
