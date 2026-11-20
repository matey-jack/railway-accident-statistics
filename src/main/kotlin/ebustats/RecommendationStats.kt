package ebustats

import java.io.File

fun groupRecommendations() {
    val inputFile = File("EBU-lists/02_Open_Data_Sicherheitsempfehlungen.csv")
    val lines = inputFile.readLines()
    val header = lines.first()
    val allHeaderFields = header.split(",")
    val keyIndices = listOf(0, 1, 2, 4)
    val headerFields = keyIndices.map { allHeaderFields[it] }

    val counts = mutableMapOf<List<String>, Int>()
    for (line in lines.drop(1)) {
        if (line.isBlank()) continue
        val fields = line.split(",", limit = 6)
        val key = keyIndices.map { fields[it] }
        counts[key] = counts.getOrDefault(key, 0) + 1
    }

    val outputFile = File("SicherheitsempfehlungenGrouped.csv")
    outputFile.bufferedWriter().use { writer ->
        writer.write(headerFields.joinToString(",") + ",Anzahl Empfehlungen")
        writer.newLine()
        for ((key, count) in counts) {
            writer.write(key.joinToString(",") + ",$count")
            writer.newLine()
        }
    }
}

fun main() {
    groupRecommendations()
}
