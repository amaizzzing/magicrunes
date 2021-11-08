package com.magicrunes.magicrunes.data.services.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity

@Dao
abstract class HistoryRuneDao: BaseDao<HistoryRuneDbEntity>() {
    @Query("DELETE FROM HistoryRuneDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM HistoryRuneDbEntity ORDER BY date DESC")
    abstract suspend fun getAll(): List<HistoryRuneDbEntity>

    @Query("SELECT * FROM HistoryRuneDbEntity WHERE idRune = :runeId")
    abstract suspend fun getRuneById(runeId: Long): HistoryRuneDbEntity?

    @Query("SELECT * FROM HistoryRuneDbEntity WHERE id = :historyId")
    abstract suspend fun getRuneByHistoryId(historyId: Long): HistoryRuneDbEntity?

    @Query("SELECT * FROM HistoryRuneDbEntity order by date DESC LIMIT 1")
    abstract suspend fun getLastRune():HistoryRuneDbEntity?

    @Query("UPDATE HistoryRuneDbEntity SET comment = :comment WHERE id = :idHistory")
    abstract suspend fun updateComment(idHistory: Long, comment: String)
}