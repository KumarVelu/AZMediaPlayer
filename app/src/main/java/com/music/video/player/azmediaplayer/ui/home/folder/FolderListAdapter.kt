package com.music.video.player.azmediaplayer.ui.home.folder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.ui.home.video.FolderItemViewHolder
import java.io.File

class FolderListAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var folderPathList = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val layoutInflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FolderItemViewHolder(
            layoutInflater.inflate(R.layout.item_view_folder, parent, false)
        )
    }

    override fun getItemCount() = folderPathList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FolderItemViewHolder).bind(File(folderPathList[position]).name)
    }

}