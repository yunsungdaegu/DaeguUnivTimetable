package kr.ac.daegu.timetable.data.utils

import android.util.Log
import kr.ac.daegu.timetable.BuildConfig
import java.io.BufferedReader
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun connect(end: String, params: String): String {
    // retrofit2로 데이터를 가져오면 content length -1 문제 발생
    val url = URL("${BuildConfig.BASE_URL}${end}")
    val connection = url.openConnection() as HttpsURLConnection
    connection.requestMethod = "POST"
    connection.doInput = true
    connection.doOutput = true
    connection.setRequestProperty("Content-Type", "text/plain")
    connection.setRequestProperty("Cookie", cookie)
    val wr = DataOutputStream(connection.outputStream)
    wr.writeBytes(params)
    wr.flush()
    wr.close()
    val sb = StringBuilder()
    val responseCode = connection.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        val allText: String = connection.inputStream.bufferedReader().use(BufferedReader::readText)
        sb.append(allText.trim())
    }
    connection.disconnect()
    Log.d("log", sb.toString())
    return sb.toString()
}