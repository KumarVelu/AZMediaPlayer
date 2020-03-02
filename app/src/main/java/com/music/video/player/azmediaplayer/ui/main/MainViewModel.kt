package com.music.video.player.azmediaplayer.ui.main

import android.Manifest
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.music.video.player.azmediaplayer.di.ApplicationContext
import com.music.video.player.azmediaplayer.ui.base.BaseViewModel
import com.music.video.player.azmediaplayer.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import pub.devrel.easypermissions.EasyPermissions

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BaseViewModel(schedulerProvider, compositeDisposable) {

    override fun onCreate() {

    }

}
