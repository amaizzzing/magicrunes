package com.magicrunes.magicrunes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magicrunes.magicrunes.databinding.InfoDescriptionItemBinding
import com.magicrunes.magicrunes.ui.adapters.items.InfoDescriptionItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.infodescription.IInfoDescriptionPresenter

class InfoDescriptionViewPagerAdapter(
    val presenter: IInfoDescriptionPresenter
): RecyclerView.Adapter<InfoDescriptionViewPagerAdapter.PagerVH>() {
    inner class PagerVH(
        private val binding: InfoDescriptionItemBinding
    ): RecyclerView.ViewHolder(binding.root), InfoDescriptionItemView {
        override fun setDescription(description: String) {
            binding.descriptionRune.text = description
        }

        override var pos: Int = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(
            InfoDescriptionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: InfoDescriptionViewPagerAdapter.PagerVH, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount(): Int = presenter.getCount()
}