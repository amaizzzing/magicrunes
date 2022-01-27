package com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class FortuneHistoryRunesRepository(
    private val dbService: MagicRunesDB
): IFortuneHistoryRunesRepository {
    override suspend fun insertFortuneRune(fortuneRune: FortuneHistoryRunesDbEntity) {
        dbService.fortuneHistoryRunesDao.insert(fortuneRune)
    }

    override suspend fun getRunesByHistoryDate(historyDate: Long): List<FortuneHistoryRunesDbEntity> =
        dbService.fortuneHistoryRunesDao.getRunesByHistoryDate(historyDate)
}