package com.magicrunes.magicrunes.domain.interactors.currentfortuneinteractor

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity
import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity
import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.IFortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.historyFortune.IHistoryFortuneRepository
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.utils.toBoolean

class CurrentFortuneInteractor(
    private val runeRepository: IRuneRepository,
    private val historyFortuneRepository: IHistoryFortuneRepository,
    private val fortuneRepository: IFortuneRepository,
    private val runeDescriptionRepository: IRuneDescriptionRepository,
    private val fortuneHistoryRunesRepository: IFortuneHistoryRunesRepository
): ICurrentFortuneInteractor {
    override suspend fun getRandomRunes(count: Int): List<RuneOfTheDayModel> =
        runeRepository.getRandomRunes(count).map { createModel(it) }

    override suspend fun updateHistoryFortune(idFortune: Long) {
        historyFortuneRepository.updateHistoryFortune(idFortune)
    }

    override suspend fun updateLastDateFortune(id: Long, date: Long) {
        fortuneRepository.updateLastDate(id, date)
    }

    override suspend fun insertFortuneRune(fortuneRune: FortuneHistoryRunesDbEntity) {
        fortuneHistoryRunesRepository.insertFortuneRune(fortuneRune)
    }

    override suspend fun getLastInHistory(): HistoryFortuneDbEntity? =
        historyFortuneRepository.getLastInHistory()

    private suspend fun isNeedReverse(rune: RuneDbEntity): Boolean {
        val runeDescription = runeDescriptionRepository.getRuneById(rune.id)
        val mayReverse = runeDescription?.revDescription?.isNotEmpty() ?: false

        return if (mayReverse){
            (0..1).random().toBoolean()
        } else {
            false
        }
    }

    private suspend fun createModel(rune: RuneDbEntity): RuneOfTheDayModel {
        val needReverse = isNeedReverse(rune)
        val runeDescription = runeDescriptionRepository.getRuneById(rune.id)
        val avrevDescription =
            (if (needReverse) runeDescription?.revDescription else runeDescription?.avDescription) ?: ""

        return RuneOfTheDayModel(
            rune,
            needReverse,
            avrevDescription
        )
    }
}