package com.music.video.player.azmediaplayer.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.di.component.ActivityComponent
import com.music.video.player.azmediaplayer.ui.base.BaseActivity
import com.music.video.player.azmediaplayer.ui.delete.DeleteHelper
import com.music.video.player.azmediaplayer.ui.home.video.VideoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>(), EasyPermissions.PermissionCallbacks {

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_STORAGE_PERM = 101
    }

    @Inject
    lateinit var mainActivityViewPagerAdapter: MainActivityViewPagerAdapter

    private var mVideoListFragment: VideoListFragment? = null

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        toolbar_title.text = getString(R.string.app_name)

        if (hasStoragePermission())
            setUpViewPager()
        else
            requestStoragePermission()
    }

    private fun setUpViewPager() {
        viewPager.apply {
            adapter = mainActivityViewPagerAdapter
        }
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = MainActivityViewPagerAdapter.TAB_LIST[position]
        }.attach()
    }

    private fun requestStoragePermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale_storage),
            RC_STORAGE_PERM,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    /*private fun setUpVideoFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        mVideoListFragment =
            supportFragmentManager.findFragmentByTag(VideoListFragment.TAG) as VideoListFragment?

        if (mVideoListFragment == null) {
            mVideoListFragment = VideoListFragment.newInstance()
            fragmentTransaction.add(R.id.fragment_container, mVideoListFragment!!, VideoListFragment.TAG)
        } else {
            fragmentTransaction.show(mVideoListFragment!!)
        }

        fragmentTransaction.commit()
    }*/

    override fun setupObservers() {
        super.setupObservers()

    }

    private fun hasStoragePermission() =
        EasyPermissions.hasPermissions(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    @SuppressLint("StringFormatMatches")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // check if storage permission is available and then proceed
            if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                setUpViewPager()
            }
        }else if(requestCode == DeleteHelper.REQUEST_PERM_DELETE && resultCode == Activity.RESULT_OK){
            mVideoListFragment?.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }else{
            finish()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        setUpViewPager()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}
