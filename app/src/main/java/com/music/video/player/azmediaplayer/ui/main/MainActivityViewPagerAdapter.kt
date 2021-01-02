package com.music.video.player.azmediaplayer.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.music.video.player.azmediaplayer.ui.video.VideoListFragment
import javax.inject.Inject


class MainActivityViewPagerAdapter @Inject constructor(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {

    companion object{
        val TAB_LIST = mutableListOf("Videos", "Folders")
    }

    override fun getItemCount() = TAB_LIST.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> VideoListFragment.newInstance()
            1 -> VideoListFragment.newInstance()
            else -> throw IllegalArgumentException("Unknown tab position")
        }
    }

}