package ebuparser

import com.anthropic.client.okhttp.AnthropicOkHttpClient
import com.anthropic.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class ClassifierKtTest {
    @Test
    fun `tool input schema is the classification schema`() {
        val client = AnthropicOkHttpClient.builder().apiKey("unused").build()
        val tool = Classifier(client, CostLog(File("unused"), TokenPrices(0.0, 0.0))).tool
        client.close()

        val mapper = jacksonObjectMapper()
        // The SDK's own mapper serializes the schema as it is sent.
        val schema = mapper.readValue<Map<String, Any?>>(jsonMapper().writeValueAsString(tool.inputSchema()))

        assertThat(schema).isEqualTo(mapper.readValue<Map<String, Any?>>(mapper.writeValueAsString(classificationSchema)))
        assertThat(tool.strict()).hasValue(true)
    }
}
