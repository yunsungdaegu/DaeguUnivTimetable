package kr.ac.daegu.timetable.data.timetable.mapper

import kr.ac.daegu.timetable.data.timetable.entity.SEL2Entity
import kr.ac.daegu.timetable.data.timetable.entity.SEL5Entity
import kr.ac.daegu.timetable.domain.timetable.model.SEL2
import kr.ac.daegu.timetable.domain.timetable.model.SEL5

fun List<SEL2Entity>.mapperToSEL2List(): List<SEL2> = this.map {
    SEL2(
        it.GWAMOK,
        it.TM_START_H,
        it.TM_START_M,
        it.TM_END_H,
        it.TM_END_M,
        it.YOIL,
        it.NAME_KR,
        it.PROF_NM,
        it.SUUP_ROOMNAME
    )
}

fun List<SEL5Entity>.mapperToSEL5List(): List<SEL5> = this.map {
    SEL5(
        it.GWAMOK,
        it.NAME_KR,
        it.PROF_NM
    )
}

fun List<SEL2>.mapperToSEL2EntityList(): List<SEL2Entity> = this.map {
    SEL2Entity(
        it.GWAMOK,
        it.TM_START_H,
        it.TM_START_M,
        it.TM_END_H,
        it.TM_END_M,
        it.YOIL,
        it.NAME_KR,
        it.PROF_NM,
        it.SUUP_ROOMNAME
    )
}

fun List<SEL5>.mapperToSEL5EntityList(): List<SEL5Entity> = this.map {
    SEL5Entity(
        it.GWAMOK,
        it.NAME_KR,
        it.PROF_NM
    )
}