package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneLover: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(4, 8 , 10, 11, 12, 14, 24, 26, 32)

    override var maxCol: Int = 7
    override var maxRow: Int = 5

    override var description: List<String> =
        listOf(
            "Насколько внимателен к вам партнер",
            "Насколько вы совместимы",
            "Общие интересы и мечты",
            "Крепка ли связь",
            "Сексуальность в глазах партнера",
            "Соответствие стандартам партнера",
            "Отношения в материальном плане",
            "Останетесь ли вместе",
            "Результат"
        )
}