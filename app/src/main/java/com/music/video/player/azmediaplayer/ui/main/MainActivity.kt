package com.music.video.player.azmediaplayer.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.di.component.ActivityComponent
import com.music.video.player.azmediaplayer.ui.home.HomeFragment
import com.music.video.player.azmediaplayer.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.tv_message

class MainActivity : BaseActivity<MainViewModel>() {

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        addHomeFragment()
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.testData.observe(this, Observer {
            tv_message.text = it
        })
    }

    private fun addHomeFragment() {
        if (supportFragmentManager.findFragmentByTag(HomeFragment.TAG) == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_fragment, HomeFragment.newInstance(), HomeFragment.TAG)
                .commit()
        }
    }

}
