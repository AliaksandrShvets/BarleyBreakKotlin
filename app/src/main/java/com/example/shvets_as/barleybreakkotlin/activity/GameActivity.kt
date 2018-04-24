package com.example.shvets_as.barleybreakkotlin.activity

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import com.example.shvets_as.barleybreakkotlin.R
import com.example.shvets_as.barleybreakkotlin.callbacks.FieldActionCallback
import com.example.shvets_as.barleybreakkotlin.callbacks.StopwatchCallback
import com.example.shvets_as.barleybreakkotlin.fragments.B_RANK
import com.example.shvets_as.barleybreakkotlin.managers.ItemsManager
import com.example.shvets_as.barleybreakkotlin.managers.StopwatchManager
import com.example.shvets_as.barleybreakkotlin.utils.FIELD_SIZE
import com.example.shvets_as.barleybreakkotlin.utils.FONT_HIND_SILIGURI_LIGHT_TTF
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

/**
 * Created by Shvets_AS on 03.04.2018.
 */

class GameActivity : Activity() {

    private var rank: Int = 0
    private var itemsManager: ItemsManager? = null
    private var stopwatchManager: StopwatchManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initViews()
        initField()
        initBgStaticField()
        initTimer()
    }

    private fun initViews() {
        rank = intent.getIntExtra(B_RANK, 3)
        ag_steps.typeface = Typeface.createFromAsset(assets, FONT_HIND_SILIGURI_LIGHT_TTF)
    }

    private fun initField() {
        itemsManager = ItemsManager(this, ag_field, rank, object : FieldActionCallback {

            override fun onWin() {
                Toast.makeText(baseContext, "WIN", Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onSwap(count: Int) {
                stopwatchManager?.let {
                    if (!it.isStarted()) it.startTimer()
                }
                ag_steps.text = count.toString()
            }
        })
    }

    private fun initBgStaticField() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val fieldSize = (metrics.widthPixels * FIELD_SIZE).toInt()
        val itemSize = fieldSize / rank
        val fieldLeft = (metrics.widthPixels - fieldSize) / 2
        val fieldTop = (metrics.heightPixels - metrics.widthPixels) / 2
        ItemsManager.generateStaticItems(layoutInflater, findViewById(R.id.ag_field_static), rank, itemSize, fieldLeft, fieldTop)
    }

    private fun initTimer() {
        ag_timer.typeface = Typeface.createFromAsset(assets, FONT_HIND_SILIGURI_LIGHT_TTF)
        updateTime(0)
        stopwatchManager = StopwatchManager(object : StopwatchCallback {
            override fun onSecondUpdate(seconds: Int) {
                updateTime(seconds)
            }
        })
        ag_timer.setOnClickListener {
            stopwatchManager?.let {
                if (it.isStarted()) {
                    it.stopTimer()
                } else {
                    it.startTimer()
                }
            }
        }
    }

    private fun updateTime(seconds: Int) {
        runOnUiThread { ag_timer.text = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60) }
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(0, 0)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
        stopwatchManager!!.stopTimer()
    }
}