package com.music.video.player.player_lib

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.TimeBar
import com.music.video.player.player_lib.data.model.VideoMetaData
import com.music.video.player.player_lib.extensions.setGone
import com.music.video.player.player_lib.extensions.setVisible
import com.music.video.player.player_lib.gesture.MyGestureDetector
import com.music.video.player.player_lib.gesture.VideoGestureListener
import com.music.video.player.player_lib.utils.constants.Constants
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.custom_playback_control.view.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A fullscreen activity to play video streams.
 */
class PlayerActivity : AppCompatActivity(), VideoGestureListener {

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = -1
    private var playbackPosition = 0L
    private lateinit var viewModel: PlayerViewModel

    private lateinit var videoMetaDataList: ArrayList<VideoMetaData>
    private lateinit var playbackStateListener: PlaybackStateListener
    private lateinit var audioManager: AudioManager
    private var maxSystemVolume = -1
    private var minSystemVolume = -1
    private var currentVolume = -1
    private var currentBrightness = 0f
    private var gestureDetector: MyGestureDetector? = null
    private lateinit var layoutParams: WindowManager.LayoutParams

    companion object {

        const val EXTRAS_VIDEO_METADATA_LIST = "video_metadata_list"
        const val EXTRAS_PLAY_POS = "play_position"
        const val EXTRAS_PLAY_BACK_POS = "play_back_pos"
        const val EXTRAS_PLAY_WHEN_READY = "play_when_ready"
        const val TAG = "PlayerActivity"

        fun getStartIntent(
            context: Context,
            videoMetaDataList: ArrayList<VideoMetaData>,
            playPos: Int
        ) =
            Intent(context, PlayerActivity::class.java).apply {
                putParcelableArrayListExtra(EXTRAS_VIDEO_METADATA_LIST, videoMetaDataList)
                putExtra(EXTRAS_PLAY_POS, playPos)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        videoMetaDataList = intent.getParcelableArrayListExtra(EXTRAS_VIDEO_METADATA_LIST)!!
        currentWindow = intent.getIntExtra(EXTRAS_PLAY_POS, 0)

        init()
    }

    private fun init() {
        initViewModel()
        playbackStateListener = PlaybackStateListener()
        initGestureListener()
        initAudioManager()
        initWindowAttrForScreenBrightness()
        initViews()
    }

    private fun initViews() {
        iv_up_button.setOnClickListener { finish() }
        ivScreenRotate.setOnClickListener { changeOrientation() }
        toggleBtnVolumeOff.setOnCheckedChangeListener{ view, isChecked ->
            if(isChecked) turnVolumeOff()
            else turnVolumeOn()
        }
    }

    private fun turnVolumeOn() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
    }

    private fun turnVolumeOff() {
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
    }

    private fun changeOrientation() {
        val orientation = resources.configuration.orientation
        requestedOrientation = if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }else{
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    private fun initGestureListener() {
        gestureDetector = MyGestureDetector(video_view, this)
    }

    private fun initViewModel() {
        val viewModelFactory = PlayerViewModelFactory(application, videoMetaDataList)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PlayerViewModel::class.java)
    }

    private fun setUpObservers() {
        viewModel.mediaSource.observe(this, Observer {
            player?.prepare(it, false, false)
            setToolbarTitle()
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
        player = SimpleExoPlayer.Builder(this).build()
        video_view.player = player

        val timeBar = video_view.findViewById<DefaultTimeBar>(R.id.exo_progress)
        timeBar.addListener(object : TimeBar.OnScrubListener {
            override fun onScrubMove(timeBar: TimeBar, position: Long) {
                player?.seekTo(position)
            }

            override fun onScrubStart(timeBar: TimeBar, position: Long) {}
            override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {}

        })

        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
    }

    private fun initializeListeners() {
        initializeControllerVisibilityListeners()
        player?.addListener(playbackStateListener)
    }

    private fun initializeControllerVisibilityListeners() {
        video_view.setControllerVisibilityListener { visibility ->
            if (visibility == View.VISIBLE) {
                showControlViews()
            } else if (visibility == View.GONE) {
                hideControlViews()
            }
        }
    }

    private fun showControlViews(){
        layout_toolbar.setVisible()
        toggleBtnVolumeOff.setVisible()
        ivScreenRotate.setVisible()
    }

    private fun hideControlViews(){
        layout_toolbar.setGone()
        toggleBtnVolumeOff.setGone()
        ivScreenRotate.setGone()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    private fun initAudioManager() {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        maxSystemVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        minSystemVolume = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC)
        } else {
            0
        }

        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        pb_volume.max = maxSystemVolume
        pb_volume.progress = currentVolume
    }

    private fun initWindowAttrForScreenBrightness() {
        layoutParams = window.attributes
        // todo : have to change this logic. Have to persist screen brightness value and use that.
        currentBrightness =
            if (window.attributes.screenBrightness == -1f) 0.5f else window.attributes.screenBrightness
        pb_brightness.progress = (currentBrightness * 100).toInt()
    }

    private fun releasePlayer() {
        player?.let {
            it.removeListener(playbackStateListener)
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gestureDetector = null
    }

    private inner class PlaybackStateListener : Player.EventListener {

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {}

        /* If you only want to detect playlist item changes, then it’s necessary to compare against the last known window index or tag,
        because the onPositionDiscontinuity()  may be triggered for other reasons. */
        override fun onPositionDiscontinuity(reason: Int) {
            player?.let {
                if (currentWindow != it.currentWindowIndex) {
                    currentWindow = it.currentWindowIndex
                    setToolbarTitle()
                    video_view.setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
                }
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {}
    }

    private fun setToolbarTitle() {
        tv_video_title.text = videoMetaDataList[currentWindow].title
    }

    override fun increaseVolume() {
        if (currentVolume <= maxSystemVolume) {
            ++currentVolume
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
        }

        pb_volume.setVisible()
        pb_volume.progress = currentVolume
    }

    override fun decreaseVolume() {
        if (currentVolume >= minSystemVolume) {
            --currentVolume
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
        }

        pb_volume.setVisible()
        pb_volume.progress = currentVolume
    }

    override fun increaseBrightness() {

        if (currentBrightness < 1.0f) {

            currentBrightness += 0.05f
            if (currentBrightness > 1.0f) currentBrightness = 1.0f

            layoutParams.screenBrightness = currentBrightness
            window.attributes = layoutParams
            pb_brightness.progress = (currentBrightness * 100).toInt()
        }

        pb_brightness.setVisible()
    }

    override fun decreaseBrightness() {

        if (currentBrightness > 0f) {

            currentBrightness -= 0.05f
            if (currentBrightness < 0f) currentBrightness = 0f

            layoutParams.screenBrightness = currentBrightness
            window.attributes = layoutParams
            pb_brightness.progress = (currentBrightness * 100).toInt()
        }

        pb_brightness.setVisible()

    }

    override fun seekForward() {
        player?.let {
            if (it.currentPosition < it.duration) {
                it.seekTo(it.currentPosition + Constants.DEFAULT_SEEK_TIME)
            }
        }
    }

    override fun seekBackward() {
        player?.let {
            if (it.currentPosition > 0) {
                it.seekTo(it.currentPosition - Constants.DEFAULT_SEEK_TIME)
            }
        }
    }

    override fun onGestureEnd() {
        pb_brightness.setGone()
        pb_volume.setGone()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putLong(EXTRAS_PLAY_BACK_POS, playbackPosition)
            putBoolean(EXTRAS_PLAY_WHEN_READY, playWhenReady)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        with(savedInstanceState){
            playbackPosition = getLong(EXTRAS_PLAY_BACK_POS)
            playWhenReady = getBoolean(EXTRAS_PLAY_WHEN_READY)
        }
    }

}
