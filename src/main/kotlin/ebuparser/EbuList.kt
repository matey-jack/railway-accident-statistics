package ebuparser

import ebustats.aliases
import java.io.File
import java.nio.charset.Charset

const val EBU_LIST = "EBU-lists/EBU 01_Open_Data_abgeschlossene_Untersuchungen.csv"

// One row of the official list of completed investigations.
data class EbuEntry(
    val date: String,
    val location: String,
    val eventType: String,
    val lineNumber: String,
    val kilometre: String,
)

fun readEbuList(path: String = EBU_LIST): List<EbuEntry> =
    parseCsv(File(path).readText(Charset.forName("windows-1252")), ';')
        .drop(1)
        .filter { it.size >= 5 }
        .map { EbuEntry(it[0].trim(), it[1].trim(), it[2].trim().let { type -> aliases[type] ?: type }, it[3].trim(), it[4].trim()) }

data class EbuMatch(
    val entry: EbuEntry?,
    val issue: String?,
)

private fun locationWords(location: String): Set<String> =
    location
        .lowercase()
        .split(Regex("""[^\p{L}]+"""))
        .filter { it.length >= 3 }
        .toSet()

// The list has no report number, so the date from the summary's title is the key.
// Some dates have several entries; then the location decides.
fun findEbuEntry(
    entries: List<EbuEntry>,
    title: ReportTitle,
): EbuMatch {
    if (title.date.isEmpty()) return EbuMatch(null, "kein Datum im Titel, daher kein Eintrag aus der EBU-Liste")
    val candidates = entries.filter { it.date == title.date }
    if (candidates.size <= 1) {
        return EbuMatch(candidates.singleOrNull(), if (candidates.isEmpty()) "kein Eintrag in der EBU-Liste zum ${title.date}" else null)
    }
    val titleWords = locationWords(title.location)
    val scored = candidates.map { it to (locationWords(it.location) intersect titleWords).size }
    val best = scored.maxOf { it.second }
    val bestEntries = scored.filter { it.second == best }
    return if (best > 0 && bestEntries.size == 1) {
        EbuMatch(bestEntries.single().first, null)
    } else {
        EbuMatch(null, "mehrere Einträge in der EBU-Liste zum ${title.date}, keiner passt eindeutig zum Ort")
    }
}
