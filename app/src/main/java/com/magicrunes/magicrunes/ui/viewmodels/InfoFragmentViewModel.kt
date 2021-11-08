package com.magicrunes.magicrunes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.magicrunes.magicrunes.domain.interactors.runeInfoInteractor.IRuneInfoInteractor
import com.magicrunes.magicrunes.ui.states.BaseState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InfoFragmentViewModel @Inject constructor(
    private val runeInfoInteractor: IRuneInfoInteractor
) : BaseViewModel<BaseState>() {
    fun getRunesList() {
        setData(BaseState.Loading)
        viewModelScope.launch(bgDispatcher) {
            val resultList = runeInfoInteractor.getRuneList()
            withContext(viewModelScope.coroutineContext) {
                setData(resultList)
            }
        }
    }
}