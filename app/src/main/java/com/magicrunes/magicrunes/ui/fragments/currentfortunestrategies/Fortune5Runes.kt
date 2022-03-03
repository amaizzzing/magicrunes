package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Fortune5Runes: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(4, 5, 6, 2, 8)

    override var maxCol: Int = 3
    override var maxRow: Int = 3

    override var description: List<String> =
        listOf("Прошлое", "Настоящее", "Результат ситуации", "Ожидаемая помощь", "Изменить невозможно")
}