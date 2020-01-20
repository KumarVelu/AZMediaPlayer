package com.music.video.player.azmediaplayer.di.component

import com.music.video.player.azmediaplayer.di.ViewModelScope
import com.music.video.player.azmediaplayer.di.module.ViewHolderModule
import com.music.video.player.azmediaplayer.ui.video.VideoItemViewHolder
import dagger.Component

@ViewModelScope
@Component(dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class])
interface ViewHolderComponent {

    fun inject(viewHolder: VideoItemViewHolder)
}