package com.music.video.player.azmediaplayer.utils

import android.media.MediaMetadataRetriever

class AppUtils {

    companion object{

        fun getDuration(filePath: String): Long{

            /*val mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(filePath)
            val timeInStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

            val timeInMilliSec = timeInStr.toLong()
            mediaMetadataRetriever.release()

            return timeInMilliSec*/

            return 0L
        }

    }
}