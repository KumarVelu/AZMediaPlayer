package com.music.video.player.player_lib.gesture

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.music.video.player.player_lib.utils.display.ScreenUtils
import com.music.video.player.player_lib.utils.gesture.GestureMove
import com.music.video.player.player_lib.utils.gesture.GestureType
import kotlin.math.abs

class MyGestureDetector(private val view: View, private val videoGestureListener: VideoGestureListener) {

    private var currentX = 0
    private var currentY = 0
    private var activeGesture: GestureType = GestureType.NONE

    companion object {
        private const val TAG = "MyGestureDetector"
        private const val GESTURE_DISPLACEMENT = 10
    }

    private var gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(view.context, GestureListener())
        setOnTouchListener()
    }

    private fun setOnTouchListener() {
        view.setOnTouchListener { v: View?, event: MotionEvent? ->

            event?.let {

                if (event.action == MotionEvent.ACTION_UP) {
                    // gesture stopped.
                    resetAllGestureValues()
                }else{
                    gestureDetector.onTouchEvent(event)
                }
            }

            false
        }
    }

    private fun resetAllGestureValues() {
        activeGesture = GestureType.NONE
        currentX = 0
        currentY = 0
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float,
            distanceY: Float): Boolean {

            if (e1 != null && e2 != null) {

                if (activeGesture == GestureType.NONE) {

                    if (currentX == 0 && currentY == 0) {
                        currentX = e1.x.toInt()
                        currentY = e1.y.toInt()
                    }

                    val verticalGestureMove = checkAndGetVerticalGestureMove(e2.y.toInt())

                    if (verticalGestureMove != GestureMove.NONE) {

                        if (e1.x.toInt() < ScreenUtils.leftHalf()) {
                            activeGesture = GestureType.VOLUME
                        } else if (e1.x.toInt() > ScreenUtils.rightHalf()) {
                            activeGesture = GestureType.BRIGHTNESS
                        }

                    } else {

                        val horizontalGestureMove = checkAndGetHorizontalGestureMove(e2.x.toInt())

                        if (horizontalGestureMove != GestureMove.NONE) {
                            activeGesture = GestureType.SEEK
                        }
                    }

                } else { // there is active gesture in progress

                    if (activeGesture == GestureType.VOLUME || activeGesture == GestureType.BRIGHTNESS) {

                        val gestureMove = checkAndGetVerticalGestureMove(e2.y.toInt())

                        if (gestureMove == GestureMove.POSITIVE && activeGesture == GestureType.VOLUME) {
                            Log.d(TAG, "onScroll: Increase volume")
                            videoGestureListener.increaseVolume()
                        } else if (gestureMove == GestureMove.NEGATIVE && activeGesture == GestureType.VOLUME) {
                            Log.d(TAG, "onScroll: Decrease volume")
                            videoGestureListener.decreaseVolume()
                        }

                        if (gestureMove == GestureMove.POSITIVE && activeGesture == GestureType.BRIGHTNESS) {
                            Log.d(TAG, "onScroll: Increase brightness")
                            videoGestureListener.increaseBrightness()
                        } else if (gestureMove == GestureMove.NEGATIVE && activeGesture == GestureType.BRIGHTNESS) {
                            Log.d(TAG, "onScroll: Decrease brightness")
                            videoGestureListener.decreaseBrightness()
                        }
                    } else if (activeGesture == GestureType.SEEK) {

                        val gestureMove = checkAndGetHorizontalGestureMove(e2.x.toInt())

                        if (gestureMove == GestureMove.POSITIVE) {
                            Log.d(TAG, "onScroll: Increase seek")
                            videoGestureListener.seekForward()
                        } else if (gestureMove == GestureMove.NEGATIVE) {
                            Log.d(TAG, "onScroll: Decrease seek")
                            videoGestureListener.seekBackward()
                        }
                    }

                }
            }

            return false
        }

        private fun checkAndGetVerticalGestureMove(yPos: Int): GestureMove {

            val displacement = yPos - currentY
            if (abs((displacement / GESTURE_DISPLACEMENT)) > 1) {

                currentY = yPos

                return if (displacement > 0)
                    GestureMove.NEGATIVE
                else
                    GestureMove.POSITIVE
            }

            return GestureMove.NONE
        }

        private fun checkAndGetHorizontalGestureMove(xPos: Int): GestureMove {

            val displacement = xPos - currentX
            if (abs((displacement / GESTURE_DISPLACEMENT)) > 1) {

                Log.i(TAG, "checkAndGetHorizontalGestureMove: currentX : $currentX :: xPos : $xPos")

                currentX = xPos

                return if (displacement > 0)
                    GestureMove.POSITIVE
                else
                    GestureMove.NEGATIVE
            }

            return GestureMove.NONE
        }

    }

}