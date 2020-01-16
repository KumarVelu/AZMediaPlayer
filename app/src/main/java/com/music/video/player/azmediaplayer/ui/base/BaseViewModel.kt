package com.music.video.player.azmediaplayer.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.music.video.player.azmediaplayer.utils.NetworkHelper
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel(
        protected val compositeDisposable: CompositeDisposable,
        protected val networkHelper: NetworkHelper
) : ViewModel() {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val messageStringId: MutableLiveData<Int> = MutableLiveData()
    val messageString: MutableLiveData<String> = MutableLiveData()

    protected fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    protected fun handleNetworkError(err: Throwable?) =
            err?.let {
                // handle the error
            }

    abstract fun onCreate()
}