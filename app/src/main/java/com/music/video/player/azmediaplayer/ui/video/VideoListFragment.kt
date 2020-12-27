package com.music.video.player.azmediaplayer.ui.video

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import com.music.video.player.azmediaplayer.di.component.FragmentComponent
import com.music.video.player.azmediaplayer.ui.base.BaseFragment
import com.music.video.player.azmediaplayer.ui.delete.DeleteHelper
import com.music.video.player.player_lib.PlayerActivity
import com.music.video.player.player_lib.data.model.VideoMetaData
import kotlinx.android.synthetic.main.fragment_video_list.*
import javax.inject.Inject

class VideoListFragment : BaseFragment<VideoListViewModel>(), VideoListAdapter.IItemClickListener {

    companion object {

        const val TAG = "VideoListFragment"

        fun newInstance(): VideoListFragment {
            val args = Bundle()
            val fragment = VideoListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var videoListAdapter: VideoListAdapter

    private var adapterItemList = mutableListOf<AdapterItem>()
    private var mOverflowItemClickPos = -1

    override fun provideLayoutId(): Int = R.layout.fragment_video_list

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupView(view: View) {
        toolbar_title.text = getString(R.string.app_name)

        rv_videos.apply {
            layoutManager = linearLayoutManager
            adapter = videoListAdapter
        }
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.loading.observe(this, Observer {
            pb_loading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.adapterItemListLiveData.observe(this, Observer {
            Log.i(TAG, "setupObservers: $it")
            it.data?.run {
                adapterItemList = this
                videoListAdapter.adapterItemList = this@VideoListFragment.adapterItemList
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    override fun onItemClick(position: Int) {
        val videoMetaDataList = arrayListOf<VideoMetaData>()

        var headerItemsToIgnore = 0

        adapterItemList.forEach {
            if (it is AdapterItem.Video) {
                videoMetaDataList.add(VideoMetaData(it.displayName, it.path))
            } else if ((headerItemsToIgnore + videoMetaDataList.size < position) && it is AdapterItem.SectionItem) headerItemsToIgnore++
        }
        startActivity(
            PlayerActivity.getStartIntent(
                context!!,
                videoMetaDataList,
                (position - headerItemsToIgnore)
            )
        )
    }

    override fun onOverFlowMenuClick(position: Int) {
        mOverflowItemClickPos = position
        val video = adapterItemList[position] as AdapterItem.Video
        val videoOverflowOptionsFragment = VideoOverflowOptionsFragment.newInstance(video)
        videoOverflowOptionsFragment.showNow(childFragmentManager, VideoOverflowOptionsFragment.TAG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == DeleteHelper.REQUEST_PERM_DELETE && resultCode == Activity.RESULT_OK){
            if(mOverflowItemClickPos != -1){
                adapterItemList.removeAt(mOverflowItemClickPos)
                videoListAdapter.notifyItemRemoved(mOverflowItemClickPos)
                mOverflowItemClickPos = -1
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}