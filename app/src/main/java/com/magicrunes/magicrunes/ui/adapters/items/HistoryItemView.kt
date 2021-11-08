package com.magicrunes.magicrunes.ui.adapters.items

interface HistoryItemView: IITemView {
    fun setRuneName(runeName: String)
    fun setDescription(description: String)
    fun setRuneImage(runeName: String, isReverse: Boolean)
    fun setDate(date: String)
    fun setCommentImage(commentImage: Int?)
}