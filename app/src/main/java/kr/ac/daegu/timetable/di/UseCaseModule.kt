package kr.ac.daegu.timetable.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.ac.daegu.timetable.domain.login.repository.LoginRepository
import kr.ac.daegu.timetable.domain.login.usecase.LoginUseCase
import kr.ac.daegu.timetable.domain.timetable.repository.TimetableRepository
import kr.ac.daegu.timetable.domain.timetable.usecase.GetTimetableUseCase

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetTimetableUseCase(timetableRepository: TimetableRepository) =
        GetTimetableUseCase(timetableRepository)

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository) =
        LoginUseCase(loginRepository)
}