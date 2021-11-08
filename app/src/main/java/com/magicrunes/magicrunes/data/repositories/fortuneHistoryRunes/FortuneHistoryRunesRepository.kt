package com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.services.database.db.MagicRunesDB

class FortuneHistoryRunesRepository(
    private val dbService: MagicRunesDB
): IFortuneHistoryRunesRepository {
    override suspend fun getAll(): List<FortuneHistoryRunesDbEntity> =
        dbService.fortuneHistoryRunesDao.getAll()

    override suspend fun insertFortuneRune(fortuneRune: FortuneHistoryRunesDbEntity) {
        dbService.fortuneHistoryRunesDao.insert(fortuneRune)
    }

    override suspend fun getRunesByIdHistory(idHistory: Long): List<FortuneHistoryRunesDbEntity> =
        dbService.fortuneHistoryRunesDao.getRunesByIdHistory(idHistory)
}