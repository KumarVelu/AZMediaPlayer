package com.music.video.player.azmediaplayer.ui.video

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import java.lang.IllegalArgumentException

private const val TYPE_ITEM = 0
private const val TYPE_HEADER = 1

class VideoListAdapter(
    private val context: Context,
    private val itemClickListener: IItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var adapterItemList: List<AdapterItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val layoutInflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_ITEM -> VideoItemViewHolder(
                layoutInflater.inflate(
                    R.layout.item_view_video,
                    parent,
                    false
                )
            )

            TYPE_HEADER -> VideoSectionItemViewHolder(
                layoutInflater.inflate(
                    R.layout.item_view_date_header,
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Unknown view type")
        }


    interface IItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int = adapterItemList.size

    override fun getItemViewType(position: Int): Int = when(adapterItemList[position]){
        is AdapterItem.Video -> TYPE_ITEM
        is AdapterItem.SectionItem -> TYPE_HEADER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = adapterItemList[position]){
            is AdapterItem.Video -> (holder as VideoItemViewHolder).bind(item)

            is AdapterItem.SectionItem -> (holder as VideoSectionItemViewHolder).bind(item.sectionName)
        }
    }
}