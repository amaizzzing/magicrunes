package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneRelationshipAnalysis: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(2, 8, 10, 6, 12, 14, 18)
    override var maxCol: Int = 7
    override var maxRow: Int = 3
    override var description: List<String> =
        listOf(
            "Отношение партнера к Вам",
            "Как партнер видит отношения",
            "Что хочет, чего не хватает",
            "Ваше отношение к партнеру",
            "Как Вы видите отношения",
            "Чего хотите от партнера",
            "Общее в отношениях"
        )
}