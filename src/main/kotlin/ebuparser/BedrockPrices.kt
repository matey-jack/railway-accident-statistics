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
data class PriceListEntry(
    val offerCode: String,
    val model: String,
    val inputUsageType: String,
    val outputUsageType: String,
)

data class TokenPrices(
    val inputDollarPer1m: Double,
    val outputDollarPer1m: Double,
) {
    fun inputCost(tokens: Long): Double = tokens * inputDollarPer1m / 1_000_000.0

    fun outputCost(tokens: Long): Double = tokens * outputDollarPer1m / 1_000_000.0

    override fun toString(): String = "input $$inputDollarPer1m, output $$outputDollarPer1m per 1M tokens"
}

fun fetchTokenPrices(
    model: BedrockModel,
    region: Region,
): TokenPrices {
    val entry = model.priceListEntry
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

fun fetchAndPrintTokenPrices(
    model: BedrockModel,
    region: Region,
): TokenPrices {
    val prices = fetchTokenPrices(model, region)
    println("Price of '${model.inferenceId}' in '${region.id()}': $prices")
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
    private val prices: TokenPrices,
) {
    private var inputTokens = 0L
    private var outputTokens = 0L

    fun start(model: BedrockModel) {
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
    }

    fun writeTotals() {
        val inputCost = prices.inputCost(inputTokens)
        val outputCost = prices.outputCost(outputTokens)
        val totals =
            "Input: $inputTokens tokens, $%.6f\n".format(inputCost) +
                "Output: $outputTokens tokens, $%.6f\n".format(outputCost) +
                "Total: $%.6f\n".format(inputCost + outputCost)
        print(totals)
        logFile.appendText(totals)
    }
}
