package com.magicrunes.magicrunes.data.repositories.userInfo

import com.magicrunes.magicrunes.data.entities.cache.UserInfoDbEntity

interface IUserInfoRepository {
    suspend fun getUserInfo(): UserInfoDbEntity?
}