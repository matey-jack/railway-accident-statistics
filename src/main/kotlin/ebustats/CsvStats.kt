package ebustats

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

fun readEvents(fileName: String): List<Event> {
    val result = mutableListOf<Event>()
    // TODO read the CSV file into result
    return result
}

fun groupEvents(events: List<Event>): List<GroupedEvents> {
    val result = mutableListOf<GroupedEvents>()
    // TODO for each month between the earliest and latest .startDate, create a GroupedEvent and count all event types for that month
    return result
}

object CsvStats {
    fun main() {
        val events = readEvents("EBU-lists/EBU 01_Open_Data_abgeschlossene_Untersuchungen.csv")
        val grouped = groupEvents(events)
        // TODO write 'grouped' to File("grouped-events.csv")
    }
}