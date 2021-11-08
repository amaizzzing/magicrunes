package com.magicrunes.magicrunes.ui.models.lists

import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity
import com.magicrunes.magicrunes.data.entities.cache.RuneDescriptionDbEntity

data class InfoRuneModel(
    var idRune: Long = 0L,
    var runeName: String = "",
    var mainDescription: String = "",
    var avDescription: String = "",
    var revDescription: String = "",
    var imageName: String = ""
) {
    constructor(runeDbEntity: RuneDbEntity, runeDescription: RuneDescriptionDbEntity): this(
        runeDbEntity.id,
        runeDbEntity.name,
        runeDbEntity.mainDescription,
        runeDescription.avDescription,
        runeDescription.revDescription,
        runeDbEntity.localImageName
    )
}