package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneMoonSecrets : ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(2, 3, 4, 6, 10, 13, 22, 23, 24)
    override var maxCol: Int = 5
    override var maxRow: Int = 5
    override var description: List<String> =
        listOf(
            "Влияние луны на личность",
            "Влияние луны на подсознание",
            "Влияние луны на интуицию",
            "Насколько открыта женская сущность луны",
            "Отношение к себе",
            "Светлая сторона луны - радостные аспекты",
            "Темная сторона луны - страхи, все что скрыто",
            "Влияние луны на судьбу",
            "Влияние луны на судьбу",
            "Влияние луны на судьбу"
        )
}