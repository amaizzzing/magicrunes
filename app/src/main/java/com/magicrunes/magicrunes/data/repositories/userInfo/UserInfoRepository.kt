package com.magicrunes.magicrunes.data.repositories.userInfo

import com.magicrunes.magicrunes.data.entities.cache.UserInfoDbEntity
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class UserInfoRepository(
    private val dbService: MagicRunesDB
): IUserInfoRepository {
    override suspend fun getUserInfo(): UserInfoDbEntity? =
        dbService.userInfoDao.getUserInfo()

    override suspend fun updateUserInfo(userInfoDbEntity: UserInfoDbEntity) {
        dbService.userInfoDao.update(userInfoDbEntity)
    }

    override suspend fun insertUserInfo(userInfoDbEntity: UserInfoDbEntity) {
        dbService.userInfoDao.insert(userInfoDbEntity)
    }
}