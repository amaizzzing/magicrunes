package com.magicrunes.magicrunes.data.services.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.FortuneDbEntity

@Dao
abstract class FortuneDao: BaseDao<FortuneDbEntity>() {
    @Query("DELETE FROM FortuneDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM FortuneDbEntity")
    abstract suspend fun getAll(): List<FortuneDbEntity>

    @Query("SELECT * FROM FortuneDbEntity WHERE id = :fortuneId")
    abstract suspend fun getFortuneById(fortuneId: Long): FortuneDbEntity?

    @Query("UPDATE FortuneDbEntity SET favourite = :fav WHERE id = :id")
    abstract suspend fun updateFavouriteState(id: Long, fav: Boolean)

    @Query("UPDATE FortuneDbEntity SET lastDate = :date WHERE id = :id")
    abstract suspend fun updateLastDate(id: Long, date: Long)
}