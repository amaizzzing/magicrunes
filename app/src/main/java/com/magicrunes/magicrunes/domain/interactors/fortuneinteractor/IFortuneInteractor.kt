package com.magicrunes.magicrunes.domain.interactors.fortuneinteractor

import com.magicrunes.magicrunes.ui.states.BaseState

interface IFortuneInteractor {
    suspend fun getFortuneList(): BaseState

    suspend fun updateFavouriteFortune(id: Long, state: Boolean)
}