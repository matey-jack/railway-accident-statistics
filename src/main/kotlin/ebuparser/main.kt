package ebuparser

import io.github.sashirestela.cleverclient.client.OkHttpClientAdapter
import io.github.sashirestela.openai.SimpleOpenAI
import io.github.sashirestela.openai.domain.chat.ChatMessage
import io.github.sashirestela.openai.domain.chat.ChatRequest
import java.io.File

const val LEMONADE_URL = "http://127.0.0.1:8001"
const val MODEL = "Qwen3-14B-GGUF"
const val OUTPUT_FILENAME = "summaries.txt"

val llmServer =
    SimpleOpenAI.builder()
        .apiKey("lemonade") // dummy key for local server
        .baseUrl(LEMONADE_URL)
        .clientAdapter(OkHttpClientAdapter())
        .build()

val prompt = File("src/main/resources/extraction-prompt.txt").readText()

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

    return SimpleSummary(fileName, content)
}

fun main() {
    val statsWriter = StatsWriter(LEMONADE_URL, "stats.txt")
    val documentsDir = File("documents")
    val files =
        documentsDir.listFiles { file -> file.isFile && file.extension == "txt" }
            ?.sortedBy { it.length() }
            ?: emptyList()

    val outputFile = File(OUTPUT_FILENAME)

    for (file in files) {
        try {
            val content = file.readText()
            val summary = extract(file.name, content)
            outputFile.appendText(summary.asOutput + "\n---\n")
            println("Processed: ${file.name}")
            statsWriter.writeFor(file.name)
        } catch (e: Exception) {
            println("Error processing ${file.name}: ${e.message}")
        }
    }

    println("Summary written to $OUTPUT_FILENAME")
}
