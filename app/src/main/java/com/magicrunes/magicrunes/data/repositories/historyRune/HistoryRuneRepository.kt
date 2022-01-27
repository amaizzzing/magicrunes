package com.magicrunes.magicrunes.data.repositories.historyRune

import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class HistoryRuneRepository(
    private val dbService: MagicRunesDB
): IHistoryRuneRepository {
    override suspend fun insert(historyRuneDbEntity: HistoryRuneDbEntity) {
        dbService.historyRuneDao.insert(historyRuneDbEntity)
    }

    override suspend fun getRuneByHistoryDate(historyDate: Long): HistoryRuneDbEntity? =
        dbService.historyRuneDao.getRuneByHistoryDate(historyDate)

    override suspend fun getLastRune(): HistoryRuneDbEntity? =
        dbService.historyRuneDao.getLastRune()

    override suspend fun getAllHistory(): List<HistoryRuneDbEntity> =
        dbService.historyRuneDao.getAll()

    override suspend fun updateComment(historyDate: Long, comment: String) {
        dbService.historyRuneDao.updateComment(historyDate, comment)
    }

    override suspend fun updateHistoryRuneByDate(
        idRune: Long,
        comment: String,
        state: Int,
        syncState: Int,
        historyDate: Long
    ) {
        dbService.historyRuneDao.updateByDate(idRune, comment, state, syncState, historyDate)
    }
}