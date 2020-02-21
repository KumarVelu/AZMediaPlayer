package com.music.video.player.azmediaplayer.utils

class TimerUtils {

    companion object{

        fun convertMillisToHMmSs(milliseconds: Long): String {
            val seconds = (milliseconds / 1000).toInt() % 60
            val minutes = (milliseconds / (1000 * 60) % 60).toInt()
            val hours = (milliseconds / (1000 * 60 * 60)).toInt()
            return if (hours > 0) {
                String.format("%d:%02d:%02d", hours, minutes, seconds)
            } else {
                if (minutes > 9) {
                    String.format("%02d:%02d", minutes, seconds)
                } else {
                    String.format("%01d:%02d", minutes, seconds)
                }

            }
        }

    }
}