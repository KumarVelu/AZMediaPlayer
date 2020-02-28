package com.music.video.player.player_lib.utils.display

import android.content.res.Resources

object ScreenUtils {

    fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

    fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

    fun leftHalf() = getScreenWidth()/2

    fun rightHalf() = getScreenWidth()/2
}