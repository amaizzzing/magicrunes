package com.magicrunes.magicrunes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.magicrunes.magicrunes.data.enums.HistoryType
import com.magicrunes.magicrunes.domain.interactors.historyInteractor.IHistoryInteractor
import com.magicrunes.magicrunes.ui.states.BaseState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryFragmentViewModel @Inject constructor(
    private val historyInteractor: IHistoryInteractor
) : BaseViewModel<BaseState>() {
    fun getHistory(type: HistoryType){
        setData(BaseState.Loading)

        viewModelScope.launch(bgDispatcher) {
            val resultList = historyInteractor.getHistory(type)
            withContext(viewModelScope.coroutineContext) {
                setData(resultList)
            }
        }
    }
}