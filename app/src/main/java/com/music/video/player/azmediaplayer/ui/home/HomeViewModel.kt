package com.music.video.player.azmediaplayer.ui.home

import androidx.lifecycle.MutableLiveData
import com.music.video.player.azmediaplayer.data.local.DatabaseService
import com.music.video.player.azmediaplayer.data.remote.NetworkService
import com.music.video.player.azmediaplayer.ui.base.BaseViewModel
import com.music.video.player.azmediaplayer.utils.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel constructor(
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) : BaseViewModel(compositeDisposable, networkHelper) {

    val testData = MutableLiveData<String>()

    override fun onCreate() {
        testData.postValue("Hello from MainViewModel")
    }
}
