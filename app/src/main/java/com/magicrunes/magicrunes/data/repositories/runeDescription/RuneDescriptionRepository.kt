package com.magicrunes.magicrunes.data.repositories.runeDescription

import com.magicrunes.magicrunes.data.entities.cache.RuneDescriptionDbEntity
import com.magicrunes.magicrunes.data.services.database.db.MagicRunesDB

class RuneDescriptionRepository(
    private val dbService: MagicRunesDB
): IRuneDescriptionRepository {
    override suspend fun getRuneById(id: Long): RuneDescriptionDbEntity? =
        dbService.runeDescriptionDao.getRuneById(id)
}