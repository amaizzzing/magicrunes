package com.magicrunes.magicrunes.domain.interactors.historyInteractor

import com.magicrunes.magicrunes.data.enums.HistoryType
import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.HistoryFortuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.historyRune.HistoryRuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.ui.models.lists.HistoryModel
import com.magicrunes.magicrunes.ui.states.BaseState

class HistoryInteractor(
    private val runeRepository: IRuneRepository,
    private val runeDescriptionRepository: IRuneDescriptionRepository,
    private val historyRuneRepositoryFactory: HistoryRuneRepositoryFactory,
    private val fortuneRepository: IFortuneRepository,
    private val historyFortuneRepositoryFactory: HistoryFortuneRepositoryFactory
): IHistoryInteractor {
    override suspend fun getHistory(type: HistoryType): BaseState{
        val resultList = when(type) {
            HistoryType.FortuneType -> { getFortuneHistoryModelList() }
            HistoryType.RuneType -> { getHistoryRuneModelList() }
            HistoryType.AllHistory -> { getAllHistoryModelList()}
        }

        return BaseState.Success(resultList)
    }

    private suspend fun getFortuneHistoryModelList(): List<HistoryModel?> =
        historyFortuneRepositoryFactory.getFortuneRepository().getAllHistory().map { history ->
            fortuneRepository.getFortuneById(history.idFortune)?.let { fortune ->
                HistoryModel(fortune, history)
            }
        }.sortedByDescending { it?.dateInMillis }

    private suspend fun getHistoryRuneModelList(): List<HistoryModel?> =
        historyRuneRepositoryFactory.getHistoryRuneRepository().getAllHistory().map { history ->
            runeRepository.getRuneById(history.idRune)?.let { rune ->
                runeDescriptionRepository.getRuneById(history.idRune)?.let { description ->
                    HistoryModel(rune, description, history)
                }
            }
        }.sortedByDescending { it?.dateInMillis }

    private suspend fun getAllHistoryModelList(): List<HistoryModel?> =
        (getHistoryRuneModelList() + getFortuneHistoryModelList()).sortedByDescending { it?.dateInMillis }
}