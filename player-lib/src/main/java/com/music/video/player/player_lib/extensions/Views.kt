package com.music.video.player.player_lib.extensions

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