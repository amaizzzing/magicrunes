package com.magicrunes.magicrunes.domain.interactors.currentfortunedescriptioninteractor

import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.FortuneHistoryRunesFactory
import com.magicrunes.magicrunes.data.repositories.historyFortune.HistoryFortuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.ui.models.CurrentFortuneDescriptionModel
import com.magicrunes.magicrunes.utils.toBoolean

class CurrentFortuneDescriptionInteractor(
    private val fortuneHistoryRunesFactory: FortuneHistoryRunesFactory,
    private val historyFortuneRepositoryFactory: HistoryFortuneRepositoryFactory,
    private val runeRepository: IRuneRepository,
    private val runeDescriptionRepository: IRuneDescriptionRepository
): ICurrentFortuneDescriptionInteractor {
    override suspend fun getCurrentFortuneRunes(historyDate: Long): List<CurrentFortuneDescriptionModel> {
        val resultList = mutableListOf<CurrentFortuneDescriptionModel>()

        val idFortune = historyFortuneRepositoryFactory.getFortuneRepository().getFortuneHistoryByDate(historyDate)?.idFortune
        fortuneHistoryRunesFactory.getFortuneRepository().getRunesByHistoryDate(historyDate).forEach { runeInFortune -> //idHistory == historyDate
            val rune = runeRepository.getRuneById(runeInFortune.idRune)
            val runeDescription = runeDescriptionRepository.getRuneById(runeInFortune.idRune)

            rune?.let {
                resultList.add(
                    CurrentFortuneDescriptionModel(
                        idRune = rune.id,
                        idHistory = historyDate,
                        idFortune = idFortune ?: 0L,
                        name = rune.name,
                        description = rune.mainDescription,
                        avrevDescription =
                            (if (runeInFortune.state.toBoolean()) runeDescription?.avDescription else runeDescription?.revDescription) ?: "",
                        image = rune.imageLink.ifEmpty { rune.localImageName },
                        isReverse = runeInFortune.state.toBoolean()
                    )
                )
            }
        }

        return resultList
    }

    override suspend fun getDescriptionId(historyDate: Long): Long? =
        historyFortuneRepositoryFactory.getFortuneRepository().getFortuneHistoryByDate(historyDate)?.idFortune
}
