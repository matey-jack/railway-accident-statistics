package ebuparser

import com.openai.client.OpenAIClient
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.core.Timeout
import com.openai.models.ReasoningEffort
import com.openai.models.chat.completions.ChatCompletion
import com.openai.models.chat.completions.ChatCompletionCreateParams
import okhttp3.OkHttpClient
import okhttp3.Request
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.core.document.Document
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient
import software.amazon.awssdk.services.bedrockruntime.model.*
import software.amazon.bedrock.token.BedrockTokenGenerator
import windows.SleepPreventer
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintStream
import java.nio.charset.Charset
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import kotlin.io.path.Path
import kotlin.jvm.optionals.getOrNull

const val LEMONADE_URL = "http://127.0.0.1:8000"
const val LEMONADE_STATS_URL = "$LEMONADE_URL/api/v1/stats"
const val LEMONADE_MODEL = "Qwen3-14B-GGUF"

enum class BedrockApi {
    CONVERSE,
    MANTLE,
}

data class BedrockModel(
    val name: String,
    val inputDollarPer1m: Double,
    val outputDollarPer1m: Double,
    val api: BedrockApi,
)

// this is the listed flex price
val novaModel = BedrockModel("amazon.nova-2-lite-v1:0", 0.195, 1.635, BedrockApi.CONVERSE)

// this is the listed standard price with 50% discount.
// Gemma 4 is only served by the OpenAI-compatible bedrock-mantle endpoint, which has no cross-region inference profiles.
val gemma31Model = BedrockModel("google.gemma-4-31b", 0.17 / 2, 0.48 / 2, BedrockApi.MANTLE)

val BEDROCK_MODEL = gemma31Model
val BEDROCK_MODEL_ID =
    when (BEDROCK_MODEL.api) {
        BedrockApi.CONVERSE -> "global." + BEDROCK_MODEL.name
        BedrockApi.MANTLE -> BEDROCK_MODEL.name
    }
const val BEDROCK_SERVICE_TIER = "flex"

// this includes reasoning tokens!
const val BEDROCK_MAX_TOKENS = 8000
const val OUTPUT_FILENAME = "summaries.md"
const val LOG_FILENAME = "extraction-log.txt"
const val LEMONADE_OUTPUT_DIR = "results/lemonade"
const val BEDROCK_OUTPUT_DIR = "results/bedrock"

// Local inference can take many minutes for a long report.
private val noReadTimeout =
    Timeout
        .builder()
        .connect(Duration.ofSeconds(30))
        .write(Duration.ofSeconds(30))
        .read(Duration.ZERO)
        .request(Duration.ZERO)
        .build()

// Bedrock calls take well under a minute, even on the flex tier.
// A request that Bedrock silently drops would otherwise block the run forever,
// because there are no response headers to wait for and no HTTP/2 pings to detect a dead connection.
private val bedrockTimeout =
    Timeout
        .builder()
        .connect(Duration.ofSeconds(30))
        .write(Duration.ofSeconds(30))
        .read(Duration.ofMinutes(5))
        .request(Duration.ofMinutes(5))
        .build()

val llmServer: OpenAIClient =
    OpenAIOkHttpClient
        .builder()
        .apiKey("lemonade")
        .baseUrl("$LEMONADE_URL/v1")
        .timeout(noReadTimeout)
        .build()

val summarizationPrompt = File("src/main/resources/extraction-prompt.txt").readText()

// The format rules are in the system prompt, but reports are long, and the model follows them more reliably
// when they are repeated right where it starts writing.
val formatReminder = File("src/main/resources/extraction-format-reminder.txt").readText()

fun reportMessage(fullText: String): String = "<bericht>\n$fullText\n</bericht>\n\n$formatReminder"

data class ExtractResult(
    val summary: SimpleSummary,
    val statsText: String,
)

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
    val params =
        ChatCompletionCreateParams
            .builder()
            .model(LEMONADE_MODEL)
            .addSystemMessage(prompt)
            .addUserMessage(reportMessage(fullText))
            .build()

    val content =
        llmServer.chat().completions().createStreaming(params).use { stream ->
            stream
                .stream()
                .toList()
                .flatMap { it.choices() }
                .mapNotNull { it.delta().content().getOrNull() }
                .joinToString("")
        }

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
            .modelId(BEDROCK_MODEL_ID)
            .system(SystemContentBlock.builder().text(prompt).build())
            .messages(
                Message
                    .builder()
                    .role(ConversationRole.USER)
                    .content(ContentBlock.builder().text(reportMessage(fullText)).build())
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
            ).additionalModelRequestFields(enableReasoning("low"))
            .build()

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
    val statsText =
        bedrockStatsRow(
            fileName = fileName,
            resolvedTier = response.serviceTier()?.typeAsString(),
            localLatencyMs = localLatencyMs,
            bedrockLatencyMs = response.metrics()?.latencyMs(),
            inputTokens = usage?.inputTokens()?.toLong() ?: 0,
            outputTokens = usage?.outputTokens()?.toLong() ?: 0,
            totalTokens = usage?.totalTokens()?.toLong() ?: 0,
            stopReason = response.stopReasonAsString(),
        )

    return ExtractResult(SimpleSummary(fileName, content), statsText)
}

// Gemma 4 is served under /openai/v1.
fun mantleBaseUrl(region: Region): String = "https://bedrock-mantle.${region.id()}.api.aws/openai/v1"

class MantleClient(
    region: Region,
    credentialsProvider: AwsCredentialsProvider,
) {
    private val tokenGenerator =
        BedrockTokenGenerator
            .builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build()

    private val client =
        OpenAIOkHttpClient
            .builder()
            .apiKey(tokenGenerator.getToken())
            .baseUrl(mantleBaseUrl(region))
            .timeout(bedrockTimeout)
            .build()

    // A short-term API key can't outlive the SSO session it is derived from, so each request gets a fresh one.
    fun createChatCompletion(params: ChatCompletionCreateParams): ChatCompletion =
        client
            .withOptions { it.apiKey(tokenGenerator.getToken()) }
            .chat()
            .completions()
            .create(params)
}

fun ChatCompletionCreateParams.Builder.withBedrockSettings(): ChatCompletionCreateParams.Builder =
    model(BEDROCK_MODEL_ID)
        .maxCompletionTokens(BEDROCK_MAX_TOKENS.toLong())
        .reasoningEffort(ReasoningEffort.LOW)
        .serviceTier(ChatCompletionCreateParams.ServiceTier.FLEX)

fun mantleStatsRow(
    fileName: String,
    chat: ChatCompletion,
    localLatencyMs: Long,
): String {
    val usage = chat.usage().getOrNull()
    return bedrockStatsRow(
        fileName = fileName,
        resolvedTier = chat.serviceTier().getOrNull()?.asString(),
        localLatencyMs = localLatencyMs,
        bedrockLatencyMs = null,
        inputTokens = usage?.promptTokens() ?: 0,
        outputTokens = usage?.completionTokens() ?: 0,
        totalTokens = usage?.totalTokens() ?: 0,
        stopReason =
            chat
                .choices()
                .firstOrNull()
                ?.finishReason()
                ?.asString()
                .orEmpty(),
    )
}

fun extractWithMantle(
    mantleClient: MantleClient,
    fileName: String,
    fullText: String,
    prompt: String = summarizationPrompt,
): ExtractResult {
    val startNs = System.nanoTime()
    val params =
        ChatCompletionCreateParams
            .builder()
            .withBedrockSettings()
            .addSystemMessage(prompt)
            .addUserMessage(reportMessage(fullText))
            .build()

    val chat = mantleClient.createChatCompletion(params)
    val localLatencyMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
    val content =
        chat
            .choices()
            .firstOrNull()
            ?.message()
            ?.content()
            ?.getOrNull()
            .orEmpty()

    return ExtractResult(SimpleSummary(fileName, content), mantleStatsRow(fileName, chat, localLatencyMs))
}

fun enableReasoning(effort: String): Document =
    Document
        .mapBuilder()
        .putMap(
            "reasoningConfig",
            mapOf(
                "type" to Document.fromString("enabled"),
                "maxReasoningEffort" to Document.fromString(effort),
            ),
        ).build()

fun bedrockStatsRow(
    fileName: String,
    resolvedTier: String?,
    localLatencyMs: Long,
    bedrockLatencyMs: Long?,
    inputTokens: Long,
    outputTokens: Long,
    totalTokens: Long,
    stopReason: String,
): String {
    val inputCost = inputTokens * BEDROCK_MODEL.inputDollarPer1m / 1_000_000.0
    val outputCost = outputTokens * BEDROCK_MODEL.outputDollarPer1m / 1_000_000.0
    return listOf(
        fileName,
        BEDROCK_MODEL_ID,
        resolvedTier ?: BEDROCK_SERVICE_TIER,
        localLatencyMs.toString(),
        bedrockLatencyMs?.toString().orEmpty(),
        inputTokens.toString(),
        outputTokens.toString(),
        totalTokens.toString(),
        stopReason,
        "%.6f".format(inputCost),
        "%.6f".format(outputCost),
        "%.6f".format(inputCost + outputCost),
    ).joinToString(",")
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
    "file,inference_profile_id,service_tier_resolved,local_call_duration_ms," +
        "bedrock_latency_ms,input_tokens,output_tokens,total_tokens," +
        "stop_reason,input_cost_usd,output_cost_usd,total_cost_usd"

fun appendStats(
    statsFile: File,
    taskId: String,
    statsText: String,
) {
    statsFile.parentFile?.mkdirs()
    if (!statsFile.exists() || statsFile.length() == 0L) {
        statsFile.appendText(STATS_CSV_HEADER + "\n")
    }
    statsFile.appendText(statsText + "\n")
}

fun cleanSummary(
    summary: String,
    onHeadingFix: (String) -> Unit = { println("Fixing heading: $it") },
): String {
    var cleaned = summary.replace(SECTION_SEPARATOR, "\n")
    cleaned = cleaned.replace(Regex("""^(#+) \*\*(.+?)\*\*""", RegexOption.MULTILINE), "$1 $2")
    cleaned = normalizeSectionHeadings(cleaned, onHeadingFix)
    return cleaned
}

private val HEADING_REGEX = Regex("""^(#+)[ \t]+(.+?)[ \t]*$""", RegexOption.MULTILINE)

// The model occasionally misspells a prescribed heading (e.g. "Verlette").
// The section headings differ from each other far more than this.
private const val MAX_HEADING_TYPOS = 2

private fun sectionHeadingOf(headingText: String): String? {
    val bare = headingText.trim('*', ' ', ':').lowercase()
    return SUMMARY_SECTION_HEADINGS.firstOrNull { editDistance(it.lowercase(), bare) <= MAX_HEADING_TYPOS }
}

// The parsers split summaries at H1 headings, but the model doesn't reliably keep the prescribed levels:
// it sometimes nests all sections under a heading copied from the report.
// So the known section headings become H1, and every other heading becomes at least H2.
fun normalizeSectionHeadings(
    text: String,
    onHeadingFix: (String) -> Unit = { println("Fixing heading: $it") },
): String =
    HEADING_REGEX.replace(text) { match ->
        val sectionHeading = sectionHeadingOf(match.groupValues[2])
        val replacement =
            if (sectionHeading != null) {
                "# $sectionHeading"
            } else {
                "#".repeat(maxOf(match.groupValues[1].length, 2)) + " " + match.groupValues[2]
            }
        if (replacement != match.value) onHeadingFix(match.value)
        replacement
    }

fun missingSectionHeadings(summary: String): List<String> {
    val present =
        HEADING_REGEX
            .findAll(summary)
            .filter { it.groupValues[1] == "#" }
            .map { it.groupValues[2] }
            .toSet()
    return SUMMARY_SECTION_HEADINGS - present
}

fun isFileInSubsetForTest(filename: String): Boolean {
    val number = filename.take(3)
    return testReports.contains(number)
}

private class TeeOutputStream(
    private val first: OutputStream,
    private val second: OutputStream,
) : OutputStream() {
    override fun write(b: Int) {
        first.write(b)
        second.write(b)
    }

    override fun write(
        b: ByteArray,
        off: Int,
        len: Int,
    ) {
        first.write(b, off, len)
        second.write(b, off, len)
    }

    override fun flush() {
        first.flush()
        second.flush()
    }
}

// Tees at the stream level, so that output of libraries and uncaught exceptions also end up in the log file.
fun alsoLogTo(logFile: File) {
    logFile.parentFile?.mkdirs()
    val fileStream = FileOutputStream(logFile, true)
    System.setOut(PrintStream(TeeOutputStream(System.out, fileStream), true, Charsets.UTF_8))
    System.setErr(PrintStream(TeeOutputStream(System.err, fileStream), true, Charsets.UTF_8))
    println("\n=== Run started ${LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)} ===")
}

fun main() {
    val useLemonade = isLemonadeRunning()
    val outputDirectory = if (useLemonade) LEMONADE_OUTPUT_DIR else BEDROCK_OUTPUT_DIR
    alsoLogTo(Path(outputDirectory).resolve(LOG_FILENAME).toFile())
    printDebugInfo()

    val bedrockClient: BedrockRuntimeClient?
    val extractor: (fileName: String, fullText: String) -> ExtractResult

    if (useLemonade) {
        println("Lemonade is running. Continuing with local processing.")
        bedrockClient = null
        extractor = { fileName, fullText -> extractWithLemonade(fileName, fullText) }
    } else {
        println("Lemonade is not running. Checking AWS profile '$AWS_PROFILE'.")
        val identity =
            getCallerIdentityForProfile(AWS_PROFILE)
                ?: error("Neither Lemonade nor a logged-in AWS profile '$AWS_PROFILE' is available.")

        printCallerIdentity(identity)
        val region = resolveAwsRegion()
        val credentialsProvider =
            ProfileCredentialsProvider
                .builder()
                .profileName(AWS_PROFILE)
                .build()

        when (BEDROCK_MODEL.api) {
            BedrockApi.CONVERSE -> {
                val converseClient =
                    BedrockRuntimeClient
                        .builder()
                        .region(region)
                        .credentialsProvider(credentialsProvider)
                        .build()
                bedrockClient = converseClient
                extractor = { fileName, fullText -> extractWithBedrock(converseClient, fileName, fullText) }
            }

            BedrockApi.MANTLE -> {
                val mantleClient = MantleClient(region, credentialsProvider)
                bedrockClient = null
                extractor = { fileName, fullText -> extractWithMantle(mantleClient, fileName, fullText) }
            }
        }

        println(
            "Using Bedrock model '$BEDROCK_MODEL_ID' via the ${BEDROCK_MODEL.api} API " +
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
                file.isFile &&
                    file.extension == "md" &&
                    file.name !in processedFiles &&
                    isFileInSubsetForTest(file.name)
            }?.sortedBy { it.name }
            ?: emptyList()

    if (System.getProperty("os.name").lowercase().contains("win")) {
        SleepPreventer.preventSleep()
    }

    var errorCount = 0
    bedrockClient.use {
        for (file in files) {
            try {
                val report = file.readText()
                val content = squeezeTablePadding(report)
                println("Starting: ${file.name}")

                val result = extractor(file.name, content)
                val cleanedSummary = result.summary.copy(summary = cleanSummary(result.summary.summary))
                missingSectionHeadings(cleanedSummary.summary).takeIf { it.isNotEmpty() }?.let {
                    println("WARNING: ${file.name} is missing the sections $it")
                }
                outputFile.appendText("\n\n---\n" + cleanedSummary.asOutput)
                appendStats(statsFile, file.name, result.statsText)
                println("Processed: ${file.name}")
                assessSummary(file.name, cleanedSummary.summary, report).logLines().forEach(::println)
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
