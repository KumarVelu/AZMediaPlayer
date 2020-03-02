package com.music.video.player.azmediaplayer.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.di.component.ActivityComponent
import com.music.video.player.azmediaplayer.ui.base.BaseActivity
import com.music.video.player.azmediaplayer.ui.video.VideoListFragment
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity<MainViewModel>(), EasyPermissions.PermissionCallbacks {

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_STORAGE_PERM = 101
    }

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)

    override fun setupView(savedInstanceState: Bundle?) {
        if (hasStoragePermission())
            setUpVideoFragment()
        else
            requestStoragePermission()
    }

    private fun requestStoragePermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.rationale_storage),
            RC_STORAGE_PERM,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun setUpVideoFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment =
            supportFragmentManager.findFragmentByTag(VideoListFragment.TAG) as VideoListFragment?

        if (fragment == null) {
            fragment = VideoListFragment.newInstance()
            fragmentTransaction.add(R.id.fragment_container, fragment, VideoListFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        fragmentTransaction.commit()
    }

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
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // check if storage permission is available and then proceed
            if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                setUpVideoFragment()
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }else{
            finish()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        setUpVideoFragment()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}
