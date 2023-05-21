package kr.ac.daegu.timetable.data.timetable.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sel2")
data class SEL2Entity(
    @PrimaryKey
    var GWAMOK: String,
    var TM_START_H: Int,
    var TM_START_M: Int,
    var TM_END_H: Int,
    var TM_END_M: Int,
    var YOIL: String,
    var NAME_KR: String,
    var PROF_NM: String,
    var SUUP_ROOMNAME: String
)