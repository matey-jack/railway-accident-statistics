package ebuparser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import software.amazon.awssdk.regions.Region
import java.io.File
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

// The bulk files of the AWS Price List API are public, so unlike its Query API (GetProducts)
// they need no IAM permission.
private const val PRICE_LIST_URL = "https://pricing.us-east-1.amazonaws.com/offers/v1.0/aws"

// Prices of a cross-region inference profile are listed under the region the request is sent to.
// Each provider names its models and usage types differently, so a model names its own.
// The marketplace models (Claude) share their usage types, so the model attribute is needed as well.
// `{region}` stands for the region's usage-type prefix, e.g. EUC1 for eu-central-1.
sealed interface PriceSource

data class PriceListEntry(
    val offerCode: String,
    val model: String,
    val inputUsageType: String,
    val outputUsageType: String,
) : PriceSource

// Anthropic charges the higher long-prompt prices for the whole request, input and output.
private const val LONG_PROMPT_TOKENS = 100_000

data class TokenPrices(
    val inputDollarPer1m: Double,
    val outputDollarPer1m: Double,
    val longPrompt: TokenPrices? = null,
) : PriceSource {
    fun forPrompt(inputTokens: Long): TokenPrices = if (longPrompt != null && inputTokens > LONG_PROMPT_TOKENS) longPrompt else this

    fun inputCost(tokens: Long): Double = tokens * forPrompt(tokens).inputDollarPer1m / 1_000_000.0

    fun outputCost(
        inputTokens: Long,
        outputTokens: Long,
    ): Double = outputTokens * forPrompt(inputTokens).outputDollarPer1m / 1_000_000.0

    override fun toString(): String =
        "input $$inputDollarPer1m, output $$outputDollarPer1m per 1M tokens" +
            longPrompt?.let { "; for prompts over $LONG_PROMPT_TOKENS tokens: $it" }.orEmpty()
}

// Only the Bedrock models need the region, for their price list.
fun tokenPrices(
    model: LlmModel,
    region: Region?,
): TokenPrices =
    when (val source = model.prices) {
        is TokenPrices -> source
        is PriceListEntry -> fetchTokenPrices(source, requireNotNull(region) { "${model.name} needs an AWS region." })
    }

fun fetchTokenPrices(
    entry: PriceListEntry,
    region: Region,
): TokenPrices {
    val url = "$PRICE_LIST_URL/${entry.offerCode}/current/${region.id()}/index.json"
    val request =
        Request
            .Builder()
            .url(url)
            .get()
            .build()
    val priceList =
        OkHttpClient().newCall(request).execute().use { response ->
            check(response.isSuccessful) { "Fetching $url failed with HTTP ${response.code}." }
            jacksonObjectMapper().readTree(response.body.string())
        }
    return TokenPrices(
        inputDollarPer1m = dollarPer1mTokens(priceList, entry.model, entry.inputUsageType),
        outputDollarPer1m = dollarPer1mTokens(priceList, entry.model, entry.outputUsageType),
    )
}

fun printedTokenPrices(
    model: LlmModel,
    region: Region?,
): TokenPrices {
    val prices = tokenPrices(model, region)
    println("Price of '${model.inferenceId}'${region?.let { " in '${it.id()}'" }.orEmpty()}: $prices")
    return prices
}

internal fun dollarPer1mTokens(
    priceList: JsonNode,
    model: String,
    usageTypePattern: String,
): Double {
    val usageType =
        Regex(usageTypePattern.split("{region}").joinToString("[A-Z0-9]+") { Regex.escape(it) })
    val skus =
        priceList["products"]
            .properties()
            .filter { (_, product) ->
                val attributes = product["attributes"]
                attributes["model"]?.asText() == model &&
                    usageType.matches(attributes["usagetype"]?.asText().orEmpty())
            }.map { it.key }
    val sku =
        skus.singleOrNull()
            ?: error("Expected one product of '$model' with usage type $usageTypePattern, found $skus.")
    val dimension =
        priceList["terms"]["OnDemand"][sku]
            .single()["priceDimensions"]
            .single()
    val price = dimension["pricePerUnit"]["USD"].asText().toBigDecimal()
    return when (val unit = dimension["unit"].asText()) {
        "1K tokens" -> price.movePointRight(3).toDouble()
        "1M tokens" -> price.toDouble()
        else -> error("Unknown price unit '$unit' for usage type $usageTypePattern.")
    }
}

class CostLog(
    private val logFile: File,
    val prices: TokenPrices,
) {
    private var inputTokens = 0L
    private var outputTokens = 0L
    private var inputCost = 0.0
    private var outputCost = 0.0

    fun start(model: LlmModel) {
        logFile.parentFile?.mkdirs()
        logFile.appendText(
            "\n=== Run started ${LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)} ===\n" +
                "Model '${model.inferenceId}': $prices\n",
        )
    }

    fun add(
        inputTokens: Long,
        outputTokens: Long,
    ) {
        this.inputTokens += inputTokens
        this.outputTokens += outputTokens
        inputCost += prices.inputCost(inputTokens)
        outputCost += prices.outputCost(inputTokens, outputTokens)
    }

    fun writeTotals() {
        val totals =
            "Input: $inputTokens tokens, $%.6f\n".format(inputCost) +
                "Output: $outputTokens tokens, $%.6f\n".format(outputCost) +
                "Total: $%.6f\n".format(inputCost + outputCost)
        print(totals)
        logFile.appendText(totals)
    }
}
