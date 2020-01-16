package com.music.video.player.azmediaplayer.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.di.component.FragmentComponent
import com.music.video.player.azmediaplayer.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.tv_message

class HomeFragment : BaseFragment<HomeViewModel>() {

    companion object {

        val TAG = "HomeFragment"

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
            fragmentComponent.inject(this)

    override fun setupView(view: View) {

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.testData.observe(this, Observer {
            tv_message.text = it
        })
    }
}
