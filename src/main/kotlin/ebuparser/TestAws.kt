package ebuparser

fun main() {
    val region = resolveAwsRegion()
    println("Using AWS SDK region: ${region.id()}")

    val identity =
        getCallerIdentityForProfile(AWS_PROFILE)
            ?: error("AWS profile '$AWS_PROFILE' is not ready.")
    printCallerIdentity(identity)
}
