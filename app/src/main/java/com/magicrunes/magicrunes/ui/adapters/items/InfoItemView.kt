package com.magicrunes.magicrunes.ui.adapters.items

interface InfoItemView: IITemView {
    fun setRuneName(runeName: String)
    fun setDescription(description: String)
    fun setRuneImage(runeImage: String)
}