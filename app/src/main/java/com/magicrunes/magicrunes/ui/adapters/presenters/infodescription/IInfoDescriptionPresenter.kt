package com.magicrunes.magicrunes.ui.adapters.presenters.infodescription

import com.magicrunes.magicrunes.ui.adapters.items.InfoDescriptionItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.IListPresenter

interface IInfoDescriptionPresenter: IListPresenter<InfoDescriptionItemView> {
    fun setList(list: List<String>)

    fun getList(): List<String>
}