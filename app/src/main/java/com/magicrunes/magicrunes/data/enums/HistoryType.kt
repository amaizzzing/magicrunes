package com.magicrunes.magicrunes.data.enums

sealed class HistoryType {
    object RuneType: HistoryType()
    object FortuneType: HistoryType()
    object AllHistory: HistoryType()
}
