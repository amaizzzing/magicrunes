package com.magicrunes.magicrunes.ui.adapters.presenters.viewpager

import com.magicrunes.magicrunes.ui.adapters.items.IViewPagerItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.IListPresenter
import com.magicrunes.magicrunes.ui.models.CurrentFortuneDescriptionModel

interface IViewPagerPresenter: IListPresenter<IViewPagerItemView> {
    fun setList(list: List<CurrentFortuneDescriptionModel>)

    fun getList(): List<CurrentFortuneDescriptionModel>
}