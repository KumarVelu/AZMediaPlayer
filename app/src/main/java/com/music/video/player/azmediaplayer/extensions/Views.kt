package com.music.video.player.azmediaplayer.extensions

import android.view.View

fun View.setVisible(){
    visibility = View.VISIBLE
}

fun View.setGone(){
    visibility = View.GONE
}

fun View.setVisible(visible: Boolean){
    if(visible) setVisible() else setGone()
}