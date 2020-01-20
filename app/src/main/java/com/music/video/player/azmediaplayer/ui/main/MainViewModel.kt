package com.music.video.player.azmediaplayer.ui.main

import androidx.lifecycle.MutableLiveData
import com.music.video.player.azmediaplayer.ui.base.BaseViewModel
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BaseViewModel(schedulerProvider, compositeDisposable) {

    val testData = MutableLiveData<String>()

    override fun onCreate() {
        testData.postValue("Hello from MainViewModel")
    }
}
