package com.magicrunes.magicrunes.data.repositories.historyFortune

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity
import com.magicrunes.magicrunes.data.services.database.firestore.IFireDatabase
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class FirestoreHistoryFortuneRepository(
    private val firestoreDB: IFireDatabase,
    private val localDB: MagicRunesDB
): IHistoryFortuneRepository {
    override suspend fun getFortuneHistoryByDate(historyDate: Long): HistoryFortuneDbEntity? {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        return firestoreDB.getFortuneHistoryByDate(idUser, historyDate).getOrNull()
    }

    override suspend fun updateHistoryFortune(
        idFortune: Long,
        date: Long,
        fortuneRunesList: List<FortuneHistoryRunesDbEntity>
    ) {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        firestoreDB.updateHistoryFortune(idUser, idFortune, date, fortuneRunesList)
    }

    override suspend fun getLastInHistory(): HistoryFortuneDbEntity? {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        return firestoreDB.getLastFortuneInHistory(idUser).getOrNull()
    }

    override suspend fun getLastInHistoryByIdFortune(idFortune: Long): HistoryFortuneDbEntity? {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        return firestoreDB.getLastInHistoryByIdFortune(idUser, idFortune).getOrNull()
    }


    override suspend fun getAllHistory(): List<HistoryFortuneDbEntity> {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        return firestoreDB.getAllHistoryFortune(idUser).getOrNull() ?: listOf()
    }
}