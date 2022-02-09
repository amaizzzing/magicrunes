package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Fortune5Runes: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(2, 4, 5, 6, 8)

    override var maxCol: Int = 3
    override var maxRow: Int = 3

    override var description: List<String> =
        listOf("Прошлое", "Настоящее", "Будущее", "Жизненный урок", "Как поступить")
}