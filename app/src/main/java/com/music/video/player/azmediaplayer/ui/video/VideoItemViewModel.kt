package com.music.video.player.azmediaplayer.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.music.video.player.azmediaplayer.data.model.Video
import com.music.video.player.azmediaplayer.ui.base.BaseItemViewModel
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class VideoItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
): BaseItemViewModel<Video>(schedulerProvider, compositeDisposable) {

    override fun onCreate() {

    }

    val title: LiveData<String> = Transformations.map(data) { it.title }
    val duration: LiveData<Long> = Transformations.map(data) { it.duration }
    val path: LiveData<String> = Transformations.map(data) { it.path }

}