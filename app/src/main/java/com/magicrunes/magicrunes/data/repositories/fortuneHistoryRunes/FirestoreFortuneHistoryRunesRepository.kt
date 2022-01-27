package com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.services.database.firestore.IFireDatabase
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class FirestoreFortuneHistoryRunesRepository(
    private val firestoreDB: IFireDatabase,
    private val localDB: MagicRunesDB
): IFortuneHistoryRunesRepository {
    override suspend fun insertFortuneRune(fortuneRune: FortuneHistoryRunesDbEntity) {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        firestoreDB.insertFortuneHistoryRune(idUser, fortuneRune)
    }

    override suspend fun getRunesByHistoryDate(historyDate: Long): List<FortuneHistoryRunesDbEntity> {
        val idUser = localDB.userInfoDao.getUserInfo()?.idGoogle ?: ""
        return firestoreDB.getFortuneRunesByHistoryDate(idUser, historyDate).getOrNull() ?: listOf()
    }
}