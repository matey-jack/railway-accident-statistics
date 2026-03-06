package ebuparser

import io.github.sashirestela.cleverclient.client.OkHttpClientAdapter
import io.github.sashirestela.openai.SimpleOpenAI
import io.github.sashirestela.openai.domain.chat.ChatMessage
import io.github.sashirestela.openai.domain.chat.ChatRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock
import software.amazon.awssdk.services.bedrockruntime.model.ConversationRole
import software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest
import software.amazon.awssdk.services.bedrockruntime.model.InferenceConfiguration
import software.amazon.awssdk.services.bedrockruntime.model.Message
import software.amazon.awssdk.services.bedrockruntime.model.ServiceTier
import software.amazon.awssdk.services.bedrockruntime.model.ServiceTierType
import software.amazon.awssdk.services.bedrockruntime.model.SystemContentBlock
import software.amazon.awssdk.services.sts.StsClient
import software.amazon.awssdk.services.sts.model.GetCallerIdentityRequest
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse
import windows.SleepPreventer
import java.io.File
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import kotlin.io.path.Path

const val LEMONADE_URL = "http://127.0.0.1:8000"
const val LEMONADE_MODEL = "Qwen3-14B-GGUF"
const val BEDROCK_MODEL = "amazon.nova-2-lite-v1:0"
const val BEDROCK_INFERENCE_PROFILE = "global.amazon.nova-2-lite-v1:0"
const val BEDROCK_SERVICE_TIER = "flex"
const val BEDROCK_MAX_TOKENS = 3000
const val OUTPUT_FILENAME = "summaries.md"
const val LEMONADE_OUTPUT_DIR = "results/lemonade"
const val BEDROCK_OUTPUT_DIR = "results/bedrock"
const val AWS_PROFILE = "llm-user"

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
        outputFile.appendText("\n---\n")
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
    val statsText =
        buildString {
            appendLine("provider: bedrock")
            appendLine("model_id: $BEDROCK_MODEL")
            appendLine("inference_profile_id: $BEDROCK_INFERENCE_PROFILE")
            appendLine("max_tokens_requested: $BEDROCK_MAX_TOKENS")
            appendLine("service_tier_requested: $BEDROCK_SERVICE_TIER")
            appendLine("service_tier_resolved: $resolvedTier")
            appendLine("local_call_duration_ms: $localLatencyMs")
            appendLine("bedrock_latency_ms: ${response.metrics()?.latencyMs()}")
            appendLine("input_tokens: ${usage?.inputTokens()}")
            appendLine("output_tokens: ${usage?.outputTokens()}")
            appendLine("total_tokens: ${usage?.totalTokens()}")
            appendLine("stop_reason: ${response.stopReasonAsString()}")
        }

    return ExtractResult(SimpleSummary(fileName, content), statsText)
}

fun printDebugInfo() {
    println("Default charset: ${Charset.defaultCharset()}")
    println("file.encoding: ${System.getProperty("file.encoding")}")
    println("stdout.encoding: ${System.getProperty("stdout.encoding")}")
    println("Encoding test: äöüß")
}

fun resolveAwsRegion(): Region {
    val regionName = System.getenv("AWS_REGION") ?: System.getenv("AWS_DEFAULT_REGION") ?: "us-east-1"
    return Region.of(regionName)
}

fun getCallerIdentityForProfile(profileName: String): GetCallerIdentityResponse? {
    return try {
        val region = resolveAwsRegion()
        ProfileCredentialsProvider
            .builder()
            .profileName(profileName)
            .build()
            .use { credentialsProvider ->
                StsClient
                    .builder()
                    .region(region)
                    .credentialsProvider(credentialsProvider)
                    .build()
                    .use { sts ->
                        sts.getCallerIdentity(GetCallerIdentityRequest.builder().build())
                    }
            }
    } catch (e: Exception) {
        println("AWS profile '$profileName' is not ready: ${e.message}")
        null
    }
}

fun printCallerIdentity(identity: GetCallerIdentityResponse) {
    println("aws sts get-caller-identity --profile $AWS_PROFILE")
    println(
        "{" +
            "\"UserId\":\"${identity.userId()}\"," +
            "\"Account\":\"${identity.account()}\"," +
            "\"Arn\":\"${identity.arn()}\"" +
            "}",
    )
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
            .url("$LEMONADE_URL/api/v1/stats")
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
            .url("$LEMONADE_URL/api/v1/stats")
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

fun appendStats(statsFile: File, taskId: String, statsText: String) {
    statsFile.parentFile?.mkdirs()
    statsFile.appendText("---\nfile: $taskId\n\n$statsText\n")
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
    val statsFile = Path(outputDirectory).resolve("stats.txt").toFile()

    val documentsDir = File("documents")
    val outputFile = Path(outputDirectory).resolve(OUTPUT_FILENAME).toFile()
    outputFile.parentFile?.mkdirs()

    val processedFiles = getAlreadyProcessedFilenames(outputFile)

    val files =
        documentsDir
            .listFiles { file -> file.isFile && file.extension == "txt" && file.name !in processedFiles }
            ?.sortedBy { it.length() }
            ?: emptyList()

    if (System.getProperty("os.name").lowercase().contains("win")) {
        SleepPreventer.preventSleep()
    }

    var errorCount = 0
    bedrockClient.use { bedrock ->
        for (file in files) {
            try {
                val content = file.readText()
                println("Starting: ${file.name}")

                val result =
                    when (provider) {
                        LlmProvider.LEMONADE -> extractWithLemonade(file.name, content)
                        LlmProvider.BEDROCK -> extractWithBedrock(requireNotNull(bedrock), file.name, content)
                    }

                outputFile.appendText(result.summary.asOutput + "\n\n---\n")
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
