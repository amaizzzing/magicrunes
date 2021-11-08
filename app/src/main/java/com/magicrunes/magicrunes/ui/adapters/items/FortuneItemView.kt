package com.magicrunes.magicrunes.ui.adapters.items

interface FortuneItemView: IITemView {
    fun setFortuneName(fortuneName: String)
    fun setFortuneDescription(description: String)
    fun setFortuneImage(fortuneName: String)
    fun setDate(date: String)
    fun setFavouriteImage(favouriteImage: Int)
}