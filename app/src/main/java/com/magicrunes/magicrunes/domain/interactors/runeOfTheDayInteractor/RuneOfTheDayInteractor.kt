package com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor

import com.magicrunes.magicrunes.data.entities.cache.HistoryRuneDbEntity
import com.magicrunes.magicrunes.data.entities.cache.RuneDbEntity
import com.magicrunes.magicrunes.data.repositories.historyRune.HistoryRuneRepositoryFactory
import com.magicrunes.magicrunes.data.repositories.rune.IRuneRepository
import com.magicrunes.magicrunes.data.repositories.runeDescription.IRuneDescriptionRepository
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.utils.DateUtils
import com.magicrunes.magicrunes.utils.toBoolean
import com.magicrunes.magicrunes.utils.toInt
import org.joda.time.DateTime

class RuneOfTheDayInteractor(
    private val runeRepository: IRuneRepository,
    private val historyRuneRepositoryFactory: HistoryRuneRepositoryFactory,
    private val runeDescriptionRepository: IRuneDescriptionRepository
): IRuneOfTheDayInteractor {
    override suspend fun createRuneOfTheDay(): BaseState {
        return historyRuneRepositoryFactory.getHistoryRuneRepository().getLastRune()?.let { lastHistory ->
            if (DateUtils.isTheSameDay(DateTime(lastHistory.date), DateTime())) {
                runeRepository.getRuneById(lastHistory.idRune)?.let { runeOfTheDay ->
                    createSuccessState(runeOfTheDay)
                } ?: BaseState.Error(Error())
            } else {
                getRandomRuneOfTheDay()
            }
        } ?: getRandomRuneOfTheDay()
    }


    override suspend fun getRandomRuneOfTheDay(): BaseState =
        runeRepository.getRandomRune()?.let { randomRune ->
            val runeState = isNeedReverse(randomRune)
            historyRuneRepositoryFactory.getHistoryRuneRepository().insert(
                HistoryRuneDbEntity().apply {
                    date = DateTime().millis
                    idRune = randomRune.id
                    comment = ""
                    state = runeState.toInt()
                }
            )
            createSuccessState(randomRune)
        } ?: BaseState.Error(Error())

    override suspend fun getRandomRunes(count: Int): List<RuneOfTheDayModel> =
        runeRepository.getRandomRunes(count).map { createModel(it) }

    override suspend fun createModel(rune: RuneDbEntity): RuneOfTheDayModel =
        createModel(rune, isNeedReverse(rune))

    override suspend fun createModel(
        rune: RuneDbEntity,
        isReverse: Boolean
    ): RuneOfTheDayModel {
        val runeDescription = runeDescriptionRepository.getRuneById(rune.id)
        val avrevDescription =
            (if (isReverse) runeDescription?.revDescription else runeDescription?.avDescription) ?: ""

        return RuneOfTheDayModel(
            rune,
            isReverse,
            avrevDescription
        )
    }

    override suspend fun getRuneFromHistory(historyDate: Long): BaseState {
        val historyRune = historyRuneRepositoryFactory.getHistoryRuneRepository().getRuneByHistoryDate(historyDate)
        return historyRune?.let {
            val rune = runeRepository.getRuneById(it.idRune)
            rune?.let {
                val model = createModel(rune, historyRune.state.toBoolean())
                BaseState.Success(model)
            }
        } ?: BaseState.Error(Error())
    }

    override suspend fun createSuccessState(rune: RuneDbEntity): BaseState.Success<RuneOfTheDayModel> {
        return BaseState.Success(createModel(rune))
    }

    override suspend fun isNeedReverse(rune: RuneDbEntity): Boolean {
        val runeInHistory = historyRuneRepositoryFactory.getHistoryRuneRepository().getLastRune()
        val runeDescription = runeDescriptionRepository.getRuneById(rune.id)

        return if (rune.id != runeInHistory?.idRune) {
            val mayReverse =
                runeDescription?.revDescription?.isNotEmpty() ?: false

            if (mayReverse){
                (0..1).random().toBoolean()
            } else {
                false
            }
        } else {
            runeInHistory.state.toBoolean()
        }
    }

    override suspend fun isTodayRune(): Boolean =
        historyRuneRepositoryFactory.getHistoryRuneRepository().getLastRune()?.let { lastHistory ->
             (DateUtils.isTheSameDay(DateTime(lastHistory.date), DateTime()))
        } ?: false
}