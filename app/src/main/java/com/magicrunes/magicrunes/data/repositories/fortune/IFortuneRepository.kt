package com.magicrunes.magicrunes.data.repositories.fortune

import com.magicrunes.magicrunes.data.entities.cache.FortuneDbEntity

interface IFortuneRepository {
    suspend fun getFortuneById(fortuneId: Long): FortuneDbEntity?

    suspend fun getAllFortunes(): List<FortuneDbEntity>

    suspend fun insert(fortune: FortuneDbEntity)

    suspend fun updateFavouriteFortune(id: Long, state: Boolean)

    suspend fun updateLastDate(id: Long, date: Long)
}