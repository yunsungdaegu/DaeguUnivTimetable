package kr.ac.daegu.timetable.presentation

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.ac.daegu.timetable.core.base.BaseViewModel
import kr.ac.daegu.timetable.presentation.utils.Constants
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : BaseViewModel() {

    init {
        setFlow(Constants.LOGIN_STUDENT_ID)
        setFlow(Constants.LOGIN_PASSWORD)
    }

    fun doLogin() {
        val id = getStringFlow(Constants.LOGIN_STUDENT_ID).value
        val password = getStringFlow(Constants.LOGIN_PASSWORD).value
    }
}