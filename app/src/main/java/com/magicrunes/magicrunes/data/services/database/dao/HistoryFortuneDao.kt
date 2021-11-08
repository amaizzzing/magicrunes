package com.magicrunes.magicrunes.data.services.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity

@Dao
abstract class HistoryFortuneDao(): BaseDao<HistoryFortuneDbEntity>() {
    @Query("DELETE FROM HistoryFortuneDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM HistoryFortuneDbEntity ORDER BY date DESC")
    abstract suspend fun getAll(): List<HistoryFortuneDbEntity>

    @Query("SELECT * FROM HistoryFortuneDbEntity WHERE id = :historyId")
    abstract suspend fun getFortuneHistoryById(historyId: Long): HistoryFortuneDbEntity?

    @Query("SELECT * FROM HistoryFortuneDbEntity ORDER BY date DESC LIMIT 1")
    abstract suspend fun getLastInHistory(): HistoryFortuneDbEntity?
}