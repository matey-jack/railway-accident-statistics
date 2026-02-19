package ebuparser

import io.github.sashirestela.cleverclient.client.OkHttpClientAdapter
import io.github.sashirestela.openai.SimpleOpenAI
import io.github.sashirestela.openai.domain.chat.ChatMessage
import io.github.sashirestela.openai.domain.chat.ChatRequest
import okhttp3.OkHttpClient
import windows.SleepPreventer
import java.io.File
import java.util.concurrent.TimeUnit

const val LEMONADE_URL = "http://127.0.0.1:8000"
const val MODEL = "Qwen3-14B-GGUF"
const val OUTPUT_FILENAME = "summaries.md"

val llmServer =
    SimpleOpenAI.builder()
        .apiKey("lemonade") // dummy key for local server
        .baseUrl(LEMONADE_URL)
        .clientAdapter(
            OkHttpClientAdapter(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.SECONDS) // No timeout for streaming
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build(),
            ),
        )
        .build()

val prompt = File("src/main/resources/extraction-prompt.txt").readText()

fun getAlreadyProcessedFilenames(outputFile: File): Set<String> {
    if (!outputFile.exists()) {
        return emptySet()
    }

    val processedFiles = mutableSetOf<String>()
    val fileContent = outputFile.readText()

    val pattern = Regex("""---\nfile: (.+)$""", RegexOption.MULTILINE)
    val matches = pattern.findAll(fileContent)
    processedFiles.addAll(matches.map { it.groupValues[1] })
    println("Skipping ${processedFiles.size} already processed files.")
    return processedFiles
}

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
    val outputFile = File(OUTPUT_FILENAME)

    val processedFiles = getAlreadyProcessedFilenames(outputFile)

    val files =
        documentsDir.listFiles { file -> file.isFile && file.extension == "txt" && file.name !in processedFiles }
            ?.sortedBy { it.length() }
            ?: emptyList()

    if (System.getProperty("os.name").lowercase().contains("win")) {
        SleepPreventer.preventSleep()
    }
    var errorCount = 0
    for (file in files) {
        try {
            val content = file.readText()
            println("Starting: ${file.name}")
            val summary = extract(file.name, content)
            outputFile.appendText(summary.asOutput + "\n\n---\n")
            statsWriter.writeFor(file.name)
            println("Processed: ${file.name}")
        } catch (e: Exception) {
            println("Error processing ${file.name}: ${e.message}")
            errorCount++
            if (errorCount == 3) {
                throw e
            }
        }
    }

    println("Summary written to $OUTPUT_FILENAME")
}
