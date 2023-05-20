package kr.ac.daegu.timetable.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.base.BaseViewModel
import kr.ac.daegu.timetable.core.utils.NetworkUtil
import kr.ac.daegu.timetable.data.login.repository.datasource.StudentDataStore
import kr.ac.daegu.timetable.data.timetable.repository.datasource.TimetableDataStore
import kr.ac.daegu.timetable.domain.login.model.Student
import kr.ac.daegu.timetable.domain.login.usecase.LoginUseCase
import kr.ac.daegu.timetable.presentation.utils.Constants
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val networkUtil: NetworkUtil,
    private val studentDataStore: StudentDataStore,
    private val timetableDataStore: TimetableDataStore
) : BaseViewModel() {

    init {
        setFlow(Constants.LOGIN_STUDENT_ID)
        setFlow(Constants.LOGIN_PASSWORD)
        setFlow(Constants.READY, false)

        // auto login check
        viewModelScope.launch {
            studentDataStore.readStudent()?.let {
                getStringFlow(Constants.LOGIN_STUDENT_ID).value = it.studentId
                getStringFlow(Constants.LOGIN_PASSWORD).value = it.password
                isFlow(Constants.READY).value = true
                doLogin()
            }
        }
    }

    fun doLogin() = viewModelScope.launch {
        val studentId = getStringFlow(Constants.LOGIN_STUDENT_ID).value
        val password = getStringFlow(Constants.LOGIN_PASSWORD).value
        if (!networkUtil.isNetworkAvailable()) {
            sendEvent(BaseEvent.NetworkFail)
            return@launch
        }
        isFlow(Constants.READY).value = true
        withContext(Dispatchers.IO) {
            loginUseCase(studentId, password).onSuccess {
                if (it) {
                    // 로그인 성공
                    if (timetableDataStore.readTimetableConfig() == null)
                        sendEvent(LoginEvent.LoginSuccess)
                    else
                        sendEvent(LoginEvent.TimetableLoadSuccess)
                } else {
                    // 로그인 실패
                    sendEvent(LoginEvent.LoginFail)
                }
                isFlow(Constants.READY).value = false
                studentDataStore.saveStudent(Student(studentId, password))
            }.onFailure {
                sendEvent(BaseEvent.Error(it))
            }
        }
    }

    interface LoginEvent {
        object LoginSuccess: BaseEvent
        object LoginFail: BaseEvent
        object TimetableLoadSuccess: BaseEvent
    }
}