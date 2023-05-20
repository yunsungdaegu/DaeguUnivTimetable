package kr.ac.daegu.timetable.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.daegu.timetable.core.base.BaseEvent
import kr.ac.daegu.timetable.core.base.BaseViewModel
import kr.ac.daegu.timetable.core.utils.NetworkUtil
import kr.ac.daegu.timetable.domain.login.usecase.LoginUseCase
import kr.ac.daegu.timetable.presentation.utils.Constants
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val networkUtil: NetworkUtil
) : BaseViewModel() {

    init {
        setFlow(Constants.LOGIN_STUDENT_ID)
        setFlow(Constants.LOGIN_PASSWORD)
    }

    fun doLogin() = viewModelScope.launch {
        val studentId = getStringFlow(Constants.LOGIN_STUDENT_ID).value
        val password = getStringFlow(Constants.LOGIN_PASSWORD).value
        if (!networkUtil.isNetworkAvailable()) {
            sendEvent(BaseEvent.NetworkFail)
            return@launch
        }
        withContext(Dispatchers.IO) {
            loginUseCase(studentId, password).onSuccess {
                if (it) {
                    // 로그인 성공
                } else {
                    // 로그인 실패
                    sendEvent(LoginEvent.LoginFail)
                }
            }.onFailure {
                sendEvent(BaseEvent.Error(it))
            }
        }
    }

    interface LoginEvent {
        object LoginFail: BaseEvent
    }
}