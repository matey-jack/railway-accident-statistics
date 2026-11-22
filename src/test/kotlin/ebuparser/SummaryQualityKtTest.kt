package ebuparser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class SummaryQualityKtTest {
    @Test
    fun testSqueezeTablePadding_OnlyTableLines() {
        val input = "Text  mit   Leerzeichen\n| a      | b    |\n|--------|------|"
        assertEquals("Text  mit   Leerzeichen\n| a | b |\n|---|---|", squeezeTablePadding(input))
    }

    @Test
    fun testNormalizeSectionHeadings_Typo() {
        assertEquals(
            "# Höhe des Schadens, Anzahl Tote und Verletzte",
            normalizeSectionHeadings("# Höhe des Schadens, Anzahl Tote und Verlette"),
        )
    }

    @Test
    fun testNormalizeSectionHeadings_OtherHeadingStaysSubheading() {
        assertEquals("## Ursachen", normalizeSectionHeadings("# Ursachen"))
    }

    @Test
    fun testAssessSummary() {
        val report =
            "Der Zug fuhr mit **ca. 54 km/h** am Signal<sup>3)</sup> vorbei. Danach entgleiste er.\n" +
                "| Spalte    | Spalte |"
        val summary =
            """
            # Titel des Untersuchungsberichts
            Entgleisung, Bf Irgendwo, 01.01.2020

            # Hergang des Unfalls
            Der Zug fuhr mit ca. 54 km/h am Signal vorbei.
            Der Zug fuhr mit ca. 60 km/h am Signal vorbei.

            # Ursachen des Unfalls
            | Spalte | Spalte |

            # Bauart der beteiligten Stellwerke
            keine Angabe im Bericht
            """.trimIndent()

        val assessment = assessSummary("x.md", summary, report)

        assertEquals(2, assessment.sentenceCount)
        assertEquals(listOf("Der Zug fuhr mit ca. 60 km/h am Signal vorbei."), assessment.notVerbatim)
        assertEquals(listOf("Bauart der beteiligten Stellwerke"), assessment.sectionsWithoutInfo)
    }

    @Test
    fun testAssessSummary_Runaway() {
        val summary = "# Hergang des Unfalls\n| a " + " ".repeat(100) + "|"
        val assessment = assessSummary("x.md", summary, "| a" + " ".repeat(50) + "| b |")
        assertEquals(true, assessment.isRunaway)
    }

    @Test
    fun testAssessSummary_JoinedParagraphsAreNoRunaway() {
        val report = "Erster Satz im Bericht.\nZweiter Satz im Bericht."
        val assessment = assessSummary("x.md", "# Hergang des Unfalls\n" + report.replace("\n", " "), report)
        assertEquals(false, assessment.isRunaway)
    }

    @Test
    fun testReadSummaryFile_OldHeadingLevels(
        @TempDir tempDir: Path,
    ) {
        val file = tempDir.resolve("summaries.md").toFile()
        file.writeText(
            "\n---\nfile: 001.md\n\n# Untersuchungsbericht\n\n## Hergang des Unfalls\nText.\n" +
                "\n\n---\nfile: 002.md\n\n# Hergang des Unfalls\nAnderer Text.\n",
        )

        val summaries = readSummaryFile(file)

        assertEquals(listOf("001.md", "002.md"), summaries.keys.toList())
        assertEquals("Text.", summarySections(summaries.getValue("001.md"))["Hergang des Unfalls"]?.trim())
    }
}
