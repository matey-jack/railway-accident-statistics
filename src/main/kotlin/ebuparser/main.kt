package ebuparser

import io.github.sashirestela.cleverclient.client.OkHttpClientAdapter
import io.github.sashirestela.openai.SimpleOpenAI
import io.github.sashirestela.openai.domain.chat.ChatMessage
import io.github.sashirestela.openai.domain.chat.ChatRequest
import java.io.File
import java.time.LocalDateTime

val lemonadeAddress = "http://127.0.0.1:8001"

val llmServer =
    SimpleOpenAI.builder()
        .apiKey("lemonade") // dummy key for local server
        .baseUrl(lemonadeAddress)
        .clientAdapter(OkHttpClientAdapter())
        .build()

val prompt = File("src/main/resources/extraction-prompt.txt").readText()

private const val MODEL = "Qwen3-14B-GGUF"

fun extract(
    fileName: String,
    fullText: String,
): SimpleSummary {
    // send the prompt concatenated with the fullText to Lemonade
    val chatRequest =
        ChatRequest.builder()
            .model(MODEL)
            .message(ChatMessage.UserMessage.of("$prompt\n$fullText"))
            .build()

    val streamResponse = llmServer.chatCompletions().createStream(chatRequest).join()
    val content =
        streamResponse
            .filter { it.choices.isNotEmpty() && it.firstContent() != null }
            .map { it.firstContent().orEmpty() }
            .toList()
            .joinToString("")

    // separate the 'thinking' part (if there is any) from the result
    val thinkingPattern = Regex(".*?</thinking>\\s*(.*)", RegexOption.DOT_MATCHES_ALL)
    val match = thinkingPattern.find(content)
    val finalContent =
        if (match != null) {
            match.groupValues[1].trim()
        } else {
            content
        }

    // append thinking part with the current time and filename to "thoughts.log"
    val thinkingContent =
        if (content.contains("</thinking>")) {
            content.substring(0, content.indexOf("</thinking>") + 10)
        } else {
            ""
        }

    if (thinkingContent.isNotEmpty()) {
        val timestamp = LocalDateTime.now()
        File("thoughts.log").appendText("[$timestamp] $fileName:\n$thinkingContent\n\n")
    }

    return SimpleSummary(fileName, finalContent)
}

const val outputFilename = "summaries.txt"

fun main() {
    // list the directory ./documents and sort the files by size ascending
    val documentsDir = File("documents")
    val files =
        documentsDir.listFiles { file -> file.isFile && file.extension == "txt" }
            ?.sortedBy { it.length() }
            ?: emptyList()

    val outputFile = File(outputFilename)
    outputFile.writeText("") // clear the file

    for (file in files) {
        try {
            val content = file.readText()
            val summary = extract(file.name, content)
            outputFile.appendText(summary.asOutput + "\n---\n")
            println("Processed: ${file.name}")
        } catch (e: Exception) {
            println("Error processing ${file.name}: ${e.message}")
        }
    }

    println("Summary written to $outputFilename")
}
