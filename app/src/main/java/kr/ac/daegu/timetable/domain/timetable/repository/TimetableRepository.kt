package kr.ac.daegu.timetable.domain.timetable.repository

interface TimetableRepository {

    suspend fun getTimetable(
        year: String,
        semester: String
    )
}