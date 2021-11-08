package com.magicrunes.magicrunes.data.services.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity

@Dao
abstract class RuneDao: BaseDao<RuneDbEntity>() {
    @Query("DELETE FROM RuneDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM RuneDbEntity")
    abstract suspend fun getAll(): List<RuneDbEntity>

    @Query("SELECT * FROM RuneDbEntity WHERE id = :runeId")
    abstract suspend fun getRuneById(runeId: Long): RuneDbEntity?

    @Query("SELECT * FROM RuneDbEntity ORDER BY RANDOM() LIMIT 1")
    abstract suspend fun getRandomRune(): RuneDbEntity?

    @Query("SELECT * FROM RuneDbEntity ORDER BY RANDOM() LIMIT :count")
    abstract suspend fun getRandomRunes(count: Int): List<RuneDbEntity>
}