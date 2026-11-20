package llmstats

import java.io.File
import kotlin.math.abs

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

    val rows =
        old.keys.intersect(new.keys).map { file ->
            val (oldIn, oldOut) = old[file]!!
            val (newIn, newOut) = new[file]!!
            Pair((newIn - oldIn).toDouble() / oldIn * 100, (newOut - oldOut).toDouble() / oldOut * 100)
        }

    val n = rows.size.toDouble()
    val avgInputDiff = rows.sumOf { it.first } / n
    val avgAbsInputDiff = rows.sumOf { abs(it.first) } / n
    val avgOutputDiff = rows.sumOf { it.second } / n
    val avgAbsOutputDiff = rows.sumOf { abs(it.second) } / n

    println("Input  - avg diff: %.1f, avg abs diff: %.1f".format(avgInputDiff, avgAbsInputDiff))
    println("Output - avg diff: %.1f, avg abs diff: %.1f".format(avgOutputDiff, avgAbsOutputDiff))

    val outputDiffs = rows.map { it.second }.sortedDescending()
    println("Most grown: " + outputDiffs.take(10).joinToString(", ") { it.toString() })
    println("Most shrunk: " + outputDiffs.reversed().take(10).joinToString(", ") { it.toString() })

    val shrunkFiles =
        old.keys
            .intersect(new.keys)
            .map { file ->
                val (oldIn, oldOut) = old[file]!!
                val (newIn, newOut) = new[file]!!
                Triple(file, (newOut - oldOut).toDouble() / oldOut * 100, newOut - oldOut)
            }.filter { (_, rel, _) -> rel < 0 }
            .sortedBy { (_, _, abs) -> abs }
    println("\nFiles with smaller output token count (biggest absolute decrease last):")
    for ((file, rel, absDiff) in shrunkFiles) {
        println("  $file  rel: ${"%.1f".format(rel)}%  abs: $absDiff")
    }

    /*
Input  - avg diff: -2392.2, avg abs diff: 2392.2
Output - avg diff: -128.6, avg abs diff: 378.8
Most grown: 2421, 1803, 1497, 1321, 1248, 1224, 1113, 1016, 938, 861
Most shrunk: -2783, -2782, -2425, -2064, -2041, -2035, -1886, -1795, -1761, -1633

     */
}
