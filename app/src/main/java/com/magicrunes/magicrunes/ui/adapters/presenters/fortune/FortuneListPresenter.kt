package com.magicrunes.magicrunes.ui.adapters.presenters.fortune

import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.ui.adapters.items.FortuneItemView
import com.magicrunes.magicrunes.ui.models.lists.FortuneModel
import javax.inject.Inject

class FortuneListPresenter: IFortuneListPresenter {
    @Inject
    lateinit var imageService: ImageService

    init {
        MagicRunesApp.appComponent.inject(this)
    }

    private var fortuneList = mutableListOf<FortuneModel>()

    override fun setList(list: List<FortuneModel>) {
        fortuneList.clear()
        fortuneList.addAll(list)
    }

    override fun getList(): List<FortuneModel> = fortuneList

    override var favouriteClickListener: ((pos: Int) -> Unit)? = null
    override var itemClickListener: ((FortuneItemView) -> Unit)? = null
    override var modelClickListener: ((pos: Int) -> Unit)? = null
    override var descriptionClickListener: ((pos: Int) -> Unit)? = null

    override fun bindView(view: FortuneItemView) {
        val fortune = fortuneList[view.pos]

        view.setFortuneName(fortune.nameFortune)
        view.setCountRunes(fortune.currentStrategy?.visibleRuneList?.size.toString())
        view.setDate(fortune.date)
        view.setFortuneImage(fortune.image)
        view.setFavouriteImage(
            if (fortune.isFavourite)
                getFavouriteImage()
            else
                getNotFavouriteImage()
        )
    }

    override fun getCount(): Int = fortuneList.size

    override fun getFavouriteImage() = imageService.getImageResource("favourite")

    override fun getNotFavouriteImage() = imageService.getImageResource("favourite_dis")
}