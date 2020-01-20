package com.music.video.player.azmediaplayer.ui.music

import com.music.video.player.azmediaplayer.ui.base.BaseViewModel
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MusicListViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BaseViewModel(schedulerProvider, compositeDisposable) {

    override fun onCreate() {

    }

}