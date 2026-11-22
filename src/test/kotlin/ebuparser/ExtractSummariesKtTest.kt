package ebuparser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class ExtractSummariesKtTest {
    @Test
    fun testGetAlreadyProcessedFilenames() {
        val outputFile = File("src/test/resources/output-1.md")
        val processedFilenames = getAlreadyProcessedFilenames(outputFile)

        assertEquals(2, processedFilenames.size)
        assertEquals(setOf("the-first-report.txt", "another.txt"), processedFilenames)
    }

    @Test
    fun testGetAlreadyProcessedFilenames_FileDoesNotExist(
        @TempDir tempDir: Path,
    ) {
        val outputFile = tempDir.resolve("nonexistent.md").toFile()
        val processedFilenames = getAlreadyProcessedFilenames(outputFile)

        assertEquals(0, processedFilenames.size)
        assertEquals(emptySet<String>(), processedFilenames)
    }

    @Test
    fun testParseSummaries_TitleFromTitleSection(
        @TempDir tempDir: Path,
    ) {
        val file = tempDir.resolve("summaries.md").toFile()
        file.writeText(
            "\n---\nfile: 001.md\n\n# Untersuchungsbericht\n\n## Titel des Untersuchungsberichts\n" +
                "**Zusammenstoß, Bf Bad Münder, 09.09.2002**\n\n## Hergang des Unfalls\nText.\n",
        )

        val summary = parseSummaries(file.path).single()

        assertEquals("Zusammenstoß, Bf Bad Münder, 09.09.2002", summary.title)
        assertEquals(listOf(TITLE_HEADING, "Hergang des Unfalls"), summary.sections.map { it.title })
    }
}
