package com.example.shvets_as.barleybreakkotlin.items

import android.graphics.Typeface
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.example.shvets_as.barleybreakkotlin.callbacks.ItemActionCallback
import com.example.shvets_as.barleybreakkotlin.utils.FIELD_SIZE
import com.example.shvets_as.barleybreakkotlin.utils.FONT_HIND_SILIGURI_LIGHT_TTF
import com.example.shvets_as.barleybreakkotlin.utils.getTranslateAnimation
import kotlinx.android.synthetic.main.item_view.view.*

/**
 * Created by Aleksandr Shvets
 * on 20.10.2017.
 */

class Item(private val view: View, metrics: DisplayMetrics, private val rank: Int, position: Int, private val callback: ItemActionCallback) {

    private var position: Int = 0
    private var xPosition: Int = 0
    private var yPosition: Int = 0
    private val itemSize: Int
    private val fieldLeft: Int
    private val fieldTop: Int

    init {
        val fieldSize = (metrics.widthPixels * FIELD_SIZE).toInt()
        itemSize = fieldSize / rank
        fieldLeft = (metrics.widthPixels - fieldSize) / 2
        fieldTop = (metrics.heightPixels - metrics.widthPixels) / 2
        setPosition(position)
        initView()
    }

    private fun initView() {
        val itemParam = FrameLayout.LayoutParams(itemSize, itemSize)
        view.layoutParams = itemParam
        view.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> callback.onClick(this)
                MotionEvent.ACTION_UP -> view.performClick()
            }
            true
        }
        val itemText = view.iv_text
        itemText.textSize = (itemSize / 5).toFloat()
        itemText.typeface = Typeface.createFromAsset(view.context.assets, FONT_HIND_SILIGURI_LIGHT_TTF)
        itemText.text = (position + 1).toString()
    }

    fun setPosition(position: Int, animateMovement: Boolean = false) {
        this.position = position
        xPosition = position % rank
        yPosition = position / rank
        if (animateMovement) {
            view.startAnimation(
                    getTranslateAnimation(
                            view.x, (xPosition * itemSize + fieldLeft).toFloat(),
                            view.y, (yPosition * itemSize + fieldTop).toFloat()
                    )
            )
        }
        view.x = (xPosition * itemSize + fieldLeft).toFloat()
        view.y = (yPosition * itemSize + fieldTop).toFloat()
    }

    fun getPosition(): Int {
        return position
    }

    fun getXPosition(): Int {
        return xPosition
    }

    fun getYPosition(): Int {
        return yPosition
    }

    fun getView(): View {
        return view
    }
}
