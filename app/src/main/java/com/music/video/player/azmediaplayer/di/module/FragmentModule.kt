package com.music.video.player.azmediaplayer.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.music.video.player.azmediaplayer.data.repository.VideoRepository
import com.music.video.player.azmediaplayer.ui.base.BaseFragment
import com.music.video.player.azmediaplayer.ui.home.HomeViewModel
import com.music.video.player.azmediaplayer.ui.music.MusicListViewModel
import com.music.video.player.azmediaplayer.ui.video.VideoListViewModel
import com.music.video.player.azmediaplayer.ui.video.VideoListAdapter
import com.music.video.player.azmediaplayer.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLinearLayoutManager() = LinearLayoutManager(fragment.context)

    @Provides
    fun provideVideosAdapter() = VideoListAdapter(fragment.lifecycle, ArrayList(), fragment as VideoListAdapter.IItemClickListener)

    @Provides
    fun provideMainViewModel(): HomeViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(HomeViewModel::class) {
            HomeViewModel()
        }).get(HomeViewModel::class.java)

    @Provides
    fun provideVideoListViewModel(
        videoRepository: VideoRepository
    ): VideoListViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(VideoListViewModel::class) {
            VideoListViewModel(videoRepository)
        }).get(VideoListViewModel::class.java)

    @Provides
    fun provideMusicListViewModel(
    ): MusicListViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(MusicListViewModel::class) {
            MusicListViewModel()
        }).get(MusicListViewModel::class.java)

}
