package com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity

interface IFortuneHistoryRunesRepository {
    suspend fun getAll(): List<FortuneHistoryRunesDbEntity>

    suspend fun insertFortuneRune(fortuneRune: FortuneHistoryRunesDbEntity)

    suspend fun getRunesByIdHistory(idHistory: Long): List<FortuneHistoryRunesDbEntity>
}