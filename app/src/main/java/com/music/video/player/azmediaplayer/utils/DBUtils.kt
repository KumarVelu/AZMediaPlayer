package com.music.video.player.azmediaplayer.utils

import android.database.Cursor

class DBUtils {

    companion object{

        fun closeCursor(cursor: Cursor?){
            cursor?.close()
        }
    }
}