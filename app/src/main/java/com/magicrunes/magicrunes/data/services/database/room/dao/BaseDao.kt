package com.magicrunes.magicrunes.data.services.database.room.dao

import androidx.room.*
import com.magicrunes.magicrunes.data.entities.cache.BaseRoom

abstract class BaseDao<T : BaseRoom> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(obj: T): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(obj: T)

    @Delete
    abstract suspend fun delete(obj: T)

    @Transaction
    open suspend fun insertOrUpdate(obj: T) {
        if (insert(obj) == -1L) {
            update(obj)
        }
    }

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrUpdate(objects: Collection<T>): List<Long>*/
}
