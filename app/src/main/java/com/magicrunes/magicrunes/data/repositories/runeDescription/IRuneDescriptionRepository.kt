package com.magicrunes.magicrunes.data.repositories.runeDescription

import com.magicrunes.magicrunes.data.entities.cache.RuneDescriptionDbEntity

interface IRuneDescriptionRepository {
    suspend fun getRuneById(id: Long): RuneDescriptionDbEntity?
}