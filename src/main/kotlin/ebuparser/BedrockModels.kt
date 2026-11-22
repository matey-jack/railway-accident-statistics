package ebuparser

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient

enum class BedrockApi {
    CONVERSE,
    MANTLE,
}

data class BedrockModel(
    val name: String,
    val api: BedrockApi,
    val priceListEntry: PriceListEntry,
) {
    val inferenceId: String
        get() =
            when (api) {
                BedrockApi.CONVERSE -> "global.$name"
                BedrockApi.MANTLE -> name
            }
}

// The price list entries are those of the service tier the model is called with.

val novaModel =
    BedrockModel(
        "amazon.nova-2-lite-v1:0",
        BedrockApi.CONVERSE,
        PriceListEntry(
            "AmazonBedrock",
            "Nova 2.0 Lite",
            "{region}-Nova2.0Lite-input-tokens-flex-cross-region-global",
            "{region}-Nova2.0Lite-output-tokens-flex-cross-region-global",
        ),
    )

// Gemma 4 is only served by the OpenAI-compatible bedrock-mantle endpoint, which has no cross-region inference profiles.
val gemma31Model =
    BedrockModel(
        "google.gemma-4-31b",
        BedrockApi.MANTLE,
        PriceListEntry(
            "AmazonBedrock",
            "google.gemma-4-31b",
            "{region}-google.gemma-4-31b-mantle-input-tokens-flex",
            "{region}-google.gemma-4-31b-mantle-output-tokens-flex",
        ),
    )

// Bedrock offers no flex tier for this model, so it is called with the standard tier.
// Its bedrock-mantle endpoint isn't available in the EU regions.
val haiku55Model =
    BedrockModel(
        "anthropic.claude-haiku-5-5",
        BedrockApi.CONVERSE,
        PriceListEntry(
            "AmazonBedrockFoundationModels",
            "Claude Haiku 5.5",
            "{region}-MP:{region}_input_tokens_global_standard-Units",
            "{region}-MP:{region}_output_tokens_global_standard-Units",
        ),
    )

// this includes reasoning tokens!
const val BEDROCK_MAX_TOKENS = 8000

fun converseClient(
    region: Region,
    credentialsProvider: AwsCredentialsProvider,
): BedrockRuntimeClient =
    BedrockRuntimeClient
        .builder()
        .region(region)
        .credentialsProvider(credentialsProvider)
        .build()
