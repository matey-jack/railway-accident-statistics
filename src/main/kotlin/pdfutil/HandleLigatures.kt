package pdfutil

import java.io.File

// WARNING: this is highly opportunistic and not unambiguous, since different input files could use different fonts.
val ligatureMap =
    mapOf(
        1 to "*",
        131 to "*",
        206 to "*",
        297 to "fb",
        298 to "ff",
        300 to "fh",
        322 to "fj",
        325 to "fk",
        326 to "ffk",
        332 to "ft",
        335 to "fft",
        414 to "tf",
        415 to "ti",
        425 to "tt",
        426 to "ttf",
        427 to "tti",
        976 to "f",
        2868 to "_0",
        2869 to "_1",
        2870 to "_2",
        2871 to "_3",
        2872 to "_4",
        2873 to "_5",
        2874 to "_6",
        2875 to "_7",
        2876 to "_8",
        2877 to "_9",
    )

val cidPattern = Regex("\\(cid:(\\d+)\\)")
val softHyphenPattern = Regex("\u00AD\n?\n?")
val sourceDir = File("documents")
val cleanedDir = File(sourceDir, "cleaned")

// https://www.amp-what.com/unicode/search/ligature from #64256 to #64262
val unicodeLigatures = mapOf(
    'ﬀ' to "ff",
    'ﬁ' to "fi",
    'ﬂ' to "fl",
    'ﬃ' to "ffi",
    'ﬄ' to "ffl",
    'ﬅ' to "st",
    'ﬆ' to "st",
)

fun main() {

    if (!sourceDir.exists() || !sourceDir.isDirectory) {
        error("Source directory '${sourceDir.path}' does not exist or is not a directory.")
    }

    cleanedDir.mkdirs()

    val txtFiles =
        sourceDir
            .listFiles { file -> file.isFile && file.extension.equals("txt", ignoreCase = true) }
            ?.sortedBy { it.name }
            .orEmpty()
    val highUnicodeCounts = mutableMapOf<Char, Int>()

    if (txtFiles.isEmpty()) {
        println("No .txt files found in '${sourceDir.path}'.")
    } else {
        val foundCounts = mutableMapOf<Int, Int>()
        var totalReplacements = 0
        var totalUnicodeLigatureReplacements = 0

        txtFiles.forEach { inputFile ->
            val content = inputFile.readText()
            val afterCid =
                cidPattern.replace(content) { match ->
                    val code = match.groupValues[1].toInt()
                    foundCounts[code] = (foundCounts[code] ?: 0) + 1

                    val replacement = ligatureMap[code]
                    if (replacement != null) {
                        totalReplacements++
                        replacement
                    } else {
                        match.value
                    }
                }
            val afterLigs = afterCid.map { ch ->
                val replacement = unicodeLigatures[ch]
                if (replacement != null) {
                    totalUnicodeLigatureReplacements++
                    replacement
                } else {
                    ch.toString()
                }
            }.joinToString("")
            val cleaned = softHyphenPattern.replace(afterLigs, "")
            // TODO (harder): also remove ascii hyphen-minus when used as a soft hyphen.
            //      This needs some heuristics to avoid removing "hin- und her-Striche". Maybe check that the first word on the next line is not a dictionary word (at least not "und", "oder", "sowie", etc). Possibly check that the concatenated word _is_ a dictionary word.
            //      Or maybe this can all be done by the LLM with the summary-extraction prompt??

            // TODO: remove superfluous empty lines ("\n\n"). We can recognize that those are not paragraph breaks when they occur very often and with less than, say 100 characters in-between.
            //      Still, it's a bit of heuristic that can also go wrong. Let's ask Claude if they have better ideas.
            content.forEach { ch ->
                if (ch.code > 255) {
                    highUnicodeCounts[ch] = (highUnicodeCounts[ch] ?: 0) + 1
                }
            }

            File(cleanedDir, inputFile.name).writeText(cleaned)
        }

        println("Processed ${txtFiles.size} files.")
        println("Wrote cleaned files to '${cleanedDir.path}'.")
        println("Replaced $totalReplacements ligature placeholders.")
        println("Replaced $totalUnicodeLigatureReplacements Unicode ligature characters.")

        println()
        println("Found cid codes:")
        foundCounts
            .entries
            .sortedByDescending { it.value }
            .forEach { (code, count) ->
                val replacement = ligatureMap[code] ?: "<unmapped>"
                println("(cid:$code) -> '$replacement' : $count")
            }

        println()
        println("High Unicode characters (code > 255):")
        highUnicodeCounts
            .entries
            .sortedByDescending { it.value }
            .forEach { (ch, count) ->
                println("U+${ch.code.toString(16).uppercase().padStart(4, '0')} '$ch' : $count")
            }
    }

}