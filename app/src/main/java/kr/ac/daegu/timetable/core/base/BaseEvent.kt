package kr.ac.daegu.timetable.core.base

interface BaseEvent {

    object NetworkFail: BaseEvent
    data class Error(val throwable: Throwable): BaseEvent
}