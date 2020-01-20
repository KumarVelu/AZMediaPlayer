package com.music.video.player.azmediaplayer.di.component

import android.content.Context
import com.music.video.player.azmediaplayer.MyApplication
import com.music.video.player.azmediaplayer.data.local.DatabaseService
import com.music.video.player.azmediaplayer.data.remote.NetworkService
import com.music.video.player.azmediaplayer.di.ApplicationContext
import com.music.video.player.azmediaplayer.di.module.ApplicationModule
import com.music.video.player.azmediaplayer.utils.NetworkHelper
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getNetworkHelper(): NetworkHelper

    fun getCompositeDisposable(): CompositeDisposable

    fun getSchedulerProvider(): SchedulerProvider
}
