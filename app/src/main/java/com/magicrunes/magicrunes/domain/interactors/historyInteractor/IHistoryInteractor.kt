package com.magicrunes.magicrunes.domain.interactors.historyInteractor

import com.magicrunes.magicrunes.data.enums.HistoryType
import com.magicrunes.magicrunes.ui.states.BaseState

interface IHistoryInteractor {
    suspend fun getHistory(type: HistoryType): BaseState
}