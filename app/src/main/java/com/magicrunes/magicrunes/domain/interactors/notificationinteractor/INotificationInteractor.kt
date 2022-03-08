package com.magicrunes.magicrunes.domain.interactors.notificationinteractor

import com.magicrunes.magicrunes.data.entities.cache.FortuneDbEntity
import com.magicrunes.magicrunes.ui.states.BaseState

interface INotificationInteractor {
    suspend fun updateNotificationShow(isNotificationShow: Int, historyDate: Long)

    suspend fun getRandomFortune(): BaseState.Success<FortuneDbEntity>
}