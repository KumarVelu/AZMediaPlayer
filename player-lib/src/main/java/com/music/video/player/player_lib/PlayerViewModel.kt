package com.music.video.player.player_lib

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory


class PlayerViewModel(
    private val application: Application,
    private val mediaPathList: List<String>
): ViewModel() {

    private val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(application, "az-media-player")
    private var mediaSourceFactory: ProgressiveMediaSource.Factory
    val mediaSource: MutableLiveData<MediaSource> = MutableLiveData()

    init {
        mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
        loadMediaSource()
    }

    private fun loadMediaSource() {
        mediaSource.postValue(buildMediaSource(mediaPathList))
    }


    private fun buildMediaSource(pathList: List<String>): MediaSource{

        val concatenatingMediaSource = ConcatenatingMediaSource()
        for (path in pathList){
            val mediaSource = mediaSourceFactory.createMediaSource(Uri.parse(path))
            concatenatingMediaSource.addMediaSource(mediaSource)
        }

        return concatenatingMediaSource
    }
}

