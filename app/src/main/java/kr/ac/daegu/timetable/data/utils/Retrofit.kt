package kr.ac.daegu.timetable.data.utils

import android.util.Log
import kr.ac.daegu.timetable.BuildConfig
import kr.ac.daegu.timetable.data.login.api.LoginApi
import kr.ac.daegu.timetable.data.login.api.SSOApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

private var authApi = Retrofit.Builder()
    .baseUrl(BuildConfig.SSO_URL)
    .client(OkHttpClient.Builder().run {
        addInterceptor(AppInterceptor())
    }.build())
    .build()
private var tigersstdApi = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .client(OkHttpClient.Builder().run {
        addInterceptor(AppInterceptor())
    }.build())
    .build()

var cookie: String? = null

class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        cookie?.let { newRequest.addHeader("Cookie", it) }
        return chain.proceed(newRequest.build())
    }
}

internal val ssoApi = authApi.create(SSOApi::class.java)
internal val loginApi = tigersstdApi.create(LoginApi::class.java)

internal suspend fun <T> getRequest(
    requestApi: suspend () -> (retrofit2.Response<T>)
): Result<T> {
    val request = requestApi.invoke()
    return when (request.code()) {
        200 -> {
            log(request)
            val getCookie = request.headers()["Set-Cookie"].toString().split(";")
            getCookie.forEach {
                when (it.split("=")[0]) {
                    "SSOTOKEN" -> cookie = it
                    "SESSION_TIGERSSTD" -> cookie += ";${it}"
                }
            }
            Result.success(request.body()!!)
        }
        else -> Result.failure(EXCEPTION_CONNECT_FAIL)
    }
}

internal fun <T> log(response: retrofit2.Response<T>) {
    Log.d("log", "")
    Log.d("log", "${response.headers()}")
    Log.d("log", "$response")
    Log.d("log", "${response.body()}")
}

val EXCEPTION_CONNECT_FAIL = Exception("Server Connect Fail.")