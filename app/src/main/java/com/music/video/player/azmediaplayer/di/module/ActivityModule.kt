package com.music.video.player.azmediaplayer.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.music.video.player.azmediaplayer.data.local.DatabaseService
import com.music.video.player.azmediaplayer.data.remote.NetworkService
import com.music.video.player.azmediaplayer.di.ActivityContext
import com.music.video.player.azmediaplayer.ui.base.BaseActivity
import com.music.video.player.azmediaplayer.ui.main.MainViewModel
import com.music.video.player.azmediaplayer.utils.NetworkHelper
import com.music.video.player.azmediaplayer.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    @Provides
    fun provideMainViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        databaseService: DatabaseService,
        networkService: NetworkService
    ): MainViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(compositeDisposable, networkHelper, databaseService, networkService)
        }).get(MainViewModel::class.java)
}