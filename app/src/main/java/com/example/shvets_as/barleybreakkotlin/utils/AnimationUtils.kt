package com.example.shvets_as.barleybreakkotlin.utils

import android.view.View
import android.view.animation.*

/**
 * Created by Shvets_AS on 02.04.2018.
 */

const val FIELD_SIZE = 0.85f
const val FONT_HIND_SILIGURI_LIGHT_TTF = "hind_siliguri_light.ttf"

private const val MAIN_ACTIVITY_DURATION = 600
private const val TRANSLATE_DURATION = 200
private const val SCALE_DURATION = 1000
private const val SCALE_OFFSET = 200
private const val SCALE_FROM = 3f
private const val SCALE_TO = 1f

fun animateMainScreen(top: View, center: View, bottom: View, isEmersion: Boolean, isFirstEnter: Boolean, callback: () -> Unit) {

    if (isFirstEnter) {
        val scaleAnimation = ScaleAnimation(2f, 1f, 2f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        val alphaAnimation = AlphaAnimation(0f, 1f)
        val set = AnimationSet(false)
        set.addAnimation(scaleAnimation)
        set.addAnimation(alphaAnimation)
        set.duration = MAIN_ACTIVITY_DURATION.toLong()
        center.startAnimation(set)
    }

    val topY = (-top.height).toFloat()
    val translateTopAnimation = TranslateAnimation(0f, 0f,
            if (isEmersion) topY else 0f, if (isEmersion) 0f else topY)
    translateTopAnimation.duration = MAIN_ACTIVITY_DURATION.toLong()
    translateTopAnimation.fillAfter = true
    top.startAnimation(translateTopAnimation)

    val bottomY = top.y + top.height
    val translateBottomAnimation = TranslateAnimation(0f, 0f,
            if (isEmersion) bottomY else 0f, if (isEmersion) 0f else bottomY)
    translateBottomAnimation.duration = MAIN_ACTIVITY_DURATION.toLong()
    translateBottomAnimation.fillAfter = true
    bottom.startAnimation(translateBottomAnimation)

    translateBottomAnimation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
        }

        override fun onAnimationEnd(animation: Animation) {
            callback()
        }

        override fun onAnimationRepeat(animation: Animation) {
        }
    })
}

fun getTranslateAnimation(xFrom: Float, xTo: Float, yFrom: Float, yTo: Float): TranslateAnimation {
    val translateAnimation = TranslateAnimation(xFrom - xTo, 0f, yFrom - yTo, 0f)
    translateAnimation.duration = TRANSLATE_DURATION.toLong()
    return translateAnimation
}

fun getScaleAnimation(view: View, startOffset: Int): AnimationSet {
    val animationSet = AnimationSet(false)
    val scaleAnimation = ScaleAnimation(SCALE_FROM, SCALE_TO, SCALE_FROM, SCALE_TO,
            view.x + view.width / 2, view.y + view.height / 2)
    scaleAnimation.interpolator = DecelerateInterpolator()
    val alphaAnimation = AlphaAnimation(0f, 1f)
    alphaAnimation.interpolator = AccelerateInterpolator()
    animationSet.addAnimation(scaleAnimation)
    animationSet.addAnimation(alphaAnimation)
    animationSet.duration = SCALE_DURATION.toLong()
    animationSet.startOffset = (startOffset * SCALE_OFFSET).toLong()
    return animationSet
}