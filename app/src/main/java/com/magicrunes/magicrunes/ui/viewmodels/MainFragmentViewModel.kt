package com.magicrunes.magicrunes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.magicrunes.magicrunes.domain.interactors.runeOfTheDayInteractor.IRuneOfTheDayInteractor
import com.magicrunes.magicrunes.ui.states.BaseState
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val runeOfTheDayInteractor: IRuneOfTheDayInteractor
): BaseViewModel<BaseState>() {
    private var currentJob: Job? = null

    fun getRuneOfTheDay() = viewModelScope.launch {
        cancelShowJob(this.coroutineContext.job)

        setData(BaseState.Loading)
        withContext(bgDispatcher) {
            val resultRune = runeOfTheDayInteractor.createRuneOfTheDay()
            withContext(viewModelScope.coroutineContext) {
                setData(resultRune)
            }
        }
    }

    fun getRuneDescription(historyDate: Long) = viewModelScope.launch {
        cancelShowJob(this.coroutineContext.job)

        setData(BaseState.Loading)
        withContext(bgDispatcher) {
            val resultRune = runeOfTheDayInteractor.getRuneFromHistory(historyDate)
            withContext(viewModelScope.coroutineContext) {
                setData(resultRune)
            }
        }
    }

    private fun cancelShowJob(newJob: Job) {
        if (currentJob != null) {
            currentJob?.cancel()
        }
        currentJob = newJob
    }
}