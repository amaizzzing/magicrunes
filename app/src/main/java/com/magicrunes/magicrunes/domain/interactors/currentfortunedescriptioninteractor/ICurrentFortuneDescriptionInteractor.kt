package com.magicrunes.magicrunes.domain.interactors.currentfortunedescriptioninteractor

import com.magicrunes.magicrunes.ui.models.CurrentFortuneDescriptionModel

interface ICurrentFortuneDescriptionInteractor {
    suspend fun getCurrentFortuneRunes(idHistory: Long): List<CurrentFortuneDescriptionModel>
}