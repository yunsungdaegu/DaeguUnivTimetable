package kr.ac.daegu.timetable.data.timetable.repository.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kr.ac.daegu.timetable.domain.timetable.model.TimetableConfig

class TimetableDataStore(private val dataStore: DataStore<Preferences>) {

    companion object {
        const val name = "name"
        const val year = "year"
        const val semester = "semester"
    }

    suspend fun saveTimetableConfig(config: TimetableConfig) {
        dataStore.edit {
            it[stringPreferencesKey(name)] = config.name
            it[stringPreferencesKey(year)] = config.year
            it[stringPreferencesKey(semester)] = config.semester
        }
    }

    suspend fun readTimetableConfig(): TimetableConfig? {
        val preferences = dataStore.data.first()
        val name = preferences[stringPreferencesKey(name)]
        val year = preferences[stringPreferencesKey(year)]
        val semester = preferences[stringPreferencesKey(semester)]
        return if (name.isNullOrEmpty()) null
        else TimetableConfig(name, year!!, semester!!)
    }
}