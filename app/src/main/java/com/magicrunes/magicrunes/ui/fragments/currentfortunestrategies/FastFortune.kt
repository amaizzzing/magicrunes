package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FastFortune: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(5)

    override var maxCol: Int = 3
    override var maxRow: Int = 3

    override var description: List<String> =
        listOf("Быстрый ответ")
}