package com.music.video.player.azmediaplayer.di.component

import com.music.video.player.azmediaplayer.di.ActivityScope
import com.music.video.player.azmediaplayer.di.module.ActivityModule
import com.music.video.player.azmediaplayer.ui.main.MainActivity

import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}
