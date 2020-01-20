package com.music.video.player.azmediaplayer.di.module

import android.content.Context
import com.music.video.player.azmediaplayer.MyApplication
import com.music.video.player.azmediaplayer.data.repository.VideoRepository
import com.music.video.player.azmediaplayer.di.ApplicationContext
import com.music.video.player.azmediaplayer.di.DatabaseInfo
import com.music.video.player.azmediaplayer.di.NetworkInfo
import com.music.video.player.azmediaplayer.utils.rx.RxScheduleProvider
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ApplicationModule(private val application: MyApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context = application

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String = "dummy_db"

    @Provides
    @DatabaseInfo
    fun provideDatabaseVersion(): Int = 1

    @Provides
    @NetworkInfo
    fun provideApiKey(): String = "SOME_API_KEY"

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideVideoRepository(): VideoRepository = VideoRepository(application)

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxScheduleProvider()
}
