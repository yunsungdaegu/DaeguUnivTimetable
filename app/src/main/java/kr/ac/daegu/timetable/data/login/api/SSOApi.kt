package kr.ac.daegu.timetable.data.login.api

import kr.ac.daegu.timetable.BuildConfig
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface SSOApi {

    @FormUrlEncoded
    @POST(BuildConfig.SSO_END)
    suspend fun process(
        @Field("loginName") studentId: String,
        @Field("password") password: String,
        @Field("overLogin") overLogin: String,
        @Field("Return_Url") returnUrl: String
    ): Response<Unit>
}