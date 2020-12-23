package com.music.video.player.azmediaplayer.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.music.video.player.azmediaplayer.di.ActivityContext
import com.music.video.player.azmediaplayer.ui.base.BaseActivity
import com.music.video.player.azmediaplayer.ui.main.MainViewModel
import com.music.video.player.azmediaplayer.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    @Provides
    fun provideMainViewModel(
    ): MainViewModel = ViewModelProvider(
        activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel()
        }).get(MainViewModel::class.java)
}