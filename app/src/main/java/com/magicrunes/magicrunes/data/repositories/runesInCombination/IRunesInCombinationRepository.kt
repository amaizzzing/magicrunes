package com.magicrunes.magicrunes.data.repositories.runesInCombination

import com.magicrunes.magicrunes.data.entities.cache.RunesInCombinationDbEntity

interface IRunesInCombinationRepository {
    suspend fun getAll(): List<RunesInCombinationDbEntity>
}