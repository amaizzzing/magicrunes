package com.magicrunes.magicrunes.data.repositories.historyFortune

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity

interface IHistoryFortuneRepository {
    suspend fun getFortuneHistoryByDate(historyDate: Long): HistoryFortuneDbEntity?

    suspend fun updateHistoryFortune(idFortune: Long, date: Long, fortuneRunesList: List<FortuneHistoryRunesDbEntity>)

    suspend fun getLastInHistory(): HistoryFortuneDbEntity?

    suspend fun getLastInHistoryByIdFortune(idFortune: Long): HistoryFortuneDbEntity?

    suspend fun getAllHistory(): List<HistoryFortuneDbEntity>
}