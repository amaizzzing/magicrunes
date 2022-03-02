package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneFearsReality: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)
    override var maxCol: Int = 2
    override var maxRow: Int = 4
    override var description: List<String> =
        listOf(
            "Что рисует воображение",
            "Как выглядит ситуация на самом деле",
            "Негативное в воображении",
            "Явный негатив по данной проблеме",
            "Позитивное в воображении",
            "Явный позитив",
            "То, что не случится на самом деле",
            "Будущее и что случится"
        )
}