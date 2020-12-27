package com.music.video.player.azmediaplayer.ui.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.music.video.player.azmediaplayer.R
import com.music.video.player.azmediaplayer.data.model.AdapterItem
import com.music.video.player.azmediaplayer.ui.delete.DeleteHelper
import kotlinx.android.synthetic.main.fragment_video_overflow_options.*

class VideoOverflowOptionsFragment: BottomSheetDialogFragment(){

    private lateinit var mVideo: AdapterItem.Video

    private val optionsClickListener by lazy {
        View.OnClickListener {
            when(it.id){
                R.id.deleteOptionContainer -> deleteVideo()
                R.id.shareOptionContainer -> shareVideo()
                R.id.fileInfoOptionContainer -> showFileInfo()
            }
        }
    }

    private fun showFileInfo() {
        TODO("Not yet implemented")
    }

    private fun shareVideo() {
        TODO("Not yet implemented")
    }

    private fun deleteVideo() {
        DeleteHelper.deleteVideos(requireActivity(), listOf(mVideo))
        dismissAllowingStateLoss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_overflow_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mVideo = arguments?.getParcelable(ARGS_VIDEO)!!

        deleteOptionContainer.setOnClickListener(optionsClickListener)
        shareOptionContainer.setOnClickListener(optionsClickListener)
        fileInfoOptionContainer.setOnClickListener(optionsClickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object{
        const val TAG = "VideoOverflowOptionsFra"
        private const val ARGS_VIDEO = "video"

        fun newInstance(video: AdapterItem.Video): VideoOverflowOptionsFragment{
            return VideoOverflowOptionsFragment().apply {
                val args = Bundle()
                args.putParcelable(ARGS_VIDEO, video)
                arguments = args
            }
        }
    }

}