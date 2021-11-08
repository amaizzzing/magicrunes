package com.magicrunes.magicrunes.ui.states

sealed class CurrentFortuneState {
    object BeforeShowing: CurrentFortuneState()
    object Showing: CurrentFortuneState()
    object ShowFortuneDescription: CurrentFortuneState()
}
