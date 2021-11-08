package com.magicrunes.magicrunes.ui.adapters.presenters.viewpager

import com.magicrunes.magicrunes.data.services.resource.IResourceService
import com.magicrunes.magicrunes.ui.adapters.items.IViewPagerItemView
import com.magicrunes.magicrunes.ui.models.CurrentFortuneDescriptionModel

class ViewPagerPresenter(
    private val resourceService: IResourceService
): IViewPagerPresenter {
    private val fortuneRuneList = mutableListOf<CurrentFortuneDescriptionModel>()

    override fun setList(list: List<CurrentFortuneDescriptionModel>) {
        fortuneRuneList.clear()
        fortuneRuneList.addAll(list)
    }

    override fun getList(): List<CurrentFortuneDescriptionModel> = fortuneRuneList

    override fun bindView(view: IViewPagerItemView) {
        val runeDescription = fortuneRuneList[view.pos]

        view.setNameFortuneRune(runeDescription.name)
        view.setDescription(runeDescription.description)
        view.setAvRevName(resourceService.getReverseName(runeDescription.isReverse))
    }

    override var itemClickListener: ((IViewPagerItemView) -> Unit)? = null

    override fun getCount(): Int = fortuneRuneList.size
}