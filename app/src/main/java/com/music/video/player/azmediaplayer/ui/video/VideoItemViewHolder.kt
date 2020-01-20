package com.music.video.player.azmediaplayer.ui.video

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.data.model.Video
import com.music.video.player.azmediaplayer.di.component.ViewHolderComponent
import com.music.video.player.azmediaplayer.ui.base.BaseItemViewHolder
import kotlinx.android.synthetic.main.item_view_video.view.*

class VideoItemViewHolder(parent: ViewGroup):
    BaseItemViewHolder<Video, VideoItemViewModel>(R .layout.item_view_video, parent){

    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.title.observe(this, Observer {
            itemView.tv_title.text = it
        })

        viewModel.duration.observe(this, Observer {
            itemView.tv_duration.text = it.toString()
        })
    }

    override fun setupView(view: View) {

    }


}