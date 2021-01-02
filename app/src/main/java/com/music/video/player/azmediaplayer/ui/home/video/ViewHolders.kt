package com.music.video.player.azmediaplayer.ui.home.video

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import com.music.video.player.azmediaplayer.utils.TimerUtils
import com.music.video.player.azmediaplayer.utils.common.FileUtil
import kotlinx.android.synthetic.main.item_view_date_header.view.*
import kotlinx.android.synthetic.main.item_view_folder.view.*
import kotlinx.android.synthetic.main.item_view_video.view.*
import kotlinx.android.synthetic.main.item_view_video.view.tvFolderName

class VideoItemViewHolder(
    itemView: View,
    iItemClickListener: VideoListAdapter.IItemClickListener
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener{
            iItemClickListener.onItemClick(adapterPosition)
        }

        itemView.imageOverflowMenu.setOnClickListener{
            iItemClickListener.onOverFlowMenuClick(adapterPosition)
        }
    }

    fun bind(video: AdapterItem.Video) {

        Glide
            .with(itemView.context)
            .load(video.path)
            .placeholder(R.mipmap.ic_launcher) // todo : change placeholder image,
            .transform(CenterCrop(), RoundedCorners(12))
            .into(itemView.iv_video_thumbnail)

        itemView.tv_title.text = video.displayName
        itemView.tv_duration.text = TimerUtils.convertMillisToHMmSs(video.durationInMs)
        itemView.tvResolutionFileSize.text = video.fileSize
        itemView.tvFolderName.text = FileUtil.getFolderName(video.path)
    }
}

class VideoSectionItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(dateAdded: String) {
        itemView.tvDateAdded.text = dateAdded
    }
}

class FolderItemViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView){

    fun bind(folderName: String){
        itemView.tvFolderName.text = folderName
    }
}