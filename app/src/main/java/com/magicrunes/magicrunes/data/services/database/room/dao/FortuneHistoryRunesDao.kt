package com.magicrunes.magicrunes.data.services.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity

@Dao
abstract class FortuneHistoryRunesDao: BaseDao<FortuneHistoryRunesDbEntity>() {
    @Query("DELETE FROM FortuneHistoryRunesDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM FortuneHistoryRunesDbEntity")
    abstract suspend fun getAll(): List<FortuneHistoryRunesDbEntity>

    @Query("SELECT * FROM FortuneHistoryRunesDbEntity WHERE idHistory = :historyDate" ) // idHistory == historyDate
    abstract suspend fun getRunesByHistoryDate(historyDate: Long): List<FortuneHistoryRunesDbEntity>
}