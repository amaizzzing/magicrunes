package com.magicrunes.magicrunes.domain.interactors.currentfortunedescriptioninteractor

import com.magicrunes.magicrunes.ui.models.CurrentFortuneDescriptionModel

interface ICurrentFortuneDescriptionInteractor {
    suspend fun getCurrentFortuneRunes(historyDate: Long): List<CurrentFortuneDescriptionModel>

    suspend fun getDescriptionId(historyDate: Long): Long?
}