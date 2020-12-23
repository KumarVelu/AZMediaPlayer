package com.music.video.player.azmediaplayer.ui.home

import androidx.lifecycle.MutableLiveData
import com.music.video.player.azmediaplayer.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    val testData = MutableLiveData<String>()

    override fun onCreate() {
        testData.postValue("Hello from MainViewModel")
    }
}
