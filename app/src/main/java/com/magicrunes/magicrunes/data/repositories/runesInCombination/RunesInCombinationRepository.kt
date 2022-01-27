package com.magicrunes.magicrunes.data.repositories.runesInCombination

import com.magicrunes.magicrunes.data.entities.cache.RunesInCombinationDbEntity
import com.magicrunes.magicrunes.data.services.database.room.db.MagicRunesDB

class RunesInCombinationRepository(
    private val dbService: MagicRunesDB
): IRunesInCombinationRepository {
    override suspend fun getAll(): List<RunesInCombinationDbEntity> =
        dbService.runesInCombinationDao.getAll()
}