package com.magicrunes.magicrunes.ui.adapters.presenters.info

import com.magicrunes.magicrunes.ui.adapters.items.InfoItemView
import com.magicrunes.magicrunes.ui.models.lists.InfoRuneModel

class InfoListPresenter: IInfoListPresenter {
    private var runeList = mutableListOf<InfoRuneModel>()

    override var itemClickListener: ((InfoItemView) -> Unit)? = null

    override fun bindView(view: InfoItemView) {
        val rune = runeList[view.pos]

        view.setRuneName(rune.runeName)

        view.setDescription(rune.mainDescription)

        rune.imageName?.let {
            view.setRuneImage(it)
        }
    }

    override fun getList(): List<InfoRuneModel> = runeList

    override fun getCount(): Int = runeList.size

    override fun setList(list: List<InfoRuneModel>) {
        runeList.clear()
        runeList.addAll(list)
    }
}