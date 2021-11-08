package com.magicrunes.magicrunes.data.repositories.historyFortune

import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity

interface IHistoryFortuneRepository {
    suspend fun getFortuneHistoryById(historyId: Long): HistoryFortuneDbEntity?

    suspend fun updateHistoryFortune(idFortune: Long)

    suspend fun getLastInHistory(): HistoryFortuneDbEntity?

    suspend fun getAllHistory(): List<HistoryFortuneDbEntity>
}