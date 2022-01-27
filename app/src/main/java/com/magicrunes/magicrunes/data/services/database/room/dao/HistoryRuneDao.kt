package com.magicrunes.magicrunes.data.services.database.room.dao

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

    @Query("SELECT * FROM HistoryRuneDbEntity WHERE date = :historyDate")
    abstract suspend fun getRuneByHistoryDate(historyDate: Long): HistoryRuneDbEntity?

    @Query("SELECT * FROM HistoryRuneDbEntity order by date DESC LIMIT 1")
    abstract suspend fun getLastRune():HistoryRuneDbEntity?

    @Query("UPDATE HistoryRuneDbEntity SET comment = :comment WHERE date = :historyDate")
    abstract suspend fun updateComment(historyDate: Long, comment: String)

    @Query("UPDATE HistoryRuneDbEntity SET idRune = :idRune, comment = :comment, state = :state, syncState = :syncState WHERE date = :historyDate")
    abstract suspend fun updateByDate(idRune: Long, comment: String, state: Int, syncState: Int, historyDate: Long)
}