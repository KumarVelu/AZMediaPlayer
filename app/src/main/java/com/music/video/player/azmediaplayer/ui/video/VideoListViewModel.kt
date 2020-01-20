package com.music.video.player.azmediaplayer.ui.video

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.music.video.player.azmediaplayer.data.model.Video
import com.music.video.player.azmediaplayer.data.repository.VideoRepository
import com.music.video.player.azmediaplayer.ui.base.BaseViewModel
import com.music.video.player.azmediaplayer.utils.common.Resource
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class VideoListViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    private val videoRepository: VideoRepository
) : BaseViewModel(schedulerProvider, compositeDisposable) {

    companion object {
        private const val TAG = "VideoListViewModel"
    }

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val videoList: MutableLiveData<Resource<List<Video>>> = MutableLiveData()

    override fun onCreate() {
    }

    init {
        loadAllVideos()
    }

    private fun loadAllVideos() {

        loading.postValue(true)
        compositeDisposable.addAll(
            videoRepository.fetchAllVideos()
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        videoList.postValue(Resource.success(it))
                        loading.postValue(false)
                    },
                    {
                        handleError(it)
                        loading.postValue(false)
                    }
                )
        )
    }

    private fun handleError(it: Throwable) {
        Log.e(TAG, "handleError: $it")
    }


}