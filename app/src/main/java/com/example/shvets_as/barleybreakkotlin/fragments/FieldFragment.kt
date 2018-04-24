package com.example.shvets_as.barleybreakkotlin.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shvets_as.barleybreakkotlin.R
import com.example.shvets_as.barleybreakkotlin.managers.ItemsManager.Companion.generateStaticItems
import com.example.shvets_as.barleybreakkotlin.utils.FIELD_SIZE

/**
 * Created by Shvets_AS on 03.04.2018.
 */

const val B_PAGE_NUMBER = "bRank"
const val B_RANK = "bRank"

class FieldFragment : Fragment() {

    private lateinit var metrics: DisplayMetrics
    private var views = mutableListOf<View>()
    private var rank: Int = 0
    private var fieldLeft: Int = 0
    private var fieldTop: Int = 0
    private var itemSize: Int = 0

    companion object {
        fun newInstance(rank: Int): FieldFragment {
            val pageFragment = FieldFragment()
            val arguments = Bundle()
            arguments.putInt(B_PAGE_NUMBER, rank)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rank = arguments.getInt(B_PAGE_NUMBER)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initFieldMetrics()
        return getField(inflater)
    }

    private fun initFieldMetrics() {
        metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val fieldSize = (metrics.widthPixels * FIELD_SIZE).toInt()
        itemSize = fieldSize / rank
        fieldLeft = (metrics.widthPixels - fieldSize) / 2
        fieldTop = (metrics.heightPixels - metrics.widthPixels) / 2
    }

    private fun getField(inflater: LayoutInflater): View {
        val view = inflater.inflate(R.layout.fragment_field_page, null)
        views = generateStaticItems(inflater, view, rank, itemSize, fieldLeft, fieldTop)
        return view
    }

    fun animateOffsetX(x: Int, isLeft: Boolean) {
        for (i in 0 until rank) {
            for (j in 0 until rank) {
                val offset = if (isLeft) {
                    x * (1 - (i + j) / (rank * 2f))
                } else {
                    (x - metrics.widthPixels) * ((i + j) / (rank * 2f))
                }
                views[i * rank + j].x = itemSize * j + fieldLeft - offset
            }
        }
    }

    fun getRank(): Int {
        return rank
    }
}