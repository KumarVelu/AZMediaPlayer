package com.music.video.player.azmediaplayer.data.model

sealed class AdapterItem{

    data class Video(
        val videoId: Long,
        val displayName: String,
        val durationInMs: Long,
        val path: String,
        val dateAdded: Long,
        val fileSize: String
    ) : AdapterItem()

    data class SectionItem(val sectionName: String) : AdapterItem()
}