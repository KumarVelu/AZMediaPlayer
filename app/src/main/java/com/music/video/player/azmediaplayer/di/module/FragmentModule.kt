package com.music.video.player.azmediaplayer.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.music.video.player.azmediaplayer.ui.home.folder.FolderListViewModel
import com.music.video.player.azmediaplayer.data.repository.FolderRepository
import com.music.video.player.azmediaplayer.data.repository.VideoRepository
import com.music.video.player.azmediaplayer.ui.base.BaseFragment
import com.music.video.player.azmediaplayer.ui.home.folder.FolderListAdapter
import com.music.video.player.azmediaplayer.ui.music.MusicListViewModel
import com.music.video.player.azmediaplayer.ui.home.video.VideoListViewModel
import com.music.video.player.azmediaplayer.ui.home.video.VideoListAdapter
import com.music.video.player.azmediaplayer.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideVideosAdapter() = VideoListAdapter(fragment.context!!, fragment as VideoListAdapter.IItemClickListener)

    @Provides
    fun provideFolderListAdapter() = FolderListAdapter(fragment.context!!)

    @Provides
    fun provideVideoListViewModel(
        videoRepository: VideoRepository
    ): VideoListViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(VideoListViewModel::class) {
            VideoListViewModel(videoRepository)
        }).get(VideoListViewModel::class.java)

    @Provides
    fun provideFolderListViewModel(
        folderRepository: FolderRepository
    ): FolderListViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(FolderListViewModel::class){
            FolderListViewModel(
                folderRepository
            )
        }).get(FolderListViewModel::class.java)

    @Provides
    fun provideMusicListViewModel(
    ): MusicListViewModel = ViewModelProvider(
        fragment, ViewModelProviderFactory(MusicListViewModel::class) {
            MusicListViewModel()
        }).get(MusicListViewModel::class.java)

}
