package com.music.video.player.azmediaplayer.ui.video

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.music.video.player.azmediaplayer.data.model.Video
import com.music.video.player.azmediaplayer.ui.base.BaseAdapter

class VideosAdapter(parentLifecycle: Lifecycle,
                    videos: ArrayList<Video>,
                    private val itemClickListener: IItemClickListener
): BaseAdapter<Video, VideoItemViewHolder>(parentLifecycle, videos) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VideoItemViewHolder(parent, itemClickListener)


    interface IItemClickListener{
        fun onItemClick(positon: Int)
    }
}