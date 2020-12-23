package com.music.video.player.azmediaplayer.ui.base

import androidx.lifecycle.MutableLiveData

abstract class BaseItemViewModel<T: Any>(
): BaseViewModel() {

    val data: MutableLiveData<T> = MutableLiveData()

    fun onManualCleared() = onCleared()

    fun updateData(data: T){
        this.data.postValue(data)
    }
}