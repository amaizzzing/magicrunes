package com.magicrunes.magicrunes.data.repositories.historyRune

import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import com.magicrunes.magicrunes.data.services.database.db.MagicRunesDB

class HistoryRuneRepository(
    private val dbService: MagicRunesDB
): IHistoryRuneRepository {
    override suspend fun insert(historyRuneDbEntity: HistoryRuneDbEntity) {
        dbService.historyRuneDao.insert(historyRuneDbEntity)
    }

    override suspend fun getRuneById(id: Long): HistoryRuneDbEntity? =
        dbService.historyRuneDao.getRuneById(id)

    override suspend fun getRuneByHistoryId(historyId: Long): HistoryRuneDbEntity? =
        dbService.historyRuneDao.getRuneByHistoryId(historyId)

    override suspend fun getLastRune(): HistoryRuneDbEntity? =
        dbService.historyRuneDao.getLastRune()

    override suspend fun getAllHistory(): List<HistoryRuneDbEntity> =
        dbService.historyRuneDao.getAll()

    override suspend fun updateComment(idHistory: Long, comment: String) {
        dbService.historyRuneDao.updateComment(idHistory, comment)
    }
}