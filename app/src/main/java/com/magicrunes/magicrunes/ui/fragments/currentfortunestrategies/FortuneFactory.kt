package com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies

class FortuneFactory {
    fun createFortune(id: Long): ICurrentFragmentStrategy {
        return when(id) {
            1L -> FastFortune()
            2L -> Fortune3Runes()
            3L -> Fortune5Runes()
            4L -> Fortune4Runes()
            5L -> FortuneChakr()
            6L -> FortuneLover()
            7L -> FortuneMarriageForecast()
            8L -> FortuneRelationshipAnalysis()
            9L -> FortuneBadLuckReasons()
            10L -> FortuneFearsReality()

            else -> FastFortune()
        }
    }
}