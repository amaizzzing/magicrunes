package com.magicrunes.magicrunes.data.repositories.combination

import com.magicrunes.magicrunes.data.entities.cache.CombinationDbEntity

interface ICombinationRepository {
    suspend fun getAll(): List<CombinationDbEntity>
}