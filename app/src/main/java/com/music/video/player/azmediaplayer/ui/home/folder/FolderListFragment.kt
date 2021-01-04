package com.music.video.player.azmediaplayer.ui.home.folder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.di.component.FragmentComponent
import com.music.video.player.azmediaplayer.extensions.setVisible
import com.music.video.player.azmediaplayer.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_folder_list.*
import kotlinx.android.synthetic.main.fragment_video_list.*
import javax.inject.Inject

class FolderListFragment : BaseFragment<FolderListViewModel>() {

    companion object{

        private const val TAG = "FolderListFragment"

        fun newInstance(): FolderListFragment{
            val args = Bundle().apply {}
            return FolderListFragment().apply {
                arguments = args
            }
        }
    }

    @Inject
    lateinit var folderListAdapter: FolderListAdapter

    override fun provideLayoutId() = R.layout.fragment_folder_list

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {
        rvFolder.adapter = folderListAdapter
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.loading.observe(this, Observer {
            pbLoading.setVisible(visible = it)
        })

        viewModel.folderToVideoMapLiveData.observe(this, Observer {
            Log.i(TAG, "setupObservers: ${it.keys.toMutableList()}")
            folderListAdapter.folderPathList = it.keys.toMutableList()
        })

    }

}