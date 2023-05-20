package kr.ac.daegu.timetable.data.login.repository.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kr.ac.daegu.timetable.domain.login.model.Student

class StudentDataStore(private val dataStore: DataStore<Preferences>) {

    companion object {
        const val studentId = "studentId"
        const val password = "password"
    }

    suspend fun saveStudent(student: Student) {
        dataStore.edit {
            it[stringPreferencesKey(studentId)] = student.studentId
            it[stringPreferencesKey(password)] = student.password
        }
    }

    suspend fun readStudent(): Student? {
        val preferences = dataStore.data.first()
        val studentId = preferences[stringPreferencesKey(studentId)]
        val password = preferences[stringPreferencesKey(password)]
        return if ((studentId == null) or (password == null)) null
        else Student(studentId!!, password!!)
    }
}