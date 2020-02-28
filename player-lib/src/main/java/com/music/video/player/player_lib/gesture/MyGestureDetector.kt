package com.music.video.player.player_lib.gesture

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.music.video.player.player_lib.utils.display.ScreenUtils
import kotlin.math.abs

class MyGestureDetector(private val view: View) {

    private var currentX = 0
    private var currentY = 0
    private var activeGesture = ""

    companion object {
        private const val TAG = "MyGestureDetector"
        private const val GESTURE_ONE_UNIT = 20
    }

    private var gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(view.context, GestureListener())
        setOnTouchListener()
    }

    private fun setOnTouchListener(){
        view.setOnTouchListener{v: View?, event: MotionEvent? ->

            event?.let {

                if (event.action == MotionEvent.ACTION_UP) {
                    // gesture stopped.
                    resetAllGestureValues()
                }

                gestureDetector.onTouchEvent(event)

            }

            false
        }
    }

    private fun resetAllGestureValues(){
        activeGesture = ""
        currentX = 0
        currentY = 0
    }

    private inner class GestureListener: GestureDetector.SimpleOnGestureListener(){


        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float,
                              distanceY: Float): Boolean {


//            Log.i(TAG, "onScroll: e1.x = ${e1?.x}, e1.y = ${e1?.y}, e2.x = ${e2?.x}, e2.y = ${e2?.y}")

            if(e1 != null && e2 != null){

                if(activeGesture.isEmpty()){

                    if(currentX == 0 && currentY == 0){
                        currentX = e2.x.toInt()
                        currentY = e2.y.toInt()
                    }

                    if(isGestureMoveVertical(e2.y.toInt())){
                        if(e1.x.toInt() < ScreenUtils.leftHalf()){
                            activeGesture = "volume"
                        }else if(e1.x.toInt() > ScreenUtils.rightHalf()){
                            activeGesture = "brightness"
                        }
                    } else if(isGestureMoveHorizontal(e2.x.toInt())){
                        activeGesture = "seek"
                    }
                } else{ // there is active gesture in progress

                    if(activeGesture == "volume" && isGestureMoveVertical(e2.y.toInt())){
                        Log.d(TAG, "onScroll: increase volume")
                    }else if(activeGesture == "brightness" && isGestureMoveVertical(e2.y.toInt())){
                        Log.d(TAG, "onScroll: increase brightness")
                    }else if(activeGesture == "seek" && isGestureMoveHorizontal(e2.x.toInt())){
                        Log.d(TAG, "onScroll: increase seek position")
                    }

                }
            }

            return false
        }

        private fun isGestureMoveVertical(yPos: Int): Boolean{

            if((abs((yPos - currentY)) / GESTURE_ONE_UNIT) > 1){
                currentY = yPos
                return true
            }
            return false
        }

        private fun isGestureMoveHorizontal(xPos: Int): Boolean{

            if((abs((xPos - currentX)) / GESTURE_ONE_UNIT) > 1){
                currentX = xPos
                return true
            }
            return false
        }
    }

}