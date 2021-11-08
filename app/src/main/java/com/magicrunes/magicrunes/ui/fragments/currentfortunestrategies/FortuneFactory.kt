package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

class FortuneFactory {
    fun createFortune(id: Long): ICurrentFragmentStrategy {
        return when(id) {
            1L -> Fortune3Runes()
            else -> Fortune5Runes()
        }
    }
}