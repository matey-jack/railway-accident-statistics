package ebuparser

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient

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

fun converseClient(
    region: Region,
    credentialsProvider: AwsCredentialsProvider,
): BedrockRuntimeClient =
    BedrockRuntimeClient
        .builder()
        .region(region)
        .credentialsProvider(credentialsProvider)
        .build()
