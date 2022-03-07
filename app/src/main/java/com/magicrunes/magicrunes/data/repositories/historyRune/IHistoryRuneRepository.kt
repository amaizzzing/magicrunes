package com.magicrunes.magicrunes.data.repositories.historyRune

import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity

interface IHistoryRuneRepository {
    suspend fun insert(historyRuneDbEntity: HistoryRuneDbEntity)

    suspend fun getRuneByHistoryDate(historyDate: Long): HistoryRuneDbEntity?

    suspend fun getLastRune(): HistoryRuneDbEntity?

    suspend fun getAllHistory(): List<HistoryRuneDbEntity>

    suspend fun updateComment(historyDate: Long, comment: String)

    suspend fun updateHistoryRuneByDate(
        idRune: Long,
        comment: String,
        state: Int,
        syncState: Int,
        isNotificationShow: Int,
        historyDate: Long
    )

    suspend fun updateNotificationShow(isNotificationShow: Int, historyDate: Long)
}