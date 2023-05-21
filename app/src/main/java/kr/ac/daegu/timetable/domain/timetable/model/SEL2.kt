package kr.ac.daegu.timetable.domain.timetable.model

data class SEL2(
    var GWAMOK: String,
    var TM_START_H: Int = 0,
    var TM_START_M: Int = 0,
    var TM_END_H: Int = 0,
    var TM_END_M: Int = 0,
    var YOIL: String = "",
    var NAME_KR: String = "",
    var PROF_NM: String = "",
    var SUUP_ROOMNAME: String = ""
)