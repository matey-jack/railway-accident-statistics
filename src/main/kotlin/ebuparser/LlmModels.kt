package ebuparser

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.core.exception.SdkClientException
import software.amazon.awssdk.core.exception.SdkServiceException
import software.amazon.awssdk.http.apache5.Apache5HttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.retries.DefaultRetryStrategy
import software.amazon.awssdk.retries.api.BackoffStrategy
import software.amazon.awssdk.retries.api.RetryStrategy
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient
import java.io.IOException
import java.net.SocketTimeoutException
import java.time.Duration

enum class LlmApi {
    CONVERSE,
    MANTLE,
    ANTHROPIC,
}

data class LlmModel(
    val name: String,
    val api: LlmApi,
    val prices: PriceSource,
) {
    val inferenceId: String
        get() =
            when (api) {
                LlmApi.CONVERSE -> "global.$name"
                LlmApi.MANTLE, LlmApi.ANTHROPIC -> name
            }

    // Each model gets its own directory, so that runs of different models can be compared.
    // Windows doesn't allow ':' in file names, and Bedrock model names can contain a version suffix like ':0'.
    val resultsDir: String
        get() = "results/${name.replace(':', '-')}"
}

// The price list entries are those of the service tier the model is called with.

val novaModel =
    LlmModel(
        "amazon.nova-2-lite-v1:0",
        LlmApi.CONVERSE,
        PriceListEntry(
            "AmazonBedrock",
            "Nova 2.0 Lite",
            "{region}-Nova2.0Lite-input-tokens-flex-cross-region-global",
            "{region}-Nova2.0Lite-output-tokens-flex-cross-region-global",
        ),
    )

// Gemma 4 is only served by the OpenAI-compatible bedrock-mantle endpoint, which has no cross-region inference profiles.
val gemma31Model =
    LlmModel(
        "google.gemma-4-31b",
        LlmApi.MANTLE,
        PriceListEntry(
            "AmazonBedrock",
            "google.gemma-4-31b",
            "{region}-google.gemma-4-31b-mantle-input-tokens-flex",
            "{region}-google.gemma-4-31b-mantle-output-tokens-flex",
        ),
    )

// Bedrock offers no flex tier for this model, so it is called with the standard tier.
// Its bedrock-mantle endpoint isn't available in the EU regions.
val bedrockHaiku55Model =
    LlmModel(
        "anthropic.claude-haiku-5-5",
        LlmApi.CONVERSE,
        PriceListEntry(
            "AmazonBedrockFoundationModels",
            "Claude Haiku 5.5",
            "{region}-MP:{region}_input_tokens_global_standard-Units",
            "{region}-MP:{region}_output_tokens_global_standard-Units",
        ),
    )

// Anthropic has no machine-readable price list, so these are copied from https://platform.claude.com/docs/en/about-claude/pricing
val haiku55Model =
    LlmModel(
        "claude-haiku-5-5",
        LlmApi.ANTHROPIC,
        TokenPrices(
            inputDollarPer1m = 0.10,
            outputDollarPer1m = 0.50,
            longPrompt = TokenPrices(inputDollarPer1m = 0.50, outputDollarPer1m = 2.50),
        ),
    )

// this includes reasoning tokens!
const val MAX_TOKENS = 8000

// Converse sends nothing until the whole answer is generated, and the flex tier is only served after the standard one,
// so at busy times a call takes many minutes. AWS advises starting with an hour and lowering it once no call times out.
private val converseReadTimeout = Duration.ofHours(1)

// Bedrock most likely bills a request that the client stopped waiting for, so a read timeout is not retried.
// Errors that Bedrock returns cost nothing.
internal fun isRetryableConverseFailure(failure: Throwable): Boolean {
    val causes = generateSequence(failure) { it.cause }
    return when (failure) {
        is SdkServiceException -> failure.isThrottlingException || failure.statusCode() == 408 || failure.statusCode() >= 500
        is SdkClientException -> causes.any { it is IOException } && causes.none { it is SocketTimeoutException }
        else -> false
    }
}

// Bedrock answers 503 when it has no capacity left, which is throttling as well.
private fun isConverseThrottling(failure: Throwable): Boolean =
    failure is SdkServiceException && (failure.isThrottlingException || failure.statusCode() == 503)

// The adaptive strategy also slows down the client's own request rate after throttling.
// Without the client defaults, the SDK would add its own retry conditions, which include read timeouts.
private fun converseRetryStrategy(): RetryStrategy =
    DefaultRetryStrategy
        .adaptiveStrategyBuilder()
        .useClientDefaults(false)
        .maxAttempts(5)
        .retryOnException(::isRetryableConverseFailure)
        .treatAsThrottling(::isConverseThrottling)
        .backoffStrategy(BackoffStrategy.exponentialDelay(Duration.ofSeconds(1), Duration.ofSeconds(20)))
        .throttlingBackoffStrategy(BackoffStrategy.exponentialDelay(Duration.ofSeconds(5), Duration.ofMinutes(2)))
        .build()

fun converseClient(
    region: Region,
    credentialsProvider: AwsCredentialsProvider,
): BedrockRuntimeClient =
    BedrockRuntimeClient
        .builder()
        .region(region)
        .credentialsProvider(credentialsProvider)
        .httpClientBuilder(Apache5HttpClient.builder().socketTimeout(converseReadTimeout))
        .overrideConfiguration { it.retryStrategy(converseRetryStrategy()) }
        .build()
