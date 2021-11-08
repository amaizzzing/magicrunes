package com.magicrunes.magicrunes.ui.adapters.presenters.history

import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.data.enums.HistoryType
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.ui.adapters.items.HistoryItemView
import com.magicrunes.magicrunes.ui.models.lists.HistoryModel
import com.magicrunes.magicrunes.utils.toBoolean
import javax.inject.Inject

class HistoryListPresenter: IHistoryListPresenter {
    @Inject
    lateinit var imageService: ImageService

    init {
        MagicRunesApp.appComponent.inject(this)
    }

    private var historyList = mutableListOf<HistoryModel>()

    override fun setList(list: List<HistoryModel>) {
        historyList.clear()
        historyList.addAll(list)
    }

    override fun getList(): List<HistoryModel> = historyList

    override var commentClickListener: ((HistoryItemView) -> Unit)? = null
    override var itemClickListener: ((HistoryItemView) -> Unit)? = null

    override fun bindView(view: HistoryItemView) {
        val historyRune = historyList[view.pos]

        view.setRuneName(historyRune.name)
        view.setDescription(
            when{
                historyRune.avDescription.isEmpty() && historyRune.revDescription.isEmpty() ->
                    historyRune.mainDescription

                historyRune.state.toBoolean() -> historyRune.revDescription

                !historyRune.state.toBoolean() -> historyRune.avDescription

                else -> historyRune.mainDescription
            }
        )
        view.setRuneImage(historyRune.image, historyRune.state.toBoolean())
        if (historyRune.historyType == HistoryType.RuneType) {
            view.setCommentImage(
                imageService.getImageResource(
                    if (historyRune.comment.isEmpty()) {
                        "ic_comment_dis"
                    } else {
                        "ic_comment"
                    }
                )
            )
        } else {
            view.setCommentImage(null)
        }

        view.setDate(historyRune.date)
    }

    override fun getCount(): Int = historyList.size
}