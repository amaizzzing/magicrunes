package com.magicrunes.magicrunes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.magicrunes.magicrunes.domain.interactors.fortuneinteractor.IFortuneInteractor
import com.magicrunes.magicrunes.ui.states.BaseState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FortuneFragmentViewModel @Inject constructor(
    private val fortuneInteractor: IFortuneInteractor
) : BaseViewModel<BaseState>() {
    fun getFortuneList(){
        setData(BaseState.Loading)

        viewModelScope.launch(bgDispatcher) {
            val resultList = fortuneInteractor.getFortuneList()
            withContext(viewModelScope.coroutineContext) {
                setData(resultList)
            }
        }
    }

    fun updateFavouriteFortune(id: Long, state: Boolean) {
        viewModelScope.launch(bgDispatcher) {
            fortuneInteractor.updateFavouriteFortune(id, state)
        }
    }
}