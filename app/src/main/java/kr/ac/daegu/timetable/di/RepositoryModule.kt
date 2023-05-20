package kr.ac.daegu.timetable.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.ac.daegu.timetable.data.login.repository.LoginRepositoryImpl
import kr.ac.daegu.timetable.data.timetable.repository.TimetableRepositoryImpl
import kr.ac.daegu.timetable.data.utils.RequestUtil
import kr.ac.daegu.timetable.domain.login.repository.LoginRepository
import kr.ac.daegu.timetable.domain.timetable.repository.TimetableRepository

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideTimetableRepository(): TimetableRepository =
        TimetableRepositoryImpl()

    @Provides
    fun provideLoginRepository(
        requestUtil: RequestUtil
    ): LoginRepository = LoginRepositoryImpl(requestUtil)
}