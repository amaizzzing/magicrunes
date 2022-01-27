package com.magicrunes.magicrunes.data.services.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.CombinationDbEntity

@Dao
abstract class CombinationDao: BaseDao<CombinationDbEntity>() {
    @Query("DELETE FROM CombinationDbEntity")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM CombinationDbEntity")
    abstract suspend fun getAll(): List<CombinationDbEntity>
}