package com.example.shvets_as.barleybreakkotlin

import android.app.Application

/**
 * Created by Shvets_AS on 04.04.2018.
 */

class BarleyBreakApp : Application() {

    companion object {
        lateinit var instance: BarleyBreakApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}