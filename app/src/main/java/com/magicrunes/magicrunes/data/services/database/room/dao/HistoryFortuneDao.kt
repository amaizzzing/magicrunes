package com.magicrunes.magicrunes.data.services.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity

@Dao
abstract class HistoryFortuneDao(): BaseDao<HistoryFortuneDbEntity>() {
    @Query("DELETE FROM HistoryFortuneDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM HistoryFortuneDbEntity ORDER BY date DESC")
    abstract suspend fun getAll(): List<HistoryFortuneDbEntity>

    @Query("SELECT * FROM HistoryFortuneDbEntity WHERE date = :historyDate")
    abstract suspend fun getFortuneHistoryByDate(historyDate: Long): HistoryFortuneDbEntity?

    @Query("SELECT * FROM HistoryFortuneDbEntity ORDER BY date DESC LIMIT 1")
    abstract suspend fun getLastInHistory(): HistoryFortuneDbEntity?

    @Query("SELECT * FROM HistoryFortuneDbEntity WHERE idFortune = :idFortune ORDER BY date DESC LIMIT 1")
    abstract suspend fun getLastInHistoryByIdFortune(idFortune: Long): HistoryFortuneDbEntity?
}