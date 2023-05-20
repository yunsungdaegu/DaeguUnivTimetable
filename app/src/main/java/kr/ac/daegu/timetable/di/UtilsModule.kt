package kr.ac.daegu.timetable.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.ac.daegu.timetable.data.utils.RequestUtil

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    fun provideRequestUtil() = RequestUtil()
}