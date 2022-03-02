package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneBadLuckReasons: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(17, 6, 4, 25)
    override var maxCol: Int = 5
    override var maxRow: Int = 5
    override var description: List<String> =
        listOf(
            "Причина, не зависящая от человека",
            "Роковая-фатальная случайность",
            "Собственная глупость",
            "Тактические и методические ошибки"
        )
}