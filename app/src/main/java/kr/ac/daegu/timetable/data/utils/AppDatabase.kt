package kr.ac.daegu.timetable.data.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.ac.daegu.timetable.data.timetable.dao.TimetableDao
import kr.ac.daegu.timetable.data.timetable.entity.SEL2Entity
import kr.ac.daegu.timetable.data.timetable.entity.SEL5Entity

@Database(
    entities = [
        SEL2Entity::class,
        SEL5Entity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB = "daegu.db"
    }

    abstract fun timetableDao(): TimetableDao
}