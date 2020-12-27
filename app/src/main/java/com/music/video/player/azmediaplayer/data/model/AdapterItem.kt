package com.music.video.player.azmediaplayer.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class AdapterItem{

    @Parcelize
    data class Video(
        val videoId: Long,
        val displayName: String,
        val durationInMs: Long,
        val path: String,
        val dateAdded: Long,
        val fileSize: String
    ) : AdapterItem(), Parcelable

    data class SectionItem(val sectionName: String) : AdapterItem()
}