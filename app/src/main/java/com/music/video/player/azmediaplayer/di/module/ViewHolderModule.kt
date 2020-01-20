package com.music.video.player.azmediaplayer.di.module

import androidx.lifecycle.LifecycleRegistry
import com.music.video.player.azmediaplayer.ui.base.BaseItemViewHolder
import dagger.Module
import dagger.Provides

@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    @Provides
    fun provideLifecycleRegistry(): LifecycleRegistry = LifecycleRegistry(viewHolder)
}