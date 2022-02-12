package com.magicrunes.magicrunes.ui.adapters.presenters.fortune

import com.magicrunes.magicrunes.ui.adapters.items.FortuneItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.IListPresenter
import com.magicrunes.magicrunes.ui.models.lists.FortuneModel

interface IFortuneListPresenter: IListPresenter<FortuneItemView> {
    fun setList(list: List<FortuneModel>)

    fun getList(): List<FortuneModel>

    fun getFavouriteImage(): Int

    fun getNotFavouriteImage(): Int

    var favouriteClickListener:((pos: Int) -> Unit)?

    var descriptionClickListener:((pos: Int) -> Unit)?

    var modelClickListener:((pos: Int) -> Unit)?
}