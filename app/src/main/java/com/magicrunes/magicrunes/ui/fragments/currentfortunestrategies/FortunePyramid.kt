package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortunePyramid: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(22, 28, 24, 26, 16, 20, 18, 12, 10, 4)
    override var maxCol: Int = 7
    override var maxRow: Int = 4
    override var description: List<String> =
        listOf(
            "О чем ваши мысли",
            "Ситуация на данный момент",
            "Оправдание или обоснование намерений",
            "Настоящие причины ситуации",
            "Надежды или опасения",
            "Что повлияет в ближайшее время на будущее",
            "Посредством чего вы можете влиять на исход",
            "Неотвратимое событие будущего",
            "Ваша реакция",
            "Итог"
        )
}