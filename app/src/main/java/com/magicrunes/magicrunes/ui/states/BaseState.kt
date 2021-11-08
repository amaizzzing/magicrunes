package com.magicrunes.magicrunes.ui.states

sealed class BaseState {
    data class Success<out T>(val resultData: T) : BaseState()
    class Error(val error: Throwable) : BaseState()
    object Loading : BaseState()
}