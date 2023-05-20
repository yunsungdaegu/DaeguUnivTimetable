package kr.ac.daegu.timetable.data.timetable.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.ac.daegu.timetable.data.timetable.entity.SEL2Entity
import kr.ac.daegu.timetable.data.timetable.entity.SEL5Entity

@Dao
interface TimetableDao {

    @Query("SELECT * FROM sel2")
    fun getTimetableSEL2(): List<SEL2Entity>

    @Query("SELECT * FROM sel5")
    fun getTimetableSEL5(): List<SEL5Entity>

    @Insert
    fun insertTimetableSEL2(seL2Entity: List<SEL2Entity>)

    @Insert
    fun insertTimetableSEL5(seL5Entity: List<SEL5Entity>)

    @Query("DELETE FROM sel2")
    fun removeAllTimetableSEL2()

    @Query("DELETE FROM sel5")
    fun removeAllTimetableSEL5()
}