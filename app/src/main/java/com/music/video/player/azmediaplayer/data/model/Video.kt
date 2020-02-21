package com.music.video.player.azmediaplayer.data.model

data class Video(
    val videoId: Long,
    val title: String,
    val durationInMs: Long,
    val path: String
)