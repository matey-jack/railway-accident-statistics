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
    val inputDollarPer1m: Double,
    val outputDollarPer1m: Double,
    val api: BedrockApi,
) {
    val inferenceId: String
        get() =
            when (api) {
                BedrockApi.CONVERSE -> "global.$name"
                BedrockApi.MANTLE -> name
            }
}

// this is the listed flex price
val novaModel = BedrockModel("amazon.nova-2-lite-v1:0", 0.195, 1.635, BedrockApi.CONVERSE)

// this is the listed standard price with 50% discount.
// Gemma 4 is only served by the OpenAI-compatible bedrock-mantle endpoint, which has no cross-region inference profiles.
val gemma31Model = BedrockModel("google.gemma-4-31b", 0.17 / 2, 0.48 / 2, BedrockApi.MANTLE)

// this is the listed standard price; Bedrock offers no flex tier for this model.
// Its bedrock-mantle endpoint isn't available in the EU regions.
val haiku55Model = BedrockModel("anthropic.claude-haiku-5-5", 0.10, 0.50, BedrockApi.CONVERSE)

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
