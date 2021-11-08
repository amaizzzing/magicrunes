package com.magicrunes.magicrunes.ui.models

import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity

data class RuneOfTheDayModel(
    var idRune: Long = 0L,
    var name: String = "",
    var description: String = "",
    var avrevDescription: String = "",
    var image: String = "",
    var isReverse: Boolean = false
) {
    constructor(runeDbEntity: RuneDbEntity, isNeedReverse: Boolean, avrevDescription: String): this() {
        this.idRune = runeDbEntity.id
        this.name = runeDbEntity.name
        this.description = runeDbEntity.mainDescription
        this.avrevDescription = avrevDescription
        this.image = runeDbEntity.imageLink.ifEmpty { runeDbEntity.localImageName }
        this.isReverse = isNeedReverse
    }
}