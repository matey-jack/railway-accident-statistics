package llmstats

import java.io.File

fun main() {
    val oldStats = File("results/bedrock/old-stats.csv").readLines()
    val newStats = File("results/bedrock/stats.csv").readLines()

    fun parseTokens(lines: List<String>): Map<String, Pair<Int, Int>> {
        val header = lines.first().split(",")
        val fileIdx = header.indexOf("file")
        val inputIdx = header.indexOf("input_tokens")
        val outputIdx = header.indexOf("output_tokens")
        return lines.drop(1).associate { line ->
            val cols = line.split(",")
            cols[fileIdx] to (cols[inputIdx].toInt() to cols[outputIdx].toInt())
        }
    }

    val old = parseTokens(oldStats)
    val new = parseTokens(newStats)

    val out = File("results/bedrock/joined-stats.csv")
    out.bufferedWriter().use { w ->
        w.write("file,old_input_tokens,input_tokens,old_output_tokens,output_tokens\n")
        for (file in (old.keys.intersect(new.keys)).toSortedSet()) {
            val (oldIn, oldOut) = old[file] ?: (0 to 0)
            val (newIn, newOut) = new[file] ?: (0 to 0)
            w.write("$file,$oldIn,$newIn,$oldOut,$newOut\n")
        }
    }
    println("Written ${out.path}")
}
