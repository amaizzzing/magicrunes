package com.magicrunes.magicrunes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magicrunes.magicrunes.MagicRunesApp

abstract class BaseViewModel<T>: ViewModel() {
    val bgDispatcher = MagicRunesApp.backgroundTaskDispatcher

    private val _data = MutableLiveData<T?>()

    val data: LiveData<T?> = _data

    fun setData(newData: T) {
        _data.value = newData
    }
}