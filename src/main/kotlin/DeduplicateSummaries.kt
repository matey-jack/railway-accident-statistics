import java.io.File

fun main() {
    val inputFile = File("summaries.md")
    val outputFile = File("distinct-summaries.md")
    
    val content = inputFile.readText()
    val sections = content.split(Regex("---\\r?\\n")).filter { it.isNotBlank() }
    
    val seenFilenames = mutableSetOf<String>()
    val distinctSections = mutableListOf<String>()
    
    // Add the first section as-is (it comes before the first ---)
    if (sections.isNotEmpty()) {
        distinctSections.add(sections[0])
    }
    
    // Process remaining sections
    for (i in 1 until sections.size) {
        val section = sections[i]
        
        // Extract filename from section
        val filenameMatch = Regex("^\\s*file:\\s*(.+?)\\s*$", RegexOption.MULTILINE).find(section)
        
        if (filenameMatch != null) {
            val filename = filenameMatch.groupValues[1].trim()
            
            if (!seenFilenames.contains(filename)) {
                seenFilenames.add(filename)
                distinctSections.add(section)
            } else {
                println("Skipping duplicate section for $filename")
            }
        } else {
            // If no filename found, add the section anyway
            distinctSections.add(section)
        }
    }
    
    // Write output with proper separator formatting
    val output = distinctSections.joinToString("\n---\n")
    outputFile.writeText("\n$output\n")
    
    println("Processed ${sections.size} sections, kept ${distinctSections.size} distinct sections")
    println("Output written to distinct-summaries.md")
}
