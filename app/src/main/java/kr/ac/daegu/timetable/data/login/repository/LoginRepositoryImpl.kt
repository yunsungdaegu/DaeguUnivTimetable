package kr.ac.daegu.timetable.data.login.repository

import kr.ac.daegu.timetable.BuildConfig
import kr.ac.daegu.timetable.data.utils.RequestUtil
import kr.ac.daegu.timetable.data.utils.connect
import kr.ac.daegu.timetable.data.utils.cookie
import kr.ac.daegu.timetable.data.utils.loginApi
import kr.ac.daegu.timetable.data.utils.ssoApi
import kr.ac.daegu.timetable.domain.login.repository.LoginRepository

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
        val postParams = "SSV:utf-8\u001EDataset:S\u001E_RowType_\u001FUSER_ID:STRING(256)\u001FUSER_PWD:STRING(256)\u001FSTEP:STRING(256)\u001FSSO_YN:STRING(256)\u001FSKEY:STRING(256)\u001EN\u001F${studentId}\u001F\u0003\u001F1\u001FY\u001F\u0003\u001E\u001E"
        connect(BuildConfig.TIGERSSTD_LOGIN, postParams)
        return (cookie?.split(";")?.size ?: 0) >= 2
    }
}