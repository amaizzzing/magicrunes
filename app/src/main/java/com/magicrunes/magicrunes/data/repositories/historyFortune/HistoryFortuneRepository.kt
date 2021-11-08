package com.magicrunes.magicrunes.data.repositories.historyFortune

import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity
import com.magicrunes.magicrunes.data.services.database.db.MagicRunesDB
import org.joda.time.DateTime

class HistoryFortuneRepository(
    private val dbService: MagicRunesDB
): IHistoryFortuneRepository {
    override suspend fun getFortuneHistoryById(historyId: Long): HistoryFortuneDbEntity? =
        dbService.historyFortuneDao.getFortuneHistoryById(historyId)

    override suspend fun updateHistoryFortune(idFortune: Long) {
        dbService.historyFortuneDao.insertOrUpdate(
            HistoryFortuneDbEntity().apply {
                this.idFortune = idFortune
                this.date = DateTime().millis
                this.comment = ""
            }
        )
    }

    override suspend fun getLastInHistory(): HistoryFortuneDbEntity? =
        dbService.historyFortuneDao.getLastInHistory()

    override suspend fun getAllHistory(): List<HistoryFortuneDbEntity> =
        dbService.historyFortuneDao.getAll()
}