package com.music.video.player.azmediaplayer.utils

import java.text.DateFormatSymbols
import java.util.*

object TimerUtils {

    private const val TAG = "TimerUtils"

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

    fun getDateStringForVideoList(dateAddedInMillis: Long) : String{

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateAddedInMillis * 1000

        val date = if(calendar.get(Calendar.DAY_OF_MONTH) < 10){
            "0${calendar.get(Calendar.DAY_OF_MONTH)}"
        }else{
            "${calendar.get(Calendar.DAY_OF_MONTH)}"
        }

        val month = DateFormatSymbols().shortMonths[calendar.get(Calendar.MONTH)]

        return if(calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)){
            "$date $month"
        }else{
            "$date $month ${calendar.get(Calendar.YEAR)}"
        }
    }

}