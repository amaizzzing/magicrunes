package com.magicrunes.magicrunes.ui.models

import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity
import com.magicrunes.magicrunes.utils.DateUtils

data class CommentDialogModel(
    var idRune: Long = 0L,
    var idHistory: Long = 0L,
    var runeName: String = "",
    var date: String = "",
    var comment: String = ""
) {
    constructor(
        runeDbEntity: RuneDbEntity,
        historyRuneDbEntity: HistoryRuneDbEntity
    ): this() {
        idRune = runeDbEntity.id
        idHistory = historyRuneDbEntity.id
        runeName = runeDbEntity.name
        date = DateUtils.getStringDate(historyRuneDbEntity.date)
        comment = historyRuneDbEntity.comment
    }
}