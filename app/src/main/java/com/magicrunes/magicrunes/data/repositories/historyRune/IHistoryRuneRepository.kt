package com.magicrunes.magicrunes.data.repositories.historyRune

import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity

interface IHistoryRuneRepository {
    suspend fun insert(historyRuneDbEntity: HistoryRuneDbEntity)

    suspend fun getRuneById(id: Long): HistoryRuneDbEntity?

    suspend fun getRuneByHistoryId(historyId: Long): HistoryRuneDbEntity?

    suspend fun getLastRune(): HistoryRuneDbEntity?

    suspend fun getAllHistory(): List<HistoryRuneDbEntity>

    suspend fun updateComment(idHistory: Long, comment: String)
}