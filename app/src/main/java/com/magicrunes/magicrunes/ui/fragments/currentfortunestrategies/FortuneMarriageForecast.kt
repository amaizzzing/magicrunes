package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneMarriageForecast: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(10, 7, 4, 1, 2, 3, 8, 6)
    override var maxCol: Int = 3
    override var maxRow: Int = 4
    override var description: List<String> =
        listOf(
            "Прошлое",
            "Прошлое",
            "Настоящее",
            "Настоящее",
            "Будущее",
            "Будущее",
            "Отдаленное будущее",
            "Отдаленное будущее"
        )
}