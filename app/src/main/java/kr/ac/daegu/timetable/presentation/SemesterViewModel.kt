package kr.ac.daegu.timetable.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.base.BaseViewModel
import kr.ac.daegu.timetable.core.utils.NetworkUtil
import kr.ac.daegu.timetable.domain.timetable.usecase.GetTimetableUseCase
import kr.ac.daegu.timetable.presentation.utils.Constants
import javax.inject.Inject

@HiltViewModel
class SemesterViewModel @Inject constructor(
    private val getTimetableUseCase: GetTimetableUseCase,
    private val networkUtil: NetworkUtil
) : BaseViewModel() {

    init {
        setFlow(Constants.READY, false)

        // 시간표를 가지고 있는지 체크
    }

    fun doNext(year: String, semester: String) = viewModelScope.launch {
        setFlow(Constants.READY, true)
        if (!networkUtil.isNetworkAvailable()) {
            sendEvent(BaseEvent.NetworkFail)
            return@launch
        }
        withContext(Dispatchers.IO) {
            getTimetableUseCase(year, semester).onSuccess {
                // 시간표 파싱 및 저장
            }
        }
    }
}