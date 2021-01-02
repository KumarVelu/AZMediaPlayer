package com.music.video.player.azmediaplayer.ui.home.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import com.music.video.player.azmediaplayer.data.repository.VideoRepository
import com.music.video.player.azmediaplayer.ui.base.BaseViewModel
import com.music.video.player.azmediaplayer.utils.TimerUtils
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
    val adapterItemListLiveData: MutableLiveData<Resource<MutableList<AdapterItem>>> = MutableLiveData()

    override fun onCreate() {
    }

    init {
        loadAllVideos()
    }

    private fun loadAllVideos() {

        viewModelScope.launch {
            loading.postValue(true)

            withContext(Dispatchers.IO){

                val adapterItemVideoList = mutableListOf<AdapterItem>()

                var sectionHeader = ""
                videoRepository.fetchAllVideos().forEach { videoItem ->
                    val dateAddedStr = TimerUtils.getDateStringForVideoList(videoItem.dateAdded)

                    if(sectionHeader != dateAddedStr){
                        adapterItemVideoList.add(AdapterItem.SectionItem(dateAddedStr))
                        sectionHeader = dateAddedStr
                    }
                    adapterItemVideoList.add(videoItem)
                }
                adapterItemListLiveData.postValue(Resource.success(adapterItemVideoList))
                loading.postValue(false)
            }
        }
    }

}