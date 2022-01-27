package com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity

interface IFortuneHistoryRunesRepository {
    suspend fun insertFortuneRune(fortuneRune: FortuneHistoryRunesDbEntity)

    suspend fun getRunesByHistoryDate(historyDate: Long): List<FortuneHistoryRunesDbEntity>
}