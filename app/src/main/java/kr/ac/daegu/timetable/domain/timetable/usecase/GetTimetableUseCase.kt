package kr.ac.daegu.timetable.domain.timetable.usecase

import kr.ac.daegu.timetable.domain.timetable.repository.TimetableRepository

class GetTimetableUseCase(private val repo: TimetableRepository) {

    suspend operator fun invoke(
        year: String,
        semester: String
    ) = runCatching {
        repo.getTimetable(year, semester)
    }
}