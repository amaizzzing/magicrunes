package com.magicrunes.magicrunes.domain.interactors.fortuneinteractor

import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.HistoryFortuneRepositoryFactory
import com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies.FortuneFactory
import com.magicrunes.magicrunes.ui.models.lists.FortuneModel
import com.magicrunes.magicrunes.ui.states.BaseState

class FortuneInteractor(
    private val fortuneRepository: IFortuneRepository,
    private val historyFortuneRepositoryFactory: HistoryFortuneRepositoryFactory,
    private val fortuneFactory: FortuneFactory
): IFortuneInteractor {
    override suspend fun getFortuneList(): BaseState {
        val resultList =
            fortuneRepository
                .getAllFortunes()
                .map {
                    val lastDate =
                        historyFortuneRepositoryFactory.getFortuneRepository().getLastInHistoryByIdFortune(it.id)?.date ?: 0L

                    FortuneModel(it, lastDate, fortuneFactory.createFortune(it.id))
                }
                .sortedBy { !it.isFavourite }

        return BaseState.Success(resultList)
    }

    override suspend fun updateFavouriteFortune(id: Long, state: Boolean) {
        fortuneRepository.updateFavouriteFortune(id, state)
    }
}