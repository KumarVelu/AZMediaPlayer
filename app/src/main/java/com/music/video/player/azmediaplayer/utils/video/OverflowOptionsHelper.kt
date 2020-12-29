package com.music.video.player.azmediaplayer.utils.video

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.system.Os.close
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import com.music.video.player.azmediaplayer.utils.TimerUtils
import kotlinx.android.synthetic.main.item_view_video.view.*
import kotlinx.android.synthetic.main.layout_file_info.view.*
import java.io.File
import java.util.concurrent.TimeUnit

object OverflowOptionsHelper {

    fun getShareIntent(videoId: Long) = Intent().apply {
        action = Intent.ACTION_SEND
        type =  "video/*"
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        val uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoId.toString())
        putExtra(Intent.EXTRA_STREAM, uri)
    }

    fun showFileInfoDialog(context: Context, video: AdapterItem.Video){

        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_file_info, null)

        dialogView.tvTitle.text = video.displayName
        dialogView.tvResolution.text = video.resolution
        dialogView.tvSize.text = video.fileSize
        dialogView.tvFormat.text = video.mimeType
        dialogView.tvPath.text = File(video.path).parent
        dialogView.tvDateCreated.text = TimerUtils.getFormattedDateTime(video.dateAdded)

        MaterialAlertDialogBuilder(context)
            .setView(dialogView)
            .setTitle(context.getString(R.string.information))
            .setNegativeButton(R.string.close, null)
            .setCancelable(true)
            .create()
            .show()

    }

}