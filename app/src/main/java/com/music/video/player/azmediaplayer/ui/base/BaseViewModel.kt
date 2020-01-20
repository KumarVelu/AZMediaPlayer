package com.music.video.player.azmediaplayer.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.music.video.player.azmediaplayer.utils.common.Resource
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable
) : ViewModel() {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()

    abstract fun onCreate()
}