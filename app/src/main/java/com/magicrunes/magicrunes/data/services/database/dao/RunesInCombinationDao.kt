package com.magicrunes.magicrunes.data.services.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.RunesInCombinationDbEntity

@Dao
abstract class RunesInCombinationDao: BaseDao<RunesInCombinationDbEntity>() {
    @Query("DELETE FROM RunesInCombinationDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM RunesInCombinationDbEntity")
    abstract suspend fun getAll(): List<RunesInCombinationDbEntity>
}