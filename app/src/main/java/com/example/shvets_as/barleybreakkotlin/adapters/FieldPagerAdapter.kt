package com.example.shvets_as.barleybreakkotlin.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.shvets_as.barleybreakkotlin.fragments.FieldFragment

/**
 * Created by Shvets_AS on 03.04.2018.
 */

class FieldPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fieldFragments = mutableListOf<FieldFragment>(
            FieldFragment.newInstance(3),
            FieldFragment.newInstance(4),
            FieldFragment.newInstance(5))

    override fun getItem(position: Int): Fragment {
        return fieldFragments[position]
    }

    override fun getCount(): Int {
        return fieldFragments.size;
    }

    fun animateOffset(pos: Int, offset: Int) {
        fieldFragments[pos].animateOffsetX(offset, true)
        if (fieldFragments.size > pos + 1) {
            fieldFragments[pos + 1].animateOffsetX(offset, false)
        }
    }
}