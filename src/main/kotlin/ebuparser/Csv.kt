package ebuparser

fun parseCsv(
    text: String,
    delimiter: Char = ',',
): List<List<String>> {
    val rows = mutableListOf<List<String>>()
    var row = mutableListOf<String>()
    val field = StringBuilder()
    var quoted = false
    var index = 0
    val normalized = text.replace("\r\n", "\n").removePrefix("﻿")

    fun endField() {
        row += field.toString()
        field.clear()
    }

    while (index < normalized.length) {
        val char = normalized[index]
        when {
            quoted && char == '"' && normalized.getOrNull(index + 1) == '"' -> {
                field.append('"')
                index++
            }
            char == '"' && (quoted || field.isEmpty()) -> quoted = !quoted
            quoted -> field.append(char)
            char == delimiter -> endField()
            char == '\n' -> {
                endField()
                rows += row
                row = mutableListOf()
            }
            else -> field.append(char)
        }
        index++
    }
    if (field.isNotEmpty() || row.isNotEmpty()) {
        endField()
        rows += row
    }
    return rows
}

fun escapeCsv(value: String): String {
    val escaped = value.replace("\"", "\"\"")
    return if (escaped.contains(',') || escaped.contains('"') || escaped.contains('\n')) "\"$escaped\"" else escaped
}

fun toCsv(rows: List<List<String>>): String = rows.joinToString("\n") { row -> row.joinToString(",") { escapeCsv(it) } } + "\n"
