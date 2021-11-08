package com.magicrunes.magicrunes.data.repositories.rune

import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity
import com.magicrunes.magicrunes.data.services.database.db.MagicRunesDB

class RuneRepository(private val dbService: MagicRunesDB): IRuneRepository {
    override suspend fun getRuneById(id: Long): RuneDbEntity? =
        dbService.runeDao.getRuneById(id)

    override suspend fun insertRune(rune: RuneDbEntity) {
        dbService.runeDao.insert(rune)
    }

    override suspend fun getAllRunes(): List<RuneDbEntity> =
        dbService.runeDao.getAll()

    override suspend fun getRandomRune(): RuneDbEntity? =
        dbService.runeDao.getRandomRune()

    override suspend fun getRandomRunes(count: Int): List<RuneDbEntity> {
        return dbService.runeDao.getRandomRunes(count)
    }

}