package kr.ac.daegu.timetable.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.base.BaseViewModel
import kr.ac.daegu.timetable.data.timetable.dao.TimetableDao
import kr.ac.daegu.timetable.data.timetable.mapper.mapperToSEL2List
import kr.ac.daegu.timetable.data.timetable.mapper.mapperToSEL5List
import kr.ac.daegu.timetable.data.timetable.repository.datasource.TimetableDataStore
import kr.ac.daegu.timetable.domain.timetable.model.SEL2
import kr.ac.daegu.timetable.presentation.utils.Constants
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val timetableDao: TimetableDao,
    private val timetableDataStore: TimetableDataStore
) : BaseViewModel() {

    var sel2: List<SEL2>? = null

    init {
        viewModelScope.launch {
            var name = ""
            timetableDataStore.readTimetableConfig()?.let {
                name = it.name
                setFlow(Constants.TIMETABLE_NAME, "${it.name}님의 시간표")
                setFlow(Constants.TIMETABLE_SEMESTER, "${it.year}/${it.semester}학기")
            }

            setFlow(Constants.TIMETABLE_SEL5)
            withContext(Dispatchers.IO) {
                // 가상 시간표
                var sel5 = ""
                val sel5List = timetableDao.getTimetableSEL5().mapperToSEL5List()
                sel5List.forEachIndexed { index, seL5i ->
                    if (index == 0) sel5 = "가상 과목 : "
                    sel5 += "${seL5i.NAME_KR}(${seL5i.PROF_NM})"
                    if (index != sel5List.lastIndex) sel5 += ", "
                }
                setFlow(Constants.TIMETABLE_SEL5, sel5)

                // 시간표
                sel2 = timetableDao.getTimetableSEL2().mapperToSEL2List()
                sendEvent(TimetableEvent.LoadingSuccess(name))
            }
        }
    }

    interface TimetableEvent {
        data class LoadingSuccess(val name: String): BaseEvent
    }
}