package com.magicrunes.magicrunes.domain.interactors.currentfortuneinteractor

import com.magicrunes.magicrunes.data.entities.cache.FortuneHistoryRunesDbEntity
import com.magicrunes.magicrunes.data.entities.cache.HistoryFortuneDbEntity
import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity
import com.magicrunes.magicrunes.data.repositories.fortune.IFortuneRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.FirestoreFortuneHistoryRunesRepository
import com.magicrunes.magicrunes.data.repositories.fortuneHistoryRunes.FortuneHistoryRunesFactory
import com.magicrunes.magicrunes.data.repositories.historyFortune.HistoryFortuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.utils.toBoolean
import com.magicrunes.magicrunes.utils.toInt
import org.joda.time.DateTime

class CurrentFortuneInteractor(
    private val runeRepository: IRuneRepository,
    private val historyFortuneRepositoryFactory: HistoryFortuneRepositoryFactory,
    private val fortuneRepository: IFortuneRepository,
    private val runeDescriptionRepository: IRuneDescriptionRepository,
    private val fortuneHistoryRunesFactory: FortuneHistoryRunesFactory
): ICurrentFortuneInteractor {
    override suspend fun getRandomRunes(count: Int): List<RuneOfTheDayModel> =
        runeRepository.getRandomRunes(count).map { createModel(it) }

    override suspend fun updateHistoryFortune(idFortune: Long, fortuneRunesList: List<RuneOfTheDayModel>): HistoryFortuneDbEntity? {
        val currentHistoryFortuneRepository = historyFortuneRepositoryFactory.getFortuneRepository()

        val date = DateTime().millis
        val fortuneHistoryRunes = fortuneRunesList.map {
            FortuneHistoryRunesDbEntity().apply {
                idHistory = date
                idRune = it.idRune
                state = it.isReverse.toInt()
            }
        }

        currentHistoryFortuneRepository.updateHistoryFortune(idFortune, date, fortuneHistoryRunes)

        if (fortuneHistoryRunesFactory.getFortuneRepository() !is FirestoreFortuneHistoryRunesRepository) {
            fortuneHistoryRunes.forEach { insertFortuneRune(it) } // в firestore вставили выше, а в рум нет...убрать эту проверку, сделать нормально
        }

        return currentHistoryFortuneRepository.getLastInHistory()
    }

    override suspend fun updateLastDateFortune(id: Long, date: Long) {
        fortuneRepository.updateLastDate(id, date)
    }

    override suspend fun insertFortuneRune(fortuneRune: FortuneHistoryRunesDbEntity) {
        fortuneHistoryRunesFactory.getFortuneRepository().insertFortuneRune(fortuneRune)
    }

    override suspend fun getLastInHistory(): HistoryFortuneDbEntity? =
        historyFortuneRepositoryFactory.getFortuneRepository().getLastInHistory()

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