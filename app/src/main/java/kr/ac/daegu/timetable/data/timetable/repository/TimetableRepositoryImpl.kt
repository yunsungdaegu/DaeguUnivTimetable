package kr.ac.daegu.timetable.data.timetable.repository

import kr.ac.daegu.timetable.BuildConfig
import kr.ac.daegu.timetable.data.utils.connect
import kr.ac.daegu.timetable.domain.timetable.repository.TimetableRepository

class TimetableRepositoryImpl : TimetableRepository {

    override suspend fun getTimetable(year: String, semester: String): String {
        val postParams = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\"><Parameters><Parameter id=\"YEAR\">${year}</Parameter><Parameter id=\"HAKGI\">${semester}</Parameter></Parameters></Root>"
        return connect(BuildConfig.TIGERSSTD_TIMETABLE, postParams)
    }
}