package com.magicrunes.magicrunes.data.services.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.UserInfoDbEntity

@Dao
abstract class UserInfoDao: BaseDao<UserInfoDbEntity>() {
    @Query("SELECT * FROM UserInfoDbEntity LIMIT 1")
    abstract suspend fun getUserInfo(): UserInfoDbEntity?
}