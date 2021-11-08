package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Fortune5Runes: ICurrentFragmentStrategy, Parcelable {
    override var invisibleRuneList: ArrayList<Int> =
        arrayListOf(1, 3, 7, 9)

    override var description: List<String> =
        listOf("Прошлое", "Настоящее", "Будущее", "Жизненный урок", "Как поступить")
}