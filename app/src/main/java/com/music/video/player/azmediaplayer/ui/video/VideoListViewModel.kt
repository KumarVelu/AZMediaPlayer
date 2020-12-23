package com.music.video.player.azmediaplayer.ui.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.music.video.player.azmediaplayer.data.model.Video
import com.music.video.player.azmediaplayer.data.repository.VideoRepository
import com.music.video.player.azmediaplayer.ui.base.BaseViewModel
import com.music.video.player.azmediaplayer.utils.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoListViewModel(
    private val videoRepository: VideoRepository
) : BaseViewModel() {

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

        viewModelScope.launch {
            loading.postValue(true)

            withContext(Dispatchers.IO){
                videoList.postValue(Resource.success(videoRepository.fetchAllVideos()))
                loading.postValue(true)
            }
        }
    }

}