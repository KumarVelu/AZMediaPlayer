package com.music.video.player.azmediaplayer.ui.video

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.data.model.Video
import com.music.video.player.azmediaplayer.di.component.ViewHolderComponent
import com.music.video.player.azmediaplayer.ui.base.BaseItemViewHolder
import com.music.video.player.azmediaplayer.utils.TimerUtils
import kotlinx.android.synthetic.main.item_view_video.view.*

class VideoItemViewHolder(
    private val parent: ViewGroup,
    private val itemClickListener: VideoListAdapter.IItemClickListener
) : BaseItemViewHolder<Video, VideoItemViewModel>(R.layout.item_view_video, parent) {

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.title.observe(this, Observer {
            itemView.tv_title.text = it
        })

        viewModel.duration.observe(this, Observer {
            itemView.tv_duration.text = TimerUtils.convertMillisToHMmSs(it)
        })

        viewModel.path.observe(this, Observer {
            Glide
                .with(parent.context)
                .load(it)
                .placeholder(R.mipmap.ic_launcher)
                .transform( CenterCrop(), RoundedCorners(12))
                .into(itemView.iv_video_thumbnail)
        })
    }

    override fun setupView(view: View) {
        itemView.setOnClickListener {
            itemClickListener.onItemClick(adapterPosition)
        }
    }


}