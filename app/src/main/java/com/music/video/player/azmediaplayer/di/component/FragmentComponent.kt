package com.music.video.player.azmediaplayer.di.component

import com.music.video.player.azmediaplayer.di.FragmentScope
import com.music.video.player.azmediaplayer.di.module.FragmentModule
import com.music.video.player.azmediaplayer.ui.home.HomeFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: HomeFragment)
}
