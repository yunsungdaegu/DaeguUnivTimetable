package kr.ac.daegu.timetable.data.login.api

import kr.ac.daegu.timetable.BuildConfig
import retrofit2.Response
import retrofit2.http.GET

internal interface LoginApi {

    @GET(BuildConfig.TIGERSSTD_SSO)
    suspend fun ssoLogin(): Response<Unit>
}