package kr.ac.daegu.timetable.data.timetable.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sel5")
data class SEL5Entity(
    @PrimaryKey
    var GWAMOK: Int,
    var NAME_KR: String,
    var PROF_NM: String
)