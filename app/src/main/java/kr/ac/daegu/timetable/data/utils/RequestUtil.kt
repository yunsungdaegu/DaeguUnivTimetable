package kr.ac.daegu.timetable.data.utils

class RequestUtil {

    suspend fun <T> getRequest(requestApi: suspend () -> (retrofit2.Response<T>)) =
        kr.ac.daegu.timetable.data.utils.getRequest(requestApi)
}