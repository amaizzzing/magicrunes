package com.magicrunes.magicrunes.domain.interactors.currentfortuneinteractor

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel

interface ICurrentFortuneInteractor {
    suspend fun getRandomRunes(count: Int): List<RuneOfTheDayModel>

    suspend fun updateHistoryFortune(idFortune: Long)

    suspend fun updateLastDateFortune(id: Long, date: Long)

    suspend fun insertFortuneRune(fortuneRune: FortuneHistoryRunesDbEntity)

    suspend fun getLastInHistory(): HistoryFortuneDbEntity?
}