package com.music.video.player.azmediaplayer.ui.home.folder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import com.music.video.player.azmediaplayer.data.repository.FolderRepository
import com.music.video.player.azmediaplayer.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FolderListViewModel(
    private val folderRepository: FolderRepository
): BaseViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val folderToVideoMapLiveData: MutableLiveData<MutableMap<String, MutableList<AdapterItem.Video>>> = MutableLiveData()

    override fun onCreate() {
        loadVideosBasedOnFolder()
    }

    private fun loadVideosBasedOnFolder() {

        viewModelScope.launch {
            loading.postValue(true)

            withContext(Dispatchers.IO){
                folderToVideoMapLiveData.postValue(folderRepository.fetchVideosByFolder())
            }

            loading.postValue(false)
        }
    }
}