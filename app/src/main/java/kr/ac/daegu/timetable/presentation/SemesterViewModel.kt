package kr.ac.daegu.timetable.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.ac.daegu.timetable.core.base.BaseViewModel
import kr.ac.daegu.timetable.presentation.utils.Constants
import javax.inject.Inject

@HiltViewModel
class SemesterViewModel @Inject constructor(

) : BaseViewModel() {

    init {
        setFlow(Constants.READY, false)

        // 시간표를 가지고 있는지 체크
    }

    fun doNext(year: String, semester: String) {
        setFlow(Constants.READY, true)
    }
}