package com.magicrunes.magicrunes.data.repositories.historyFortune

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class HistoryFortuneRepository(
    private val dbService: MagicRunesDB
): IHistoryFortuneRepository {
    override suspend fun getFortuneHistoryByDate(historyDate: Long): HistoryFortuneDbEntity? =
        dbService.historyFortuneDao.getFortuneHistoryByDate(historyDate)

    override suspend fun updateHistoryFortune(idFortune: Long, date: Long, fortuneRunesList: List<FortuneHistoryRunesDbEntity>) {
        dbService.historyFortuneDao.insertOrUpdate(
            HistoryFortuneDbEntity().apply {
                this.idFortune = idFortune
                this.date = date
                this.comment = ""
            }
        )
    }

    override suspend fun getLastInHistory(): HistoryFortuneDbEntity? =
        dbService.historyFortuneDao.getLastInHistory()

    override suspend fun getLastInHistoryByIdFortune(idFortune: Long): HistoryFortuneDbEntity? =
        dbService.historyFortuneDao.getLastInHistoryByIdFortune(idFortune)

    override suspend fun getAllHistory(): List<HistoryFortuneDbEntity> =
        dbService.historyFortuneDao.getAll()
}