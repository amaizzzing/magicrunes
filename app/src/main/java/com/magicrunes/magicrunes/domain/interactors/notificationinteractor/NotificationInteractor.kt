package com.magicrunes.magicrunes.domain.interactors.notificationinteractor

import com.magicrunes.magicrunes.data.entities.cache.FortuneDbEntity
import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyRune.HistoryRuneRepositoryFactory
import com.magicrunes.magicrunes.ui.states.BaseState

class NotificationInteractor(
    private val historyRuneRepositoryFactory: HistoryRuneRepositoryFactory,
    private val fortuneRepository: IFortuneRepository
): INotificationInteractor {
    override suspend fun updateNotificationShow(isNotificationShow: Int, historyDate: Long) {
        historyRuneRepositoryFactory.getHistoryRuneRepository().updateNotificationShow(isNotificationShow, historyDate)
    }

    override suspend fun getRandomFortune(): BaseState.Success<FortuneDbEntity> =
        BaseState.Success(fortuneRepository.getAllFortunes().random())
}