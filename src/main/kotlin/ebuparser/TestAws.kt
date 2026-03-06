package ebuparser

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sts.StsClient
import software.amazon.awssdk.services.sts.model.GetCallerIdentityRequest

private const val PROFILE_NAME = "llm-user"

private fun resolveRegion(): Region {
    val regionName = System.getenv("AWS_REGION") ?: System.getenv("AWS_DEFAULT_REGION") ?: "us-east-1"
    return Region.of(regionName)
}

fun main() {
    val region = resolveRegion()
    println("aws sts get-caller-identity --profile $PROFILE_NAME")
    println("Using AWS SDK region: ${region.id()}")

    ProfileCredentialsProvider
        .builder()
        .profileName(PROFILE_NAME)
        .build()
        .use { credentialsProvider ->
            StsClient
                .builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                .build()
                .use { sts ->
                    val identity = sts.getCallerIdentity(GetCallerIdentityRequest.builder().build())
                    println(
                        "{" +
                            "\"UserId\":\"${identity.userId()}\"," +
                            "\"Account\":\"${identity.account()}\"," +
                            "\"Arn\":\"${identity.arn()}\"" +
                            "}",
                    )
                }
        }
}
