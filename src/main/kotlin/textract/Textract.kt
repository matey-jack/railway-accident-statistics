package textract

// files were already uploaded manually
const val S3_BUCKET = "matey-jack-bedrock-batch"
const val S3_PREFIX = "EBU"

val PILOT_PREFIXES = "001 002 039 114 116 151 163 189 199 215 221 226 231 238".split(" ")

fun main() {
// TODO:
    // initialize AWS as in ExtractSummaries.kt
    // and for each P of the PILOT_PREFIXES: get the S3 key name in S3_BUCKET with prefix S3_PREFIX/P (there is exactly one)
    // pass that key to the Textract SDK for AnalyzeDocument with the Tables option set. Store result in same bucket with Prefix EBU/textracted/
}
