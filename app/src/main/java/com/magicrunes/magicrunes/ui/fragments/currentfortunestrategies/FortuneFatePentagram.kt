package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FortuneFatePentagram : ICurrentFragmentStrategy, Parcelable {
    override var visibleRuneList: ArrayList<Int> =
        arrayListOf(4, 1, 2, 6, 7, 10, 11, 12, 18, 24, 25, 26, 22, 29, 30, 28, 35, 34)
    override var maxCol: Int = 7
    override var maxRow: Int = 5
    override var description: List<String> =
        listOf(
            "Общая характеристика прошлого воплощения",
            "Физическое состояние в том воплощении",
            "Эмоционального состояния в том воплощении",
            "Интелектуальное состояние в том воплощении",
            "Духовное состояние в том воплощении",
            "Важные события того воплощения",
            "Важные события того воплощения",
            "Важные события того воплощения",
            "Важный мотив повторяющийся в этой жизни",
            "Важные люди в том воплощении",
            "Важные люди в том воплощении",
            "Важные люди в том воплощении",
            "Важный человек в этой жизни",
            "Главный урок из того воплощения",
            "Незаконченные дела с того воплощения",
            "Таланты того воплощения",
            "Проблемы из того воплощения",
            "Значение жизни в развитии Души"
        )
}