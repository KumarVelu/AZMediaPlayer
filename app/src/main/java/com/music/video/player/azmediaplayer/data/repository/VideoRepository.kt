package com.music.video.player.azmediaplayer.data.repository

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.WorkerThread
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import com.music.video.player.azmediaplayer.di.ApplicationContext
import com.music.video.player.azmediaplayer.utils.DBUtils
import com.music.video.player.azmediaplayer.utils.common.FileUtil
import java.lang.Exception
import javax.inject.Inject

class VideoRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    companion object {
        private const val TAG = "VideoRepository"
    }

    private val videoListProjection = arrayOf(
        MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.MIME_TYPE
    )

    @WorkerThread
    fun fetchAllVideos(): List<AdapterItem.Video> {

        val videoList = mutableListOf<AdapterItem.Video>()

        var videoCursor: Cursor? = null

        try {
            videoCursor = appContext.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                videoListProjection, null, null,
                MediaStore.Video.Media.DATE_ADDED + " DESC "
            )

            Log.i(TAG, "fetchAllVideos: ${videoCursor?.count}")

            videoCursor?.let {
                while (it.moveToNext()){
                    val fileSize = it.getLong(it.getColumnIndex(MediaStore.Video.Media.SIZE))

                    videoList.add(
                        AdapterItem.Video(
                            videoId = it.getLong(it.getColumnIndex(MediaStore.Video.Media._ID)),
                            displayName = it.getString(it.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)),
                            durationInMs = it.getLong(it.getColumnIndex(MediaStore.Video.Media.DURATION)),
                            path = it.getString(it.getColumnIndex(MediaStore.Video.Media.DATA)),
                            dateAdded = it.getLong(it.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)),
                            fileSize = FileUtil.getReadableFileSize(fileSize),
                            resolution = it.getString(it.getColumnIndex(MediaStore.Video.Media.RESOLUTION)),
                            mimeType = it.getString(it.getColumnIndex(MediaStore.Video.Media.MIME_TYPE))
                        )
                    )
                }
            }
        }catch (e: Exception){
            Log.e(TAG, "fetchAllVideos: $e")
        }
        finally {
            DBUtils.closeCursor(videoCursor)
        }

        return videoList
    }

}