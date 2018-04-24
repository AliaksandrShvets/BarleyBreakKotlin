package com.example.shvets_as.barleybreakkotlin.managers

import com.example.shvets_as.barleybreakkotlin.callbacks.StopwatchCallback
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

/**
 * Created by Aleksandr Shvets
 * on 16.12.2017.
 */


class StopwatchManager(private val callback: StopwatchCallback) {

    private val ONE_SECOND_IN_MILLIS = 1000

    private lateinit var timer: Timer
    private var isStarted = false
    private var seconds = 0
    private var millis = 0
    private var startMillis = 0

    fun startTimer() {
        isStarted = true
        startMillis = Date().time.toInt() % 1000
        timer = Timer()
        timer.scheduleAtFixedRate((ONE_SECOND_IN_MILLIS - millis).toLong(), ONE_SECOND_IN_MILLIS.toLong(), {
            seconds++
            callback.onSecondUpdate(seconds)
        })
    }

    fun stopTimer() {
        if (isStarted) {
            isStarted = false
            timer.cancel()
            millis = ((Date().time - startMillis + millis) % 1000).toInt()
        }
    }

    fun isStarted(): Boolean {
        return isStarted
    }
}
