package ebuparser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.Test

class BedrockPricesKtTest {
    @Test
    fun `a long prompt makes the whole request use the long-prompt prices`() {
        val prices = haiku55Model.prices as TokenPrices

        assertThat(prices.inputCost(100_000)).isCloseTo(0.01, within(1e-12))
        assertThat(prices.outputCost(100_000, 1_000_000)).isCloseTo(0.5, within(1e-12))
        assertThat(prices.inputCost(200_000)).isCloseTo(0.1, within(1e-12))
        assertThat(prices.outputCost(200_000, 1_000_000)).isCloseTo(2.5, within(1e-12))
    }

    private data class Product(
        val model: String,
        val usageType: String,
        val unit: String,
        val usd: String,
    )

    private fun priceList(vararg products: Product) =
        jacksonObjectMapper().valueToTree<JsonNode>(
            mapOf(
                "products" to
                    products.withIndex().associate { (sku, p) ->
                        "$sku" to mapOf("attributes" to mapOf("model" to p.model, "usagetype" to p.usageType))
                    },
                "terms" to
                    mapOf(
                        "OnDemand" to
                            products.withIndex().associate { (sku, p) ->
                                "$sku" to
                                    mapOf(
                                        "term" to
                                            mapOf(
                                                "priceDimensions" to
                                                    mapOf("dim" to mapOf("unit" to p.unit, "pricePerUnit" to mapOf("USD" to p.usd))),
                                            ),
                                    )
                            },
                    ),
            ),
        )

    private val list =
        priceList(
            Product("Claude Haiku 5.5", "EUC1-MP:EUC1_input_tokens_global_standard-Units", "1M tokens", "0.1000000000"),
            Product("Claude Haiku 5.5", "EUC1-MP:EUC1_input_tokens_standard-Units", "1M tokens", "0.1100000000"),
            Product("Claude Sonnet 4.6", "EUC1-MP:EUC1_input_tokens_global_standard-Units", "1M tokens", "3.0000000000"),
            Product("google.gemma-4-31b", "EUC1-google.gemma-4-31b-mantle-input-tokens-flex", "1K tokens", "0.0000840000"),
        )

    @Test
    fun testDollarPer1mTokens_MatchesModelAndRegionPrefix() {
        assertThat(dollarPer1mTokens(list, "Claude Haiku 5.5", "{region}-MP:{region}_input_tokens_global_standard-Units"))
            .isEqualTo(0.10)
    }

    @Test
    fun testDollarPer1mTokens_ConvertsPricePer1kTokens() {
        assertThat(dollarPer1mTokens(list, "google.gemma-4-31b", "{region}-google.gemma-4-31b-mantle-input-tokens-flex"))
            .isEqualTo(0.084)
    }

    @Test
    fun testDollarPer1mTokens_FailsWithoutMatch() {
        assertThatThrownBy { dollarPer1mTokens(list, "google.gemma-4-31b", "{region}-google.gemma-4-31b-mantle-input-tokens-batch") }
            .hasMessageContaining("Expected one product")
    }
}
