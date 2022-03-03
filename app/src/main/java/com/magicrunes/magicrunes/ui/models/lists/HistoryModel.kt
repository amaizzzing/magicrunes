package com.magicrunes.magicrunes.ui.models.lists

import com.magicrunes.magicrunes.data.entities.cache.*
import com.magicrunes.magicrunes.data.enums.HistoryType
import com.magicrunes.magicrunes.utils.DateUtils

data class HistoryModel(
    var id: Long = 0L,
    var name: String = "",
    var mainDescription: String = "",
    var avDescription: String = "",
    var revDescription: String = "",
    var image: String = "",
    var comment: String = "",
    var date: String = "",
    var dateInMillis: Long = 0L,
    var state: Int = 0,
    var historyType: HistoryType
) {
    constructor(
        runeDbEntity: RuneDbEntity,
        runeDescriptionDbEntity: RuneDescriptionDbEntity,
        historyRuneDbEntity: HistoryRuneDbEntity
    ): this(
        id = historyRuneDbEntity.id,
        name = runeDbEntity.name,
        mainDescription = runeDbEntity.mainDescription,
        avDescription = runeDescriptionDbEntity.avDescription,
        revDescription = runeDescriptionDbEntity.revDescription,
        image = runeDbEntity.localImageName,
        comment = historyRuneDbEntity.comment,
        date = DateUtils.getStringDate(historyRuneDbEntity.date),
        dateInMillis = historyRuneDbEntity.date,
        state = historyRuneDbEntity.state,
        historyType = HistoryType.RuneType
    )

    constructor(
        fortuneDbEntity: FortuneDbEntity,
        historyFortuneDbEntity: HistoryFortuneDbEntity
    ): this(
        id = historyFortuneDbEntity.id,
        name = fortuneDbEntity.nameFortune,
        mainDescription = fortuneDbEntity.description,
        avDescription = "",
        revDescription = "",
        image = fortuneDbEntity.localImageName,
        comment = historyFortuneDbEntity.comment,
        date = DateUtils.getStringDate(historyFortuneDbEntity.date),
        dateInMillis = historyFortuneDbEntity.date,
        state = 0,
        historyType = HistoryType.FortuneType
    )
}