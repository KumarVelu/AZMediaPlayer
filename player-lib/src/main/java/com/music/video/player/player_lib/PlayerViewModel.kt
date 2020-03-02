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
import com.music.video.player.player_lib.data.model.VideoMetaData


class PlayerViewModel(
    private val application: Application,
    private val videoMetaDataList: List<VideoMetaData>
): ViewModel() {

    private val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(application, "az-media-player")
    private var mediaSourceFactory: ProgressiveMediaSource.Factory
    val mediaSource: MutableLiveData<MediaSource> = MutableLiveData()

    init {
        mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
        loadMediaSource()
    }

    private fun loadMediaSource() {
        mediaSource.postValue(buildMediaSource())
    }

    private fun buildMediaSource(): MediaSource{

        val concatenatingMediaSource = ConcatenatingMediaSource()
        for (videoMetaData in videoMetaDataList){
            val mediaSource = mediaSourceFactory
                .createMediaSource(Uri.parse(videoMetaData.path))
            concatenatingMediaSource.addMediaSource(mediaSource)
        }

        return concatenatingMediaSource
    }
}

