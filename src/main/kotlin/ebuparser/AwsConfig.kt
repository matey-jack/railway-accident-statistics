package ebuparser

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sts.StsClient
import software.amazon.awssdk.services.sts.model.GetCallerIdentityRequest
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse

const val AWS_PROFILE = "llm-user"

fun resolveAwsRegion(): Region {
    val regionName = System.getenv("AWS_REGION") ?: System.getenv("AWS_DEFAULT_REGION") ?: "eu-central-1"
    return Region.of(regionName)
}

fun getCallerIdentityForProfile(profileName: String): GetCallerIdentityResponse? =
    try {
        ProfileCredentialsProvider
            .builder()
            .profileName(profileName)
            .build()
            .use { credentialsProvider ->
                StsClient
                    .builder()
                    .region(resolveAwsRegion())
                    .credentialsProvider(credentialsProvider)
                    .build()
                    .use { sts ->
                        sts.getCallerIdentity(GetCallerIdentityRequest.builder().build())
                    }
            }
    } catch (e: Exception) {
        println("AWS profile '$profileName' is not ready: ${e.message}")
        null
    }

fun printCallerIdentity(identity: GetCallerIdentityResponse) {
    println("aws sts get-caller-identity --profile $AWS_PROFILE")
    println(
        "{" +
            "\"UserId\":\"${identity.userId()}\"," +
            "\"Account\":\"${identity.account()}\"," +
            "\"Arn\":\"${identity.arn()}\"" +
            "}",
    )
}
