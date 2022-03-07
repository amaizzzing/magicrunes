package com.magicrunes.magicrunes.data.repositories.historyRune

import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import com.magicrunes.magicrunes.data.services.database.firestore.IFireDatabase
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class FirestoreHistoryRuneRepository(
    private val firestoreDB: IFireDatabase,
    private val localDB: MagicRunesDB
): IHistoryRuneRepository {
    override suspend fun insert(historyRuneDbEntity: HistoryRuneDbEntity) {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        firestoreDB.insertHistoryRune(idUser, historyRuneDbEntity)
    }

    override suspend fun getRuneByHistoryDate(historyDate: Long): HistoryRuneDbEntity? {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        return firestoreDB.getRuneByHistoryDate(idUser, historyDate)?.getOrNull()
    }

    override suspend fun getLastRune(): HistoryRuneDbEntity? {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        return firestoreDB.getLastRune(idUser)?.getOrNull()
    }

    override suspend fun getAllHistory(): List<HistoryRuneDbEntity> {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        return firestoreDB.getAllHistoryRune(idUser)?.getOrNull() ?: listOf()
    }


    override suspend fun updateComment(historyDate: Long, comment: String) {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        firestoreDB.updateCommentRune(idUser, historyDate, comment)
    }

    override suspend fun updateHistoryRuneByDate(
        idRune: Long,
        comment: String,
        state: Int,
        syncState: Int,
        isNotificationShow: Int,
        historyDate: Long
    ) {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        firestoreDB.updateHistoryRuneByDate(
            idUser,
            idRune,
            comment,
            state,
            syncState,
            isNotificationShow,
            historyDate
        )
    }

    override suspend fun updateNotificationShow(isNotificationShow: Int, historyDate: Long) {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        firestoreDB.updateNotificationShow(
            idUser,
            isNotificationShow,
            historyDate
        )
    }
}