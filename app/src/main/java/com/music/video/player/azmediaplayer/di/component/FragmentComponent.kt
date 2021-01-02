package com.music.video.player.azmediaplayer.di.component

import com.music.video.player.azmediaplayer.di.FragmentScope
import com.music.video.player.azmediaplayer.di.module.FragmentModule
import com.music.video.player.azmediaplayer.ui.home.folder.FolderListFragment
import com.music.video.player.azmediaplayer.ui.music.MusicListFragment
import com.music.video.player.azmediaplayer.ui.home.video.VideoListFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: VideoListFragment)
    fun inject(fragment: MusicListFragment)
    fun inject(fragment: FolderListFragment)
}
