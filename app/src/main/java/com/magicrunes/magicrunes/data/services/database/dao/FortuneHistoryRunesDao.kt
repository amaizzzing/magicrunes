package com.magicrunes.magicrunes.data.services.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity

@Dao
abstract class FortuneHistoryRunesDao: BaseDao<FortuneHistoryRunesDbEntity>() {
    @Query("DELETE FROM FortuneHistoryRunesDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM FortuneHistoryRunesDbEntity")
    abstract suspend fun getAll(): List<FortuneHistoryRunesDbEntity>

    @Query("SELECT * FROM FortuneHistoryRunesDbEntity WHERE idHistory = :idHistory" )
    abstract suspend fun getRunesByIdHistory(idHistory: Long): List<FortuneHistoryRunesDbEntity>
}