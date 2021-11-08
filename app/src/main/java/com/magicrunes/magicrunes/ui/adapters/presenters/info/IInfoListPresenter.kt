package com.magicrunes.magicrunes.ui.adapters.presenters.info

import com.magicrunes.magicrunes.ui.adapters.items.InfoItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.IListPresenter
import com.magicrunes.magicrunes.ui.models.lists.InfoRuneModel

interface IInfoListPresenter: IListPresenter<InfoItemView> {
    fun setList(list: List<InfoRuneModel>)

    fun getList(): List<InfoRuneModel>
}