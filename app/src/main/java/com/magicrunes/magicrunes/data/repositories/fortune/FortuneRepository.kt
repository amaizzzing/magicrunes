package com.magicrunes.magicrunes.data.repositories.fortune

import com.magicrunes.magicrunes.data.entities.cache.FortuneDbEntity
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class FortuneRepository(
    private val dbService: MagicRunesDB
): IFortuneRepository {
    override suspend fun getFortuneById(fortuneId: Long): FortuneDbEntity? =
        dbService.fortuneDao.getFortuneById(fortuneId)

    override suspend fun getAllFortunes(): List<FortuneDbEntity> =
        dbService.fortuneDao.getAll()

    override suspend fun insert(fortune: FortuneDbEntity) {
        dbService.fortuneDao.insert(fortune)
    }

    override suspend fun updateFavouriteFortune(id: Long, state: Boolean) {
        dbService.fortuneDao.updateFavouriteState(id, state)
    }

    override suspend fun updateLastDate(id: Long, date: Long) {
        dbService.fortuneDao.updateLastDate(id, date)
    }
}