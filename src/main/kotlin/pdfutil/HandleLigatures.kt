package pdfutil

import java.io.File

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
        2869 to "_1",
        2870 to "_2",
        2871 to "_3",
    )

val cidPattern = Regex("\\(cid:(\\d+)\\)")
val sourceDir = File("documents")
val cleanedDir = File(sourceDir, "cleaned")

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
        var totalReplacements = 0

        txtFiles.forEach { inputFile ->
            val content = inputFile.readText()
            val cleaned =
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

            File(cleanedDir, inputFile.name).writeText(cleaned)
        }

        println("Processed ${txtFiles.size} files.")
        println("Wrote cleaned files to '${cleanedDir.path}'.")
        println("Replaced $totalReplacements ligature placeholders.")
        println()
        println("Found cid codes:")

        foundCounts
            .toSortedMap()
            .forEach { (code, count) ->
                val replacement = ligatureMap[code] ?: "<unmapped>"
                println("(cid:$code) -> '$replacement' : $count")
            }
    }

}