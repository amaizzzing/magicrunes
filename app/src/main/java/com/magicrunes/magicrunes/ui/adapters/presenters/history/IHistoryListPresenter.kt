package com.magicrunes.magicrunes.ui.adapters.presenters.history

import com.magicrunes.magicrunes.ui.adapters.items.HistoryItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.IListPresenter
import com.magicrunes.magicrunes.ui.models.lists.HistoryModel

interface IHistoryListPresenter: IListPresenter<HistoryItemView> {
    fun setList(list: List<HistoryModel>)

    fun getList(): List<HistoryModel>

    var commentClickListener:((HistoryItemView) -> Unit)?
}