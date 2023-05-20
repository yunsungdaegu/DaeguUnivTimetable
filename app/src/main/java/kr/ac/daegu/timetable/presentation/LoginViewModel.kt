package kr.ac.daegu.timetable.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.ac.daegu.timetable.core.base.BaseViewModel
import kr.ac.daegu.timetable.domain.login.usecase.LoginUseCase
import kr.ac.daegu.timetable.presentation.utils.Constants
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    init {
        setFlow(Constants.LOGIN_STUDENT_ID)
        setFlow(Constants.LOGIN_PASSWORD)
    }

    fun doLogin() {
        val studentId = getStringFlow(Constants.LOGIN_STUDENT_ID).value
        val password = getStringFlow(Constants.LOGIN_PASSWORD).value
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loginUseCase(studentId, password).onSuccess {

                }.onFailure {

                }
            }
        }
    }
}