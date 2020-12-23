package com.music.video.player.azmediaplayer.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.music.video.player.azmediaplayer.data.model.Video
import com.music.video.player.azmediaplayer.ui.base.BaseItemViewModel
import javax.inject.Inject

class VideoItemViewModel @Inject constructor(
): BaseItemViewModel<Video>() {

    override fun onCreate() {

    }

    val title: LiveData<String> = Transformations.map(data) { it.displayName }
    val duration: LiveData<Long> = Transformations.map(data) { it.durationInMs }
    val path: LiveData<String> = Transformations.map(data) { it.path }

}