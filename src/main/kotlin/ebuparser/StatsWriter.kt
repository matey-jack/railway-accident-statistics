package ebuparser

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class StatsWriter(
    serverUrl: String,
    filename: String,
) {
    val statsFile = File(filename)
    val client = OkHttpClient()
    val statsUrl =
        serverUrl
            .toHttpUrlOrNull()
            ?.newBuilder()
            ?.addPathSegments("api/v1/stats")
            ?.build()
            ?: throw IllegalArgumentException("Invalid base URL: $serverUrl")

    fun writeFor(taskId: String) {
        val request =
            Request
                .Builder()
                .url(statsUrl)
                .get()
                .build()
        val response = client.newCall(request).execute()
        val statsJson = response.body.string()
        println(statsJson)
        statsFile.appendText("---\nfile: $taskId\n\n$statsJson\n")
    }
}
