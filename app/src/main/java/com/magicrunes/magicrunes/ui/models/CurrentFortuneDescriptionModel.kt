package com.magicrunes.magicrunes.ui.models

data class CurrentFortuneDescriptionModel(
    var idRune: Long = 0L,
    var idHistory: Long = 0L,
    var idFortune: Long = 0L,
    var name: String = "",
    var description: String = "",
    var avrevDescription: String = "",
    var image: String = "",
    var isReverse: Boolean = false
) {
}