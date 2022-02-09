package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

interface ICurrentFragmentStrategy {
    var visibleRuneList: ArrayList<Int>

    var maxCol: Int
    var maxRow: Int

    var description: List<String>
}