package com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor

import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.ui.states.BaseState

interface IRuneOfTheDayInteractor {
    suspend fun createRuneOfTheDay(): BaseState

    suspend fun getRandomRuneOfTheDay(): BaseState

    suspend fun getRandomRunes(count: Int): List<RuneOfTheDayModel>

    suspend fun createSuccessState(rune: RuneDbEntity): BaseState.Success<RuneOfTheDayModel>

    suspend fun isNeedReverse(rune: RuneDbEntity): Boolean

    suspend fun createModel(rune: RuneDbEntity, isReverse: Boolean = false): RuneOfTheDayModel
    suspend fun createModel(rune: RuneDbEntity): RuneOfTheDayModel

    suspend fun getRuneFromHistory(historyId: Long): BaseState

    suspend fun isTodayRune(): Boolean
}