package com.magicrunes.magicrunes.data.repositories.userInfo

import com.magicrunes.magicrunes.data.entities.cache.UserInfoDbEntity
import com.magicrunes.magicrunes.data.services.database.db.MagicRunesDB

class UserInfoRepository(
    private val dbService: MagicRunesDB
): IUserInfoRepository {
    override suspend fun getUserInfo(): UserInfoDbEntity? =
        dbService.userInfoDao.getUserInfo()
}