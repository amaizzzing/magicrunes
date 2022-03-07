package com.magicrunes.magicrunes.data.services.database.firestore

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity

interface IFireDatabase {
    suspend fun insertUser(id: String): Result<Unit>

    suspend fun getLastRune(idUser: String): Result<HistoryRuneDbEntity?>

    suspend fun insertHistoryRune(idUser: String, historyRune: HistoryRuneDbEntity): Result<Unit>

    suspend fun getRuneByHistoryDate(idUser: String, historyDate: Long): Result<HistoryRuneDbEntity?>

    suspend fun getAllHistoryRune(idUser: String): Result<List<HistoryRuneDbEntity>>

    suspend fun updateCommentRune(idUser: String, historyDate: Long, comment: String): Result<Unit>

    suspend fun getFortuneHistoryByDate(idUser: String, historyDate: Long): Result<HistoryFortuneDbEntity?>

    suspend fun updateHistoryFortune(idUser: String, idFortune: Long, date: Long, fortuneRunesList: List<FortuneHistoryRunesDbEntity>): Result<Unit>

    suspend fun getLastFortuneInHistory(idUser: String): Result<HistoryFortuneDbEntity?>

    suspend fun getLastInHistoryByIdFortune(idUser: String, idFortune: Long): Result<HistoryFortuneDbEntity?>

    suspend fun getAllHistoryFortune(idUser: String): Result<List<HistoryFortuneDbEntity>>

    suspend fun insertFortuneHistoryRune(idUser: String, fortuneRune: FortuneHistoryRunesDbEntity): Result<Unit>

    suspend fun getFortuneRunesByHistoryDate(idUser: String, historyDate: Long): Result<List<FortuneHistoryRunesDbEntity>>

    suspend fun updateHistoryRuneByDate(
        idUser: String,
        idRune: Long,
        comment: String,
        state: Int,
        syncState: Int,
        isNotificationShow: Int,
        historyDate: Long
    ): Result<Unit>

    suspend fun updateNotificationShow(
        idUser: String,
        isNotificationShow: Int,
        historyDate: Long
    ): Result<Unit>
}