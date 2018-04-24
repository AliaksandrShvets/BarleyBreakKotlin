package com.example.shvets_as.barleybreakkotlin.activity


import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.widget.TextView
import com.example.shvets_as.barleybreakkotlin.R
import com.example.shvets_as.barleybreakkotlin.adapters.FieldPagerAdapter
import com.example.shvets_as.barleybreakkotlin.fragments.B_RANK
import com.example.shvets_as.barleybreakkotlin.fragments.FieldFragment
import com.example.shvets_as.barleybreakkotlin.utils.FONT_HIND_SILIGURI_LIGHT_TTF
import com.example.shvets_as.barleybreakkotlin.utils.animateMainScreen
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Shvets_AS on 02.04.2018.
 */

class MainActivity : FragmentActivity() {

    private val scaleIndicator = 0.8f

    private lateinit var pagerAdapter: FieldPagerAdapter
    private val indicators = mutableListOf<TextView>()
    private var isFirstEnter = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        indicators.add(am_indicator_8)
        indicators.add(am_indicator_15)
        indicators.add(am_indicator_24)
        initViews()
        initPager()
    }

    private fun initViews() {
        am_start_game.typeface = Typeface.createFromAsset(assets, FONT_HIND_SILIGURI_LIGHT_TTF)
        am_start_game.setOnClickListener {
            animateMainScreen(am_top_container, am_pager, am_start_game, false, false, {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra(B_RANK, (pagerAdapter.getItem(am_pager.currentItem) as FieldFragment).getRank())
                startActivity(intent)
            })
        }
    }

    private fun initPager() {
        indicators.forEachIndexed { index, textView ->
            with(textView) {
                typeface = Typeface.createFromAsset(assets, FONT_HIND_SILIGURI_LIGHT_TTF)
                scaleX = scaleIndicator
                scaleY = scaleIndicator
                tag = index
                setOnClickListener { view -> am_pager.currentItem = view.tag as Int }
            }
        }

        pagerAdapter = FieldPagerAdapter(getSupportFragmentManager())
        am_pager.adapter = pagerAdapter
        am_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                pagerAdapter.animateOffset(position, positionOffsetPixels)
                indicators[position].scaleX = scaleIndicator + (1 - scaleIndicator) * (1 - positionOffset)
                indicators[position].scaleY = scaleIndicator + (1 - scaleIndicator) * (1 - positionOffset)
                if (position + 1 < indicators.size) {
                    indicators[position + 1].scaleX = scaleIndicator + (1 - scaleIndicator) * positionOffset
                    indicators[position + 1].scaleY = scaleIndicator + (1 - scaleIndicator) * positionOffset
                }
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }

    override fun onResume() {
        super.onResume()
        animateMainScreen(am_top_container, am_pager, am_start_game, true, isFirstEnter, {})
        isFirstEnter = false
    }
}