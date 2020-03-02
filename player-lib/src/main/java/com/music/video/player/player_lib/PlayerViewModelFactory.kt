package com.music.video.player.player_lib

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.music.video.player.player_lib.data.model.VideoMetaData

class PlayerViewModelFactory(
    private val application: Application,
    private val videoMetaDataList: List<VideoMetaData>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayerViewModel::class.java)){
            return PlayerViewModel(application, videoMetaDataList) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }
}