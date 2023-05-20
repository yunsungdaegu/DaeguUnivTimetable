package kr.ac.daegu.timetable.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val _eventFlow = MutableSharedFlow<BaseEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun sendEvent(event: BaseEvent) =
        viewModelScope.launch {
            _eventFlow.emit(event)
        }

    private val _flow = HashMap<String, MutableStateFlow<Any?>>()

    protected fun setFlow(key: String, any: Any? = "") {
        if (_flow.containsKey(key))
            _flow[key]?.value = any
        else
            _flow[key] = MutableStateFlow(any)
    }
}