package com.magicrunes.magicrunes.ui.models.lists

import com.magicrunes.magicrunes.data.entities.cache.FortuneDbEntity
import com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies.ICurrentFragmentStrategy
import com.magicrunes.magicrunes.utils.DateUtils

data class FortuneModel(
    var id: Long,
    var nameFortune: String = "",
    var description: String = "",
    var date: String = "",
    var dateInMillis: Long = 0,
    var image: String = "",
    var imageId: Int = -1,
    var isFavourite: Boolean = false,
    var currentStrategy: ICurrentFragmentStrategy? = null
) {
    constructor(fortuneDbEntity: FortuneDbEntity): this(
        id = fortuneDbEntity.id,
        nameFortune = fortuneDbEntity.nameFortune,
        description = fortuneDbEntity.description,
        date =
            if (fortuneDbEntity.lastDate == 0L) {
                "-"
            } else {
                DateUtils.getStringDate(fortuneDbEntity.lastDate)
            },
        dateInMillis = fortuneDbEntity.lastDate,
        image = fortuneDbEntity.localImageName,
        imageId = -1,
        isFavourite = fortuneDbEntity.favourite
    )

    constructor(fortuneDbEntity: FortuneDbEntity, date: Long, currentStrategy: ICurrentFragmentStrategy): this(
        id = fortuneDbEntity.id,
        nameFortune = fortuneDbEntity.nameFortune,
        description = fortuneDbEntity.description,
        date =
            if (date == 0L) {
                "-"
            } else {
                DateUtils.getStringDate(date)
            },
        dateInMillis = date,
        image = fortuneDbEntity.localImageName,
        imageId = -1,
        isFavourite = fortuneDbEntity.favourite,
        currentStrategy = currentStrategy
    )
}