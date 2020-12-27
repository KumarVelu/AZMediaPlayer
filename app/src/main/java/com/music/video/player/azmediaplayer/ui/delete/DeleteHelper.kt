package com.music.video.player.azmediaplayer.ui.delete

import android.app.Activity
import android.content.ContentUris
import android.content.IntentSender
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.music.video.player.azmediaplayer.data.model.AdapterItem


object DeleteHelper {

    const val REQUEST_PERM_DELETE = 201

    fun deleteVideos(activity: Activity, videoList: List<AdapterItem.Video>) {
        deleteUsingScopedStorage(activity, videoList)
    }

    private fun deleteUsingScopedStorage(activity: Activity, videoList: List<AdapterItem.Video>) {

        val uriList = mutableListOf<Uri>()
        videoList.forEach {
            uriList.add(
                ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    it.videoId
                )
            )
        }

        val pendingIntent = MediaStore.createDeleteRequest(activity.contentResolver, uriList)
        try {
            activity.startIntentSenderForResult(
                pendingIntent.intentSender, REQUEST_PERM_DELETE, null, 0, 0,
                0
            )
        } catch (e: IntentSender.SendIntentException) {
        }
    }
}