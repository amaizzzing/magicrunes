package com.magicrunes.magicrunes.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.magicrunes.magicrunes.ui.models.lists.FortuneModel

class FortuneDiffUtilCallback(
    private val oldList: List<FortuneModel>,
    private val newList: List< FortuneModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].nameFortune == newList[newItemPosition].nameFortune

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].isFavourite == newList[newItemPosition].isFavourite

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}