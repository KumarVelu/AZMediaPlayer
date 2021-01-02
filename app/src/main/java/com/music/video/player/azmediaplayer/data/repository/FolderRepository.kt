package com.music.video.player.azmediaplayer.data.repository

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import com.music.video.player.azmediaplayer.di.ApplicationContext
import com.music.video.player.azmediaplayer.utils.DBUtils
import com.music.video.player.azmediaplayer.utils.common.FileUtil
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class FolderRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    companion object {
        private const val TAG = "FolderRepository"
    }

    private val videoListProjection = arrayOf(
        MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA,
        MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.MIME_TYPE
    )

    fun fetchVideosByFolder(): MutableMap<String, MutableList<AdapterItem.Video>> {

        var videoCursor: Cursor? = null
        val folderToVideoMap = mutableMapOf<String, MutableList<AdapterItem.Video>>()

        try {
            videoCursor = appContext.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                videoListProjection, null, null,
                MediaStore.Video.Media.DATE_ADDED + " DESC "
            )

            videoCursor?.let {
                while (it.moveToNext()) {
                    val fileSize = it.getLong(it.getColumnIndex(MediaStore.Video.Media.SIZE))

                    val videoPath = it.getString(it.getColumnIndex(MediaStore.Video.Media.DATA))
                    val folderPath: String = File(videoPath).parent!!

                    val video = AdapterItem.Video(
                        videoId = it.getLong(it.getColumnIndex(MediaStore.Video.Media._ID)),
                        displayName = it.getString(it.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)),
                        durationInMs = it.getLong(it.getColumnIndex(MediaStore.Video.Media.DURATION)),
                        path = videoPath,
                        dateAdded = it.getLong(it.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)),
                        fileSize = FileUtil.getReadableFileSize(fileSize),
                        resolution = it.getString(it.getColumnIndex(MediaStore.Video.Media.RESOLUTION)),
                        mimeType = it.getString(it.getColumnIndex(MediaStore.Video.Media.MIME_TYPE))
                    )

                    folderToVideoMap[folderPath] = folderToVideoMap[folderPath] ?: mutableListOf()
                    folderToVideoMap[folderPath]!!.apply {
                        add(video)
                        folderToVideoMap[folderPath] = this
                    }

                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "fetchAllVideos: $e")
        } finally {
            DBUtils.closeCursor(videoCursor)
        }

        Log.i(TAG, "fetchVideosByFolder: $folderToVideoMap")

        return folderToVideoMap
    }

}
