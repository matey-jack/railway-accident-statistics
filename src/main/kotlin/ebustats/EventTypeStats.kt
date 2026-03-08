package ebustats

import java.io.File
import java.time.LocalDate

data class Event(
    // first column in CSV, "Zeitpunkt des Ereignisses"
    val date: LocalDate,
    // third column in CSV, "Ereignisart"
    val type: String,
)

data class GroupedEvents(
    val startDate: LocalDate,
    // maps event.type to count of this event type in the interval
    val counts: Map<String, Int>,
)

val aliases =
    mapOf(
        Pair("Unzulässige Einfahrt in einen besetzten Gleisabschnitt", "Einfahrt in besetzten Abschnitt"),
        Pair("Einfahrt in einen besetzten Gleisabschnitt", "Einfahrt in besetzten Abschnitt"),
        Pair("Zugentgleisung", "Entgleisung"),
        Pair("Zugkollision", "Kollision"),
        Pair("Zusammenstoß", "Kollision"),
        // not to all: "Aufprall" bezieht sich auf Objekte, die keine Züge sind. Aber später wurde dies auch als Kollision gezählt und so machen wir es nun auch.
        Pair("Aufprall", "Kollision"),
        Pair("Brandereignis", "Fahrzeugbrand"),
        Pair("Unregelmäßigkeit an Eisenbahnfahrzeugen", "Störung am Fahrzeug"),
    )

fun readEvents(fileName: String): List<Event> {
    val result = mutableListOf<Event>()

    File(fileName).bufferedReader(Charsets.ISO_8859_1).use { reader ->
        // Skip header line
        reader.readLine()

        var line = reader.readLine()
        while (line != null) {
            val parts = line.split(';')
            if (parts.isNotEmpty()) {
                try {
                    val dateStr = parts[0]
                    // Parse date format "dd.MM.yyyy"
                    val dateParts = dateStr.split('.')
                    if (dateParts.size == 3) {
                        val date =
                            LocalDate.of(
                                dateParts[2].toInt(),
                                dateParts[1].toInt(),
                                dateParts[0].toInt(),
                            )
                        var type = if (parts.size > 2) parts[2] else "Unknown"
                        if (type == "Unknown") {
                            val text = if (parts.size >= 5) " – ${parts[5]}" else ""
                            println("Unknown Type for: $date$text")
                        }
                        if (aliases.containsKey(type)) {
                            type = aliases[type]!!
                        }
                        result.add(Event(date, type))
                    }
                } catch (_e: Exception) {
                    // Skip lines that can't be parsed
                }
            }
            line = reader.readLine()
        }
    }

    return result
}

fun groupEvents(events: List<Event>): List<GroupedEvents> {
    val result = mutableListOf<GroupedEvents>()

    if (events.isEmpty()) {
        return result
    }

    val sortedEvents = events.sortedBy { it.date }
    val minDate = sortedEvents.first().date
    val maxDate = sortedEvents.last().date

    // Start from the quarter containing minDate
    var currentDate = minDate.withDayOfMonth(1)
    val quarterStartMonths = listOf(1, 4, 7, 10)
    val quarterMonth = quarterStartMonths.findLast { it <= currentDate.monthValue } ?: 1
    currentDate = currentDate.withMonth(quarterMonth)

    while (currentDate <= maxDate) {
        val nextQuarterMonth = (currentDate.monthValue + 3) % 12
        val nextDate =
            if (nextQuarterMonth <= currentDate.monthValue) {
                currentDate.withYear(currentDate.year + 1).withMonth(nextQuarterMonth)
            } else {
                currentDate.withMonth(nextQuarterMonth)
            }

        val counts = mutableMapOf<String, Int>()

        for (event in sortedEvents) {
            if (!event.date.isBefore(currentDate) && event.date.isBefore(nextDate)) {
                counts[event.type] = counts.getOrDefault(event.type, 0) + 1
            }
        }

        result.add(GroupedEvents(currentDate, counts))
        currentDate = nextDate
    }

    return result
}

fun main() {
    val events = readEvents("EBU-lists/EBU 01_Open_Data_abgeschlossene_Untersuchungen.csv")
    val grouped = groupEvents(events)

    // Collect all unique event types
    val allEventTypes = grouped.flatMap { it.counts.keys }.distinct().sorted()

    val outputFile = File("grouped-events-by-quarter.csv")
    outputFile.bufferedWriter().use { writer ->
        // Write header
        writer.write("month," + allEventTypes.joinToString(",") + "\n")

        // Write data
        for (group in grouped) {
            writer.write(group.startDate.toString())
            for (eventType in allEventTypes) {
                writer.write(",${group.counts.getOrDefault(eventType, 0)}")
            }
            writer.write("\n")
        }
    }
}
