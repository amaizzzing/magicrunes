package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneWhoAmI: ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(1, 5, 8, 13, 16, 20, 23)
    override var maxCol: Int = 5
    override var maxRow: Int = 5
    override var description: List<String> =
        listOf(
            "Моя самооценка",
            "Как меня видят окружающие",
            "Позиция на жизненном пути",
            "Какую проблему решить, какие страхи преодолять",
            "Что достигнуто",
            "Что предстоит пройти",
            "На что обратить особое внимание"
        )
}