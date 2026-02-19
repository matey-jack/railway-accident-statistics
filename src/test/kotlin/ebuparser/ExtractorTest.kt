package ebuparser

import org.junit.jupiter.api.Test

class ExtractorTest {
    // Even this trivial request takes more than a minute, so don't run this test automatically...
    @Test
    fun test001() {
        val statsWriter = StatsWriter(LEMONADE_URL, "test-stats.txt")
        val content = "Translate this message to French: 'Hello, I love you; won't you tell me your name.'"
        val summary = extract("test", content)
        println(summary)
        statsWriter.writeFor("test")
    }
}
