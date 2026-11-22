package ebuparser

import com.anthropic.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ClassifierKtTest {
    @Test
    fun `Anthropic tool input schema is the classification schema`() {
        val tool = anthropicTool
        val mapper = jacksonObjectMapper()
        // The SDK's own mapper serializes the schema as it is sent.
        val schema = mapper.readValue<Map<String, Any?>>(jsonMapper().writeValueAsString(tool.inputSchema()))

        assertThat(schema).isEqualTo(mapper.readValue<Map<String, Any?>>(mapper.writeValueAsString(classificationSchema)))
        assertThat(tool.strict()).hasValue(true)
    }

    @Test
    fun `Bedrock tool input schema is the classification schema`() {
        val mapper = jacksonObjectMapper()
        val schema = fromDocument(bedrockToolSpec(strict = true).inputSchema().json())

        assertThat(schema).isEqualTo(mapper.readValue<Map<String, Any?>>(mapper.writeValueAsString(classificationSchema)))
        assertThat(bedrockToolSpec(strict = true).strict()).isTrue()
        val unstrict: Boolean? = bedrockToolSpec(strict = false).strict()
        assertThat(unstrict).isNull()
    }
}
