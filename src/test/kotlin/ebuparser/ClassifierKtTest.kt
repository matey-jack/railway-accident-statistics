package ebuparser

import com.anthropic.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider
import software.amazon.awssdk.core.exception.SdkClientException
import software.amazon.awssdk.core.exception.SdkServiceException
import software.amazon.awssdk.regions.Region
import java.net.SocketException
import java.net.SocketTimeoutException
import java.time.Duration

class ClassifierKtTest {
    @Test
    fun `pauses start after three failures in a row and double up to half an hour`() {
        assertThat((1..8).map { pauseDuration(it)?.toMinutes() }).containsExactly(null, null, 2, 4, 8, 16, 30, 30)
        assertThat(pauseDuration(1000)).isEqualTo(Duration.ofMinutes(30))
    }

    @Test
    fun `Converse client accepts the timeout and retry settings`() {
        converseClient(Region.EU_CENTRAL_1, AnonymousCredentialsProvider.create()).use { client ->
            assertThat(client.serviceClientConfiguration().overrideConfiguration().retryStrategy()).isPresent()
        }
    }

    @Test
    fun `Converse retries throttling and connection failures but not read timeouts`() {
        fun serviceError(status: Int) = SdkServiceException.builder().statusCode(status).build()

        fun clientError(cause: Throwable) = SdkClientException.builder().cause(cause).build()

        assertThat(isRetryableConverseFailure(serviceError(429))).isTrue()
        assertThat(isRetryableConverseFailure(serviceError(503))).isTrue()
        assertThat(isRetryableConverseFailure(serviceError(400))).isFalse()
        assertThat(isRetryableConverseFailure(clientError(SocketException("Connection reset")))).isTrue()
        assertThat(isRetryableConverseFailure(clientError(SocketTimeoutException("Read timed out")))).isFalse()
    }

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
