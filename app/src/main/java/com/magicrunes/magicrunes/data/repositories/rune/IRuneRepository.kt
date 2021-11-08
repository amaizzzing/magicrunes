package com.magicrunes.magicrunes.data.repositories.rune

import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity

interface IRuneRepository {
    suspend fun getRuneById(id: Long): RuneDbEntity?

    suspend fun insertRune(rune: RuneDbEntity)

    suspend fun getRandomRune(): RuneDbEntity?

    suspend fun getRandomRunes(count: Int): List<RuneDbEntity>

    suspend fun getAllRunes(): List<RuneDbEntity>
}