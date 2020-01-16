package com.music.video.player.azmediaplayer.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.music.video.player.azmediaplayer.data.local.DatabaseService
import com.music.video.player.azmediaplayer.data.remote.NetworkService
import com.music.video.player.azmediaplayer.di.ActivityContext
import com.music.video.player.azmediaplayer.ui.base.BaseFragment
import com.music.video.player.azmediaplayer.ui.home.HomeViewModel
import com.music.video.player.azmediaplayer.utils.NetworkHelper
import com.music.video.player.azmediaplayer.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.context!!

    @Provides
    fun provideMainViewModel(
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        databaseService: DatabaseService,
        networkService: NetworkService
    ): HomeViewModel = ViewModelProviders.of(
            fragment, ViewModelProviderFactory(HomeViewModel::class) {
        HomeViewModel(compositeDisposable, networkHelper, databaseService, networkService)
    }).get(HomeViewModel::class.java)
}
