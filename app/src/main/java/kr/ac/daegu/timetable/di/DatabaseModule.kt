package kr.ac.daegu.timetable.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.ac.daegu.timetable.data.utils.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB)
        .build()

    @Provides
    fun provideTimetableDao(appDatabase: AppDatabase) =
        appDatabase.timetableDao()
}