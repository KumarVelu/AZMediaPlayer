package com.music.video.player.player_lib.gesture

interface VideoGestureListener {

    fun increaseVolume()

    fun decreaseVolume()

    fun increaseBrightness()

    fun decreaseBrightness()

    fun seekForward()

    fun seekBackward()

    fun onGestureEnd()
}