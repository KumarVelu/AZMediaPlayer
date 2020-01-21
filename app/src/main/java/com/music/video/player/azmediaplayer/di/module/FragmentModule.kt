package com.music.video.player.azmediaplayer.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.music.video.player.azmediaplayer.data.repository.VideoRepository
import com.music.video.player.azmediaplayer.di.ActivityContext
import com.music.video.player.azmediaplayer.ui.base.BaseFragment
import com.music.video.player.azmediaplayer.ui.home.HomeViewModel
import com.music.video.player.azmediaplayer.ui.music.MusicListViewModel
import com.music.video.player.azmediaplayer.ui.video.VideoListViewModel
import com.music.video.player.azmediaplayer.ui.video.VideosAdapter
import com.music.video.player.azmediaplayer.utils.ViewModelProviderFactory
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLinearLayoutManager() = LinearLayoutManager(fragment.context)

    @Provides
    fun provideVideosAdapter() = VideosAdapter(fragment.lifecycle, ArrayList(), fragment as VideosAdapter.IItemClickListener)

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable
    ): HomeViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(HomeViewModel::class) {
            HomeViewModel(schedulerProvider, compositeDisposable)
        }).get(HomeViewModel::class.java)

    @Provides
    fun provideVideoListViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        videoRepository: VideoRepository
    ): VideoListViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(VideoListViewModel::class) {
            VideoListViewModel(schedulerProvider, compositeDisposable, videoRepository)
        }).get(VideoListViewModel::class.java)

    @Provides
    fun provideMusicListViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable
    ): MusicListViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(MusicListViewModel::class) {
            MusicListViewModel(schedulerProvider, compositeDisposable)
        }).get(MusicListViewModel::class.java)

}
