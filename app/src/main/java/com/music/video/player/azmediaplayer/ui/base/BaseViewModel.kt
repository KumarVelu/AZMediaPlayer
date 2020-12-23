package com.music.video.player.azmediaplayer.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.music.video.player.azmediaplayer.utils.common.Resource


abstract class BaseViewModel(

) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    val messageStringId: MutableLiveData<Resource<Int>> = MutableLiveData()
    val messageString: MutableLiveData<Resource<String>> = MutableLiveData()

    abstract fun onCreate()
}