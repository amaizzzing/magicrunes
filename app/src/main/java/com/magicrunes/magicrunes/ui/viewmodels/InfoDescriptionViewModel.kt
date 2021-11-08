package com.magicrunes.magicrunes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.magicrunes.magicrunes.domain.interactors.runeInfoInteractor.IRuneInfoInteractor
import com.magicrunes.magicrunes.ui.states.BaseState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InfoDescriptionViewModel @Inject constructor(
    private val runeInfoInteractor: IRuneInfoInteractor
) : BaseViewModel<BaseState>() {
    fun getRuneDescription(id: Long) {
        setData(BaseState.Loading)
        viewModelScope.launch(bgDispatcher) {
            val result = runeInfoInteractor.getRuneById(id)
            withContext(viewModelScope.coroutineContext) {
                setData(result)
            }
        }
    }
}