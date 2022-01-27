package com.magicrunes.magicrunes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.magicrunes.magicrunes.domain.interactors.currentfortuneinteractor.ICurrentFortuneInteractor
import com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies.FortuneFactory
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.ui.states.BaseState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import javax.inject.Inject

class CurrentFortuneViewModel @Inject constructor(
    private val fortuneFactory: FortuneFactory,
    private val currentFortuneInteractor: ICurrentFortuneInteractor
) : BaseViewModel<BaseState>() {
    private val currentFortuneRunes = mutableListOf<RuneOfTheDayModel>()

    fun getCurrentFortuneStrategy(idFortune: Long) {
        setData(BaseState.Loading)
        viewModelScope.launch(bgDispatcher) {
            val strategyFortune = fortuneFactory.createFortune(idFortune)
            withContext(viewModelScope.coroutineContext) {
                setData(BaseState.Success(strategyFortune))
            }
        }
    }

    suspend fun getRandomRunes(count: Int): List<RuneOfTheDayModel> {
        val randomRunes = currentFortuneInteractor.getRandomRunes(count)

        currentFortuneRunes.addAll(randomRunes)

        return randomRunes
    }

    suspend fun updateHistoryFortune(idFortune: Long): Long? {
        currentFortuneInteractor.updateLastDateFortune(idFortune, DateTime().millis)
        val fortuneInHistory = currentFortuneInteractor.updateHistoryFortune(idFortune, currentFortuneRunes)

        return fortuneInHistory?.date
    }
}