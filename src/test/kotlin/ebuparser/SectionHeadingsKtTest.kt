package ebuparser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SectionHeadingsKtTest {
    @Test
    fun testNormalizeSectionHeadings_NestedUnderCopiedHeading() {
        val input =
            """
            # Untersuchungsbericht

            ## **Titel des Untersuchungsberichts**
            Zusammenstoß, Bf Bad Münder, 09.09.2002

            ## Hergang des Unfalls
            Text.

            ### 2.1 Hergang
            Mehr Text.
            """.trimIndent()

        val expected =
            """
            ## Untersuchungsbericht

            # Titel des Untersuchungsberichts
            Zusammenstoß, Bf Bad Münder, 09.09.2002

            # Hergang des Unfalls
            Text.

            ### 2.1 Hergang
            Mehr Text.
            """.trimIndent()

        assertEquals(expected, normalizeSectionHeadings(input))
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
    fun testMissingSectionHeadings() {
        val summary = SUMMARY_SECTION_HEADINGS.drop(1).joinToString("\n\n") { "# $it\nText." }
        assertEquals(listOf(TITLE_HEADING), missingSectionHeadings(summary))
    }
}
