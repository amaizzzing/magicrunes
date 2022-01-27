package com.magicrunes.magicrunes.data.services.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.RuneDescriptionDbEntity

@Dao
abstract class RuneDescriptionDao: BaseDao<RuneDescriptionDbEntity>() {
    @Query("DELETE FROM RuneDescriptionDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM RuneDescriptionDbEntity")
    abstract suspend fun getAll(): List<RuneDescriptionDbEntity>

    @Query("SELECT * FROM RuneDescriptionDbEntity WHERE idRune = :runeId")
    abstract suspend fun getRuneById(runeId: Long): RuneDescriptionDbEntity?
}