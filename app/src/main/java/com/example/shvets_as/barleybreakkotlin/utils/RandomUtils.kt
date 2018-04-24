package com.example.shvets_as.barleybreakkotlin.utils

import java.util.*

/**
 * Created by Shvets_AS on 03.04.2018.
 */

fun getRandomList(length: Int): List<Int> {
    val randomList = ArrayList<Int>()
    for (i in 0 until length - 1) {
        randomList.add(i)
    }
    randomList.shuffle()
    randomList.add(length - 1)
    if (!isPossibleToSolve(randomList)) {
        val temp = randomList[length - 2]
        randomList[length - 2] = randomList[length - 3]
        randomList[length - 3] = temp
    }
    return randomList
}

private fun isPossibleToSolve(randomList: List<Int>): Boolean {
    var inversions = 0
    for (i in randomList.indices) {
        for (j in i until randomList.size) {
            if (randomList[i] > randomList[j]) {
                inversions++
            }
        }
    }
    // если количество инверсий четное, то решение существует
    return inversions % 2 == 0
}