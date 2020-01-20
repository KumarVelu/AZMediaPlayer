package com.music.video.player.azmediaplayer.ui.music

import android.os.Bundle
import android.view.View
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.di.component.FragmentComponent
import com.music.video.player.azmediaplayer.ui.base.BaseFragment

class MusicListFragment: BaseFragment<MusicListViewModel>() {

    companion object {

        val TAG = "MusicListFragment"

        fun newInstance(): MusicListFragment {
            val args = Bundle()
            val fragment = MusicListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_music_list

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupView(view: View) {

    }
}