package kr.ac.daegu.timetable.domain.login.repository

interface LoginRepository {

    suspend fun login(
        studentId: String,
        password: String
    )
}