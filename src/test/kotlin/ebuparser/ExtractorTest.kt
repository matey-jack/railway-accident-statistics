package ebuparser

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

class ExtractorTest {
    // This needs Lemonade running to work...
    @Test
    @Disabled("Even this trivial request takes more than a minute, so don't run this test automatically...")
    fun test001() {
        val statsWriter = StatsWriter(LEMONADE_URL, "test-stats.txt")
        // I make it long hoping to get some non-ascii characters in the output.
        val content = "Translate this message to French: `Hello, I love you; won't you tell me your name. Let's have coffee sometime and get to know each other well.`'`"
        val summary = extract("müßiger Test", content)
        println(summary)
        File("test-summary.md").writeText(summary.asOutput)
        statsWriter.writeFor("test")
    }
}
