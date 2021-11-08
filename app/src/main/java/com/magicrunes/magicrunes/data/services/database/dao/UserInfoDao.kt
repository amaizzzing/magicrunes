package com.magicrunes.magicrunes.data.services.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.magicrunes.magicrunes.data.entities.cache.UserInfoDbEntity

@Dao
abstract class UserInfoDao: BaseDao<UserInfoDbEntity>() {
    @Query("SELECT * FROM UserInfoDbEntity")
    abstract suspend fun getUserInfo(): UserInfoDbEntity?
}