package com.example.shvets_as.barleybreakkotlin.managers

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.shvets_as.barleybreakkotlin.R
import com.example.shvets_as.barleybreakkotlin.callbacks.FieldActionCallback
import com.example.shvets_as.barleybreakkotlin.callbacks.ItemActionCallback
import com.example.shvets_as.barleybreakkotlin.items.Item
import com.example.shvets_as.barleybreakkotlin.utils.getRandomList
import com.example.shvets_as.barleybreakkotlin.utils.getScaleAnimation
import kotlinx.android.synthetic.main.item_view.view.*

/**
 * Created by Shvets_AS on 03.04.2018.
 */

class ItemsManager(activity: Activity, container: FrameLayout, private val rank: Int, val callback: FieldActionCallback) {

    private val items = mutableListOf<Item>()
    private lateinit var lastItem: Item
    private var swapCount = 0

    init {
        initViews(activity, container)
    }

    private fun initViews(activity: Activity, container: FrameLayout) {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        for (i in 0 until rank * rank) {
            lastItem = Item(
                    activity.layoutInflater.inflate(R.layout.item_view, null, false),
                    metrics,
                    rank,
                    i,
                    object : ItemActionCallback {
                        override fun onClick(item: Item) {
                            swap(item)
                        }
                    }
            )
            container.addView(lastItem?.getView())
            items.add(lastItem)
        }
        lastItem?.getView()?.visibility = View.INVISIBLE
        callback.onSwap(0)
        mix()
    }

    private fun mix() {
        val randomList = getRandomList(items.size)
        for ((index, value) in randomList.withIndex()) {
            items[index].setPosition(value)
            if (index < randomList.size - 1) {
                items[index].getView().startAnimation(
                        getScaleAnimation(items[index].getView(), value / rank + value % rank)
                )
            }
        }
    }

    private fun swap(swapItem: Item) {
        if (isItemsNearby(lastItem.getXPosition(), swapItem.getXPosition(), lastItem.getYPosition(), swapItem.getYPosition())) {
            val lastPosition = lastItem.getPosition()
            lastItem.setPosition(swapItem.getPosition(), false)
            swapItem.setPosition(lastPosition, true)
            swapCount++
            callback.onSwap(swapCount)
            checkAllElementsInTheirPositions()
        }
    }

    companion object {
        fun generateStaticItems(inflater: LayoutInflater, view: View, rank: Int, itemSize: Int, fieldLeft: Int, fieldTop: Int): MutableList<View> {
            val views = mutableListOf<View>()
            for (i in 0 until rank) {
                for (j in 0 until rank) {
                    val item = inflater.inflate(R.layout.item_view, null)
                    item.iv_container.setBackgroundResource(R.drawable.item_unnamed)
                    item.layoutParams = FrameLayout.LayoutParams(itemSize, itemSize)
                    item.x = (itemSize * j + fieldLeft).toFloat()
                    item.y = (itemSize * i + fieldTop).toFloat()
                    (view as FrameLayout).addView(item)
                    views.add(item)
                }
            }
            return views
        }
    }

    private fun isItemsNearby(x1: Int, x2: Int, y1: Int, y2: Int): Boolean {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1
    }

    private fun checkAllElementsInTheirPositions() {
        for ((index, value) in items.withIndex()) {
            if (index != value.getPosition()) {
                return
            }
        }
        callback.onWin()
    }
}