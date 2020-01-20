package com.music.video.player.azmediaplayer.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.di.component.ActivityComponent
import com.music.video.player.azmediaplayer.ui.base.BaseActivity
import com.music.video.player.azmediaplayer.ui.music.MusicListFragment
import com.music.video.player.azmediaplayer.ui.video.VideoListFragment
import kotlinx.android.synthetic.main.activity_main.view_pager
import kotlinx.android.synthetic.main.activity_main.tab_layout

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var tabs: Array<String>

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        tabs = resources.getStringArray(R.array.main_tabs)
        setupViewPager()
    }

    private fun setupViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        view_pager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(view_pager)

    }

    override fun setupObservers() {
        super.setupObservers()

    }

    private inner class ViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {

            return when (tabs[position]) {
                getString(R.string.video) -> VideoListFragment.newInstance()

                getString(R.string.music) -> MusicListFragment.newInstance()

                else -> VideoListFragment.newInstance()
            }
        }

        override fun getCount(): Int = tabs.size

        override fun getPageTitle(position: Int): CharSequence? {

            return when (tabs[position]) {
                getString(R.string.video) -> getString(R.string.video)

                getString(R.string.music) -> getString(R.string.music)

                else -> getString(R.string.video)
            }
        }

    }

}
