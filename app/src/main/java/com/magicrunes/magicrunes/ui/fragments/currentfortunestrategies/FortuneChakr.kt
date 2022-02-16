package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneChakr: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(7, 6 ,5, 4, 3, 2, 1)

    override var maxCol: Int = 1
    override var maxRow: Int = 7

    override var description: List<String> =
        listOf(
            "Муладхара/Связь с родом",
            "Свадхистана/Секс. энергия",
            "Манипура/Власть и деньги",
            "Анахата/Сердце и любовь",
            "Вишудха/Воля",
            "Аджна/Третий глаз",
            "Сахасрара/Душа"
        )
}