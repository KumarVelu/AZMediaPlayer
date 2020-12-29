package com.music.video.player.azmediaplayer.utils.video

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

object OverflowOptionsHelper {

    fun getShareIntent(videoId: Long) = Intent().apply {
        action = Intent.ACTION_SEND
        type =  "video/*"
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        val uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoId.toString())
        putExtra(Intent.EXTRA_STREAM, uri)
    }

}