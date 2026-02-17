package ebuparser

import io.github.sashirestela.cleverclient.client.OkHttpClientAdapter
import io.github.sashirestela.openai.SimpleOpenAI

val lemonadeAddress = "http://127.0.0.1:8001"

val llmServer = SimpleOpenAI.builder()
    .apiKey("lemonade")  // dummy key for local server
    .baseUrl(lemonadeAddress)  // point at Lemonade
    .clientAdapter(OkHttpClientAdapter())
    .build()

// TODO: use proper function
val prompt = readFile('src/main/resources/extraction-prompt.txt')

fun extract(fileName: String, fullText: String): SimpleSummary {
    // send the prompt concatenated with the fullText to Lemonade
    llmServer.completions()
    // separate the 'thinking' part (if there is any) from the result
    // and append it with the current time and filename to "thoughts.log"
    return SimpleSummary(fileName, // here the non-thinking part
        )
}

val outputFilename = "summaries.txt"

fun main() {
    // list the directory ./documents and sort the files by size ascending
    // for each file, call the extract() function with the file's text content
    // store the results in File(outputFilename) separated by '\n---\n'
}