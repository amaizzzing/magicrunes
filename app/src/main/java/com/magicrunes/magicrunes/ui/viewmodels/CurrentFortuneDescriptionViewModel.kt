package com.magicrunes.magicrunes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.magicrunes.magicrunes.domain.interactors.currentfortunedescriptioninteractor.ICurrentFortuneDescriptionInteractor
import com.magicrunes.magicrunes.ui.states.BaseState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentFortuneDescriptionViewModel@Inject constructor(
    private val currentFortuneDescriptionInteractor: ICurrentFortuneDescriptionInteractor
): BaseViewModel<BaseState>() {
    fun getCurrentDescription(historyId: Long) {
        setData(BaseState.Loading)
        viewModelScope.launch(bgDispatcher) {
            val fortuneRunes = currentFortuneDescriptionInteractor.getCurrentFortuneRunes(historyId)
            withContext(viewModelScope.coroutineContext) {
                setData(BaseState.Success(fortuneRunes))
            }
        }
    }
}