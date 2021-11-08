package com.magicrunes.magicrunes.ui.adapters.presenters.infodescription

import com.magicrunes.magicrunes.ui.adapters.items.InfoDescriptionItemView

class InfoDescriptionPresenter: IInfoDescriptionPresenter {
    private var descriptionList = mutableListOf<String>()

    override fun setList(list: List<String>) {
        descriptionList.clear()
        descriptionList.addAll(list)
    }

    override fun getList(): List<String> = descriptionList

    override var itemClickListener: ((InfoDescriptionItemView) -> Unit)? = null

    override fun bindView(view: InfoDescriptionItemView) {
        val description = descriptionList[view.pos]

        view.setDescription(description)
    }

    override fun getCount(): Int = descriptionList.size
}