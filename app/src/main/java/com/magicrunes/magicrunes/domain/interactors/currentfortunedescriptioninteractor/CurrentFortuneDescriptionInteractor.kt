package com.magicrunes.magicrunes.domain.interactors.currentfortunedescriptioninteractor

import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.IFortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.IHistoryFortuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.ui.models.CurrentFortuneDescriptionModel
import com.magicrunes.magicrunes.utils.toBoolean

class CurrentFortuneDescriptionInteractor(
    private val fortuneHistoryRunesRepository: IFortuneHistoryRunesRepository,
    private val historyFortuneRepository: IHistoryFortuneRepository,
    private val runeRepository: IRuneRepository,
    private val runeDescriptionRepository: IRuneDescriptionRepository
): ICurrentFortuneDescriptionInteractor {
    override suspend fun getCurrentFortuneRunes(idHistory: Long): List<CurrentFortuneDescriptionModel> {
        val resultList = mutableListOf<CurrentFortuneDescriptionModel>()

        val idFortune = historyFortuneRepository.getFortuneHistoryById(idHistory)?.idFortune
        fortuneHistoryRunesRepository.getRunesByIdHistory(idHistory).forEach { runeInFortune ->
            val rune = runeRepository.getRuneById(runeInFortune.idRune)
            val runeDescription = runeDescriptionRepository.getRuneById(runeInFortune.idRune)

            rune?.let {
                resultList.add(
                    CurrentFortuneDescriptionModel(
                        idRune = rune.id,
                        idHistory = idHistory,
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
}
