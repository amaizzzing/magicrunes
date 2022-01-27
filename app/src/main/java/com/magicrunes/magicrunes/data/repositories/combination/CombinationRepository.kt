package com.magicrunes.magicrunes.data.repositories.combination

import com.magicrunes.magicrunes.data.entities.cache.CombinationDbEntity
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class CombinationRepository(
    private val dbService: MagicRunesDB
): ICombinationRepository {
    override suspend fun getAll(): List<CombinationDbEntity> =
        dbService.combinationDao.getAll()
}