package ebuparser

import io.github.sashirestela.cleverclient.client.OkHttpClientAdapter
import io.github.sashirestela.openai.SimpleOpenAI
import io.github.sashirestela.openai.domain.chat.ChatMessage
import io.github.sashirestela.openai.domain.chat.ChatRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient
import software.amazon.awssdk.services.bedrockruntime.model.*
import windows.SleepPreventer
import java.io.File
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import kotlin.io.path.Path

const val LEMONADE_URL = "http://127.0.0.1:8000"
const val LEMONADE_STATS_URL = "$LEMONADE_URL/api/v1/stats"
const val LEMONADE_MODEL = "Qwen3-14B-GGUF"
const val BEDROCK_MODEL = "amazon.nova-2-lite-v1:0"
const val BEDROCK_INFERENCE_PROFILE = "global.amazon.nova-2-lite-v1:0"
const val BEDROCK_SERVICE_TIER = "flex"
const val BEDROCK_MAX_TOKENS = 3000
const val OUTPUT_FILENAME = "summaries.md"
const val LEMONADE_OUTPUT_DIR = "results/lemonade"
const val BEDROCK_OUTPUT_DIR = "results/bedrock"

val llmServer =
    SimpleOpenAI
        .builder()
        .apiKey("lemonade")
        .baseUrl(LEMONADE_URL)
        .clientAdapter(
            OkHttpClientAdapter(
                OkHttpClient
                    .Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build(),
            ),
        ).build()

val summarizationPrompt = File("src/main/resources/extraction-prompt.txt").readText()

data class ExtractResult(
    val summary: SimpleSummary,
    val statsText: String,
)

enum class LlmProvider {
    LEMONADE,
    BEDROCK,
}

fun getAlreadyProcessedFilenames(outputFile: File): Set<String> {
    if (!outputFile.exists()) {
        println("'$outputFile' doesn't exist. Creating it.")
        // this is needed for the Regex below to also find the first entry.
        // and the new line at the start is needed, so it doesn't look like a preamble or whatever the Markdown term for that is.
        outputFile.appendText(SECTION_SEPARATOR)
        return emptySet()
    }

    val processedFiles = mutableSetOf<String>()
    val fileContent = outputFile.readText()

    val pattern = Regex("""---\r?\nfile: (.+)$""", RegexOption.MULTILINE)
    val matches = pattern.findAll(fileContent)
    processedFiles.addAll(matches.map { it.groupValues[1] })
    println("Skipping ${processedFiles.size} already processed files.")
    return processedFiles
}

fun extract(
    fileName: String,
    fullText: String,
    prompt: String = summarizationPrompt,
): SimpleSummary = extractWithLemonade(fileName, fullText, prompt).summary

fun extractWithLemonade(
    fileName: String,
    fullText: String,
    prompt: String = summarizationPrompt,
): ExtractResult {
    val startNs = System.nanoTime()
    val chatRequest =
        ChatRequest
            .builder()
            .model(LEMONADE_MODEL)
            .message(ChatMessage.SystemMessage.of(prompt))
            .message(ChatMessage.UserMessage.of(fullText))
            .build()

    val streamResponse = llmServer.chatCompletions().createStream(chatRequest).join()
    val content =
        streamResponse
            .filter { it.choices.isNotEmpty() && it.firstContent() != null }
            .map { it.firstContent().orEmpty() }
            .toList()
            .joinToString("")

    val localLatencyMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
    val lemonadeStats = fetchLemonadeStatsJson() ?: "unavailable"
    val statsText =
        buildString {
            appendLine("provider: lemonade")
            appendLine("model: $LEMONADE_MODEL")
            appendLine("local_call_duration_ms: $localLatencyMs")
            appendLine("server_stats: $lemonadeStats")
        }

    return ExtractResult(SimpleSummary(fileName, content), statsText)
}

fun extractWithBedrock(
    bedrockClient: BedrockRuntimeClient,
    fileName: String,
    fullText: String,
    prompt: String = summarizationPrompt,
): ExtractResult {
    val startNs = System.nanoTime()
    val request =
        ConverseRequest
            .builder()
            .modelId(BEDROCK_INFERENCE_PROFILE)
            .system(SystemContentBlock.builder().text(prompt).build())
            .messages(
                Message
                    .builder()
                    .role(ConversationRole.USER)
                    .content(ContentBlock.builder().text(fullText).build())
                    .build(),
            ).inferenceConfig(
                InferenceConfiguration
                    .builder()
                    .maxTokens(BEDROCK_MAX_TOKENS)
                    .build(),
            ).serviceTier(
                ServiceTier
                    .builder()
                    .type(ServiceTierType.FLEX)
                    .build(),
            ).build()

    val response = bedrockClient.converse(request)
    val localLatencyMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
    val content =
        response
            .output()
            .message()
            .content()
            .mapNotNull { it.text() }
            .joinToString("")

    val usage = response.usage()
    val resolvedTier = response.serviceTier()?.typeAsString() ?: BEDROCK_SERVICE_TIER
    // '_' in name is to get input_ and output aligned, so cost constants below compare better.
    val input_Tokens = usage?.inputTokens() ?: 0
    val outputTokens = usage?.outputTokens() ?: 0
    val totalTokens = usage?.totalTokens() ?: 0
    val input_Cost = input_Tokens * 0.000195 / 1000.0
    val outputCost = outputTokens * 0.001635 / 1000.0
    val totalCost = input_Cost + outputCost
    val statsText =
        listOf(
            fileName,
            "bedrock",
            BEDROCK_MODEL,
            BEDROCK_INFERENCE_PROFILE,
            BEDROCK_MAX_TOKENS.toString(),
            BEDROCK_SERVICE_TIER,
            resolvedTier,
            localLatencyMs.toString(),
            (response.metrics()?.latencyMs() ?: "").toString(),
            input_Tokens.toString(),
            outputTokens.toString(),
            totalTokens.toString(),
            response.stopReasonAsString(),
            "%.6f".format(input_Cost),
            "%.6f".format(outputCost),
            "%.6f".format(totalCost),
        ).joinToString(",")

    return ExtractResult(SimpleSummary(fileName, content), statsText)
}

fun printDebugInfo() {
    println("Default charset: ${Charset.defaultCharset()}")
    println("file.encoding: ${System.getProperty("file.encoding")}")
    println("stdout.encoding: ${System.getProperty("stdout.encoding")}")
    println("Encoding test: äöüß")
}

fun isLemonadeRunning(): Boolean {
    val client =
        OkHttpClient
            .Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()
    val request =
        Request
            .Builder()
            .url(LEMONADE_STATS_URL)
            .get()
            .build()

    return try {
        client.newCall(request).execute().use { it.isSuccessful }
    } catch (_: Exception) {
        false
    }
}

fun fetchLemonadeStatsJson(): String? {
    val client = OkHttpClient()
    val request =
        Request
            .Builder()
            .url(LEMONADE_STATS_URL)
            .get()
            .build()

    return try {
        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                response.body.string()
            } else {
                null
            }
        }
    } catch (_: Exception) {
        null
    }
}

const val STATS_CSV_HEADER =
    "file,provider,model_id,inference_profile_id,max_tokens_requested," +
        "service_tier_requested,service_tier_resolved,local_call_duration_ms," +
        "bedrock_latency_ms,input_tokens,output_tokens,total_tokens," +
        "stop_reason,input_cost_usd,output_cost_usd,total_cost_usd"

fun appendStats(statsFile: File, taskId: String, statsText: String) {
    statsFile.parentFile?.mkdirs()
    if (!statsFile.exists() || statsFile.length() == 0L) {
        statsFile.appendText(STATS_CSV_HEADER + "\n")
    }
    statsFile.appendText(statsText + "\n")
}

fun cleanSummary(summary: String): String {
    var cleaned = summary.replace(SECTION_SEPARATOR, "\n")
    cleaned = cleaned.replace(Regex("""^(#+) \*\*(.+?)\*\*""", RegexOption.MULTILINE), "$1 $2")
    cleaned = normalizeHeadingLevels(cleaned)
    return cleaned
}

// Reduces heading levels so the shallowest heading in the text becomes H1.
// Sub-headings are reduced by the same amount but clamped to H2 (##) so they remain sub-headings.
fun normalizeHeadingLevels(text: String): String {
    val headingRegex = Regex("""^(#+) (.+)""", RegexOption.MULTILINE)
    val minLevel = headingRegex.findAll(text).map { it.groupValues[1].length }.minOrNull() ?: return text
    val reduction = minLevel - 1
    if (reduction == 0) return text

    return headingRegex.replace(text) { match ->
        val currentLevel = match.groupValues[1].length
        val newLevel = if (currentLevel == minLevel) 1 else maxOf(currentLevel - reduction, 2)
        if (newLevel == currentLevel) {
            match.value
        } else {
            println("Fixing line ${match.value}")
            "#".repeat(newLevel) + " " + match.groupValues[2]
        }
    }
}

fun isFileInSubsetForTest(filename: String): Boolean {
    val number = filename.take(3).toIntOrNull() ?: return false
    return number <= 10 || number % 10 == 0
}

fun main() {
    printDebugInfo()

    val provider: LlmProvider
    val outputDirectory: String
    val bedrockClient: BedrockRuntimeClient?

    if (isLemonadeRunning()) {
        println("Lemonade is running. Continuing with local processing.")
        provider = LlmProvider.LEMONADE
        outputDirectory = LEMONADE_OUTPUT_DIR
        bedrockClient = null
    } else {
        println("Lemonade is not running. Checking AWS profile '$AWS_PROFILE'.")
        val identity = getCallerIdentityForProfile(AWS_PROFILE)
            ?: error("Neither Lemonade nor a logged-in AWS profile '$AWS_PROFILE' is available.")

        printCallerIdentity(identity)
        val region = resolveAwsRegion()
        val credentialsProvider =
            ProfileCredentialsProvider
                .builder()
                .profileName(AWS_PROFILE)
                .build()

        provider = LlmProvider.BEDROCK
        outputDirectory = BEDROCK_OUTPUT_DIR
        bedrockClient =
            BedrockRuntimeClient
                .builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build()

        println(
            "Using Bedrock model '$BEDROCK_MODEL' via inference profile '$BEDROCK_INFERENCE_PROFILE' " +
                    "with service tier '$BEDROCK_SERVICE_TIER' in region '${region.id()}'.",
        )
    }
    val statsFile = Path(outputDirectory).resolve("stats.csv").toFile()

    val documentsDir = File("documents")
    val outputFile = Path(outputDirectory).resolve(OUTPUT_FILENAME).toFile()
    outputFile.parentFile?.mkdirs()

    val processedFiles = getAlreadyProcessedFilenames(outputFile)

    val files =
        documentsDir
            .listFiles { file ->
                file.isFile && file.extension == "md"
                        && file.name !in processedFiles
//                    && isFileInSubsetForTest(file.name)
            }
            ?.sortedBy { it.name }
            ?: emptyList()

    if (System.getProperty("os.name").lowercase().contains("win")) {
        SleepPreventer.preventSleep()
    }

    var errorCount = 0
    bedrockClient.use { bedrock ->
        for (file in files.take(1)) {
            try {
                val content = file.readText()
                println("Starting: ${file.name}")

                val result =
                    when (provider) {
                        LlmProvider.LEMONADE -> extractWithLemonade(file.name, content)
                        LlmProvider.BEDROCK -> extractWithBedrock(requireNotNull(bedrock), file.name, content)
                    }
                val cleanedSummary = result.summary.copy(summary = cleanSummary(result.summary.summary))
                outputFile.appendText("\n\n---\n" + cleanedSummary.asOutput)
                appendStats(statsFile, file.name, result.statsText)
                println("Processed: ${file.name}")
            } catch (e: Exception) {
                println("Error processing ${file.name}: ${e.message}")
                errorCount++
                if (errorCount == 3) {
                    throw e
                }
            }
        }
    }

    println("Summary written to ${outputFile.path}")
}
