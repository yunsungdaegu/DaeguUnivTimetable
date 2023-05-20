package kr.ac.daegu.timetable.data.login.repository

import kr.ac.daegu.timetable.BuildConfig
import kr.ac.daegu.timetable.data.utils.RequestUtil
import kr.ac.daegu.timetable.data.utils.cookie
import kr.ac.daegu.timetable.data.utils.loginApi
import kr.ac.daegu.timetable.data.utils.ssoApi
import kr.ac.daegu.timetable.domain.login.repository.LoginRepository
import java.io.BufferedReader
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LoginRepositoryImpl(
    private val requestUtil: RequestUtil
) : LoginRepository {

    override suspend fun login(studentId: String, password: String): Boolean {
        requestUtil.getRequest {
            ssoApi.process(
                studentId,
                password,
                "true",
                BuildConfig.BASE_URL,
            )
        }
        return ssoLogin(studentId)
    }

    private suspend fun ssoLogin(studentId: String): Boolean {
        requestUtil.getRequest {
            loginApi.ssoLogin()
        }
        return login(studentId)
    }

    private fun login(studentId: String): Boolean {
        val url = URL("${BuildConfig.BASE_URL}${BuildConfig.TIGERSSTD_LOGIN}")
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = "POST"
        connection.doInput = true
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "text/plain")
        connection.setRequestProperty("Cookie", cookie)
        val postParams = "SSV:utf-8\u001EDataset:S\u001E_RowType_\u001FUSER_ID:STRING(256)\u001FUSER_PWD:STRING(256)\u001FSTEP:STRING(256)\u001FSSO_YN:STRING(256)\u001FSKEY:STRING(256)\u001EN\u001F${studentId}\u001F\u0003\u001F1\u001FY\u001F\u0003\u001E\u001E"
        val wr = DataOutputStream(connection.outputStream)
        wr.writeBytes(postParams)
        wr.flush()
        wr.close()
        val sb = StringBuilder()
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val allText: String = connection.inputStream.bufferedReader().use(BufferedReader::readText)
            sb.append(allText.trim())
        }
        connection.disconnect()
        return sb.toString().split(studentId).size >= 4
    }
}