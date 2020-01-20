package com.music.video.player.azmediaplayer.ui.video

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.di.component.FragmentComponent
import com.music.video.player.azmediaplayer.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_video_list.*
import javax.inject.Inject

class VideoListFragment : BaseFragment<VideoListViewModel>(){

    companion object {

        val TAG = "VideoListFragment"

        fun newInstance(): VideoListFragment {
            val args = Bundle()
            val fragment = VideoListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var linearLayouManager: LinearLayoutManager

    @Inject
    lateinit var videosAdapter: VideosAdapter

    override fun provideLayoutId(): Int = R.layout.fragment_video_list

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupView(view: View) {
        rv_videos.apply {
            layoutManager = linearLayouManager
            adapter = videosAdapter
        }
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.loading.observe(this, Observer {
            pb_loading.visibility = if(it) View.VISIBLE else View.GONE
        })

        viewModel.videoList.observe(this, Observer {
            Log.i(TAG, "setupObservers: $it")
            it.data?.run { videosAdapter.appendData(this) }
        })
    }
}