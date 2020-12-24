package com.music.video.player.azmediaplayer.utils.common

import java.text.DecimalFormat
import kotlin.math.log10

object FileUtil {

    fun getReadableFileSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups =
            (Math.log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(
            size / Math.pow(
                1024.0,
                digitGroups.toDouble()
            )
        ).toString() + " " + units[digitGroups]
    }

    fun getFolderName(filePath: String) =
        when{
            filePath.contains("whatsapp", ignoreCase = true) -> "WhatsApp"

            filePath.contains("Camera") || filePath.contains("DCIM") -> "Camera"

            filePath.contains("download", ignoreCase = true) -> "Download"

            filePath.contains("Movies", ignoreCase = true) -> "Movies"

            else -> ""
        }

}