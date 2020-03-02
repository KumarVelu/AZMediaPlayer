package com.music.video.player.player_lib

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.music.video.player.player_lib.data.model.VideoMetaData
import com.music.video.player.player_lib.gesture.VideoGestureListener
import com.music.video.player.player_lib.gesture.MyGestureDetector
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A fullscreen activity to play audio or video streams.
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
    private var gestureDetector: MyGestureDetector? = null

    companion object {

        const val EXTRAS_VIDEO_METADATA_LIST = "video_metadata_list"
        const val EXTRAS_PLAY_POS = "play_position"
        const val TAG = "PlayerActivity"

        fun getStartIntent(context: Context, videoMetaDataList: ArrayList<VideoMetaData>, playPos: Int) =
            Intent(context, PlayerActivity::class.java).apply {
                putParcelableArrayListExtra(EXTRAS_VIDEO_METADATA_LIST, videoMetaDataList)
                putExtra(EXTRAS_PLAY_POS, playPos)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        videoMetaDataList = intent.getParcelableArrayListExtra(EXTRAS_VIDEO_METADATA_LIST)
        currentWindow = intent.getIntExtra(EXTRAS_PLAY_POS, 0)

        init()
    }

    private fun init() {

        initViewModel()
        playbackStateListener = PlaybackStateListener()
//        initGestureListener()
        initAudioManager()
    }

    private fun initGestureListener() {
        gestureDetector = MyGestureDetector(video_view, this)
    }

    private fun initViewModel() {
        val viewModelFactory = PlayerViewModelFactory(application, videoMetaDataList)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
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

        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
    }

    private fun initializeListeners() {
        initializeControllerVisibilityListeners()
        player?.addListener(playbackStateListener)
    }

    private fun initializeControllerVisibilityListeners() {
        video_view.setControllerVisibilityListener{visibility ->
            if(visibility == View.VISIBLE){
                showToolbar()
            }else if(visibility == View.GONE){
                hideToolbar()
            }
        }
    }

    private fun showToolbar(){
        layout_toolbar.visibility = View.VISIBLE
    }

    private fun hideToolbar(){
        layout_toolbar.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    private fun initAudioManager(){
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

    private inner class PlaybackStateListener: Player.EventListener{

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

        }

        /* If you only want to detect playlist item changes, then it’s necessary to compare against the last known window index or tag,
        because the onPositionDiscontinuity()  may be triggered for other reasons. */
        override fun onPositionDiscontinuity(reason: Int) {
            player?.let {
                if(currentWindow != it.currentWindowIndex){
                    currentWindow = it.currentWindowIndex
                    setToolbarTitle()
                }
            }
        }

    }

    private fun setToolbarTitle() {
        tv_video_title.text = videoMetaDataList[currentWindow].title
    }

    override fun increaseVolume() {

        if (currentVolume <= maxSystemVolume) {
            ++currentVolume
        }

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)

        pb_volume.progress = currentVolume
        pb_volume.visibility = View.VISIBLE
    }

    override fun decreaseVolume() {
        if (currentVolume >= minSystemVolume) {
            --currentVolume
        }

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)

        pb_volume.progress = currentVolume
        pb_volume.visibility = View.VISIBLE
    }

    override fun increaseBrightness() {

    }

    override fun decreaseBrightness() {

    }

    override fun seekForward() {

    }

    override fun seekBackward() {

    }

}
