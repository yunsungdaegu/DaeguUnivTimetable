package kr.ac.daegu.timetable.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.ac.daegu.timetable.core.utils.NetworkUtil
import kr.ac.daegu.timetable.data.utils.RequestUtil

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    fun provideRequestUtil() = RequestUtil()

    @Provides
    fun provideNetworkUtil(@ApplicationContext context: Context) =
        NetworkUtil(context)
}