package com.magicrunes.magicrunes.domain.interactors.runeInfoInteractor

import com.magicrunes.magicrunes.ui.states.BaseState

interface IRuneInfoInteractor {
    suspend fun getRuneList(): BaseState

    suspend fun getRuneById(id: Long): BaseState
}