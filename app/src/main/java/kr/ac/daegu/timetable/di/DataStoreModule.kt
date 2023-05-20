package kr.ac.daegu.timetable.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.ac.daegu.timetable.data.login.repository.datasource.StudentDataStore
import kr.ac.daegu.timetable.data.timetable.repository.datasource.TimetableDataStore

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    private val Context.studentDataStore by preferencesDataStore(name = "student")
    private val Context.timetableDataStore by preferencesDataStore(name = "timetable")

    @Provides
    fun provideTimetableDataStore(@ApplicationContext context: Context) =
        TimetableDataStore(context.timetableDataStore)

    @Provides
    fun provideStudentDataStore(@ApplicationContext context: Context) =
        StudentDataStore(context.studentDataStore)
}