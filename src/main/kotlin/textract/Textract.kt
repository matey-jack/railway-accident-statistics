package textract

import ebuparser.AWS_PROFILE
import ebuparser.getCallerIdentityForProfile
import ebuparser.printCallerIdentity
import ebuparser.resolveAwsRegion
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.textract.TextractClient
import software.amazon.awssdk.services.textract.model.DocumentLocation
import software.amazon.awssdk.services.textract.model.FeatureType
import software.amazon.awssdk.services.textract.model.GetDocumentAnalysisRequest
import software.amazon.awssdk.services.textract.model.JobStatus
import software.amazon.awssdk.services.textract.model.OutputConfig
import software.amazon.awssdk.services.textract.model.S3Object
import software.amazon.awssdk.services.textract.model.StartDocumentAnalysisRequest

// files were already uploaded manually
const val S3_BUCKET = "matey-jack-bedrock-batch"
const val S3_PREFIX = "EBU"

val PILOT_PREFIXES = "001 002 039 114 116 151 163 189 199 215 221 226 231 238".split(" ")

fun main() {
    val identity = getCallerIdentityForProfile(AWS_PROFILE)
        ?: error("AWS profile '$AWS_PROFILE' is not available.")
    printCallerIdentity(identity)

    val region = resolveAwsRegion()
    val credentialsProvider = ProfileCredentialsProvider.builder()
        .profileName(AWS_PROFILE)
        .build()

    val s3Client = S3Client.builder()
        .region(region)
        .credentialsProvider(credentialsProvider)
        .build()
    val textractClient = TextractClient.builder()
        .region(region)
        .credentialsProvider(credentialsProvider)
        .build()

    // prefix -> jobId
    val jobs = mutableMapOf<String, String>()

    s3Client.use { s3 ->
        textractClient.use { textract ->
            for (prefix in PILOT_PREFIXES) {
                val objects = s3.listObjectsV2(
                    ListObjectsV2Request.builder()
                        .bucket(S3_BUCKET)
                        .prefix("$S3_PREFIX/$prefix")
                        .build(),
                ).contents()
                check(objects.size == 1) {
                    "Expected exactly one object for prefix $S3_PREFIX/$prefix, " +
                        "found ${objects.size}: ${objects.map { it.key() }}"
                }
                val key = objects.single().key()

                val jobId = textract.startDocumentAnalysis(
                    StartDocumentAnalysisRequest.builder()
                        .documentLocation(
                            DocumentLocation.builder()
                                .s3Object(S3Object.builder().bucket(S3_BUCKET).name(key).build())
                                .build(),
                        )
                        .featureTypes(FeatureType.TABLES)
                        .outputConfig(
                            OutputConfig.builder()
                                .s3Bucket(S3_BUCKET)
                                .s3Prefix("$S3_PREFIX/textracted")
                                .build(),
                        )
                        .build(),
                ).jobId()

                println("Started job for $prefix ($key): $jobId")
                jobs[prefix] = jobId
            }

            val pending = jobs.toMutableMap()
            while (pending.isNotEmpty()) {
                Thread.sleep(5_000)
                val iter = pending.iterator()
                while (iter.hasNext()) {
                    val (prefix, jobId) = iter.next()
                    val response = textract.getDocumentAnalysis(
                        GetDocumentAnalysisRequest.builder().jobId(jobId).build(),
                    )
                    when (response.jobStatus()) {
                        JobStatus.SUCCEEDED -> {
                            println("Done: $prefix -> s3://$S3_BUCKET/$S3_PREFIX/textracted/$jobId/")
                            iter.remove()
                        }
                        JobStatus.FAILED -> {
                            println("Failed: $prefix (${response.statusMessage()})")
                            iter.remove()
                        }
                        else -> println("Pending: $prefix (${response.jobStatusAsString()})")
                    }
                    Thread.sleep(500)
                }
            }
        }
    }
}
