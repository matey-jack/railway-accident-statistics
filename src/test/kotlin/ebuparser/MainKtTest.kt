package ebuparser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class MainKtTest {
    @Test
    fun testGetAlreadyProcessedFilenames() {
        val outputFile = File("src/test/resources/output-1.md")
        val processedFilenames = getAlreadyProcessedFilenames(outputFile)

        assertEquals(2, processedFilenames.size)
        assertEquals(setOf("the-first-report.txt", "another.txt"), processedFilenames)
    }

    @Test
    fun testGetAlreadyProcessedFilenames_FileDoesNotExist() {
        val outputFile = File("src/test/resources/nonexistent.md")
        val processedFilenames = getAlreadyProcessedFilenames(outputFile)

        assertEquals(0, processedFilenames.size)
        assertEquals(emptySet<String>(), processedFilenames)
    }
}
