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

val shortParagraphPattern = Regex("\n\n([^\n]{60,100})\n\n([^\n]{60,100})\n\n")
val cidPattern = Regex("\\(cid:(\\d+)\\)")
val softHyphenPattern = Regex("\u00AD\n?\n?")
val conjunctions = "und|oder|sowie|bzw\\.?|als|wie|auch|sondern|plus"
val lineBreakHyphenPattern = Regex(
    // letter before hyphen, lowercase letter after newline, next word is not a conjunction
    "(?<=[\\wäöüÄÖÜß])-\n(?!(?:$conjunctions)(?:\\s|[^a-zA-ZäöüÄÖÜß]))([a-zäöüß])"
)
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

fun joinOneLineParagraphs(content: String): String {
    val paras = content
        .replace("\r\n", "\n")
        .split(Regex("\n *\n"))
        .toMutableList()
    var i = 0
    var merged = false
    while (i < paras.size - 1) {
        if (paras[i].isBlank()) {
            paras.removeAt(i)
            merged = false
        } else if ((merged || paras[i].length < 100) && paras[i+1].length < 100) {
            paras[i] = paras[i] + "\n" + paras[i+1]
            paras.removeAt(i+1)
            merged = true
        } else {
            merged = false
            i++
        }
    }
    return paras.joinToString("\n\n")
}

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

    if (txtFiles.isEmpty()) {
        println("No .txt files found in '${sourceDir.path}'.")
    } else {
        val foundCounts = mutableMapOf<Int, Int>()
        val highUnicodeCounts = mutableMapOf<Char, Int>()
        val shortParagraphs = mutableMapOf<String, Int>()

        txtFiles.forEach { inputFile ->
            val content = inputFile.readText()
            shortParagraphs[inputFile.name] = shortParagraphPattern.findAll(content).count()
            val afterLines = joinOneLineParagraphs(content)
            val afterCid =
                cidPattern.replace(afterLines) { match ->
                    val code = match.groupValues[1].toInt()
                    foundCounts[code] = (foundCounts[code] ?: 0) + 1
                    ligatureMap[code] ?: match.value
                }
            val afterLigs = afterCid.map { ch -> unicodeLigatures[ch] ?: ch.toString() }.joinToString("")
            val afterSoftHyphens = softHyphenPattern.replace(afterLigs, "")
            val cleaned = lineBreakHyphenPattern.replace(afterSoftHyphens) { it.groupValues[1] }
            content.forEach { ch ->
                if (ch.code > 255) {
                    highUnicodeCounts[ch] = (highUnicodeCounts[ch] ?: 0) + 1
                }
            }

            File(cleanedDir, inputFile.name).writeText(cleaned)
        }

        println("Processed ${txtFiles.size} files.")
        println("Wrote cleaned files to '${cleanedDir.path}'.")

//        println()
//        println("Found cid codes:")
//        foundCounts
//            .entries
//            .sortedByDescending { it.value }
//            .forEach { (code, count) ->
//                val replacement = ligatureMap[code] ?: "<unmapped>"
//                println("(cid:$code) -> '$replacement' : $count")
//            }
//
//        println()
//        println("High Unicode characters (code > 255):")
//        highUnicodeCounts
//            .entries
//            .sortedByDescending { it.value }
//            .forEach { (ch, count) ->
//                println("U+${ch.code.toString(16).uppercase().padStart(4, '0')} '$ch' : $count")
//            }
//
//        println()
//        println("Files with short paragraphs:")
//        shortParagraphs
//            .entries
//            .sortedByDescending { it.value }
//            .forEach { (fileName, count) ->
//                println("$fileName : $count")
//            }

    }

}