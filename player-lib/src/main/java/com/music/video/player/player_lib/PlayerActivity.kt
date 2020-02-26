package com.music.video.player.player_lib

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.activity_player.*

/**
 * A fullscreen activity to play audio or video streams.
 */
class PlayerActivity : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private lateinit var viewModel: PlayerViewModel

    private lateinit var mediaPathList: ArrayList<String>

    companion object {

        const val EXTRAS_MEDIA_PATH_LIST = "media_path_list"
        const val EXTRAS_PLAY_POS = "play_position"
        const val TAG = "PlayerActivity"

        fun getStartIntent(context: Context, mediaPathList: ArrayList<String>, playPos: Int) =
            Intent(context, PlayerActivity::class.java).apply {
                putStringArrayListExtra(EXTRAS_MEDIA_PATH_LIST, mediaPathList)
                putExtra(EXTRAS_PLAY_POS, playPos)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        mediaPathList = intent.getStringArrayListExtra(EXTRAS_MEDIA_PATH_LIST)
        currentWindow = intent.getIntExtra(EXTRAS_PLAY_POS, 0)

        initViewModel()
    }

    private fun initViewModel() {
        val viewModelFactory = PlayerViewModelFactory(application, mediaPathList)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PlayerViewModel::class.java)

    }

    private fun setUpObservers() {
        viewModel.mediaSource.observe(this, Observer {
            player?.prepare(it, false, false)
        })
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (player == null) {
            initializePlayer()
            setUpObservers()
        }

        initializeListeners()
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        video_view.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this)
        video_view.player = player

        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
    }

    private fun initializeListeners() {
        initializeControllerVisibilityListeners()
    }

    private fun initializeControllerVisibilityListeners() {
        video_view.setControllerVisibilityListener{visibility ->
            if(visibility == View.VISIBLE){
                layout_toolbar.visibility = View.VISIBLE
            }else if(visibility == View.GONE){
                layout_toolbar.visibility = View.GONE
            }
        }
    }

    private fun showToolbar(){

    }

    private fun hideToolbar(){

    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    private fun releasePlayer() {

        player?.let {
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }

    }

}
