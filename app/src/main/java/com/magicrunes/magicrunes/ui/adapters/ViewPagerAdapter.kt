package com.magicrunes.magicrunes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magicrunes.magicrunes.databinding.CurrentFortuneDescriptionItemBinding
import com.magicrunes.magicrunes.ui.adapters.items.IViewPagerItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.viewpager.IViewPagerPresenter

class ViewPagerAdapter(
    private val presenter: IViewPagerPresenter
): RecyclerView.Adapter<ViewPagerAdapter.PagerVH>() {
    inner class PagerVH(
        private val binding: CurrentFortuneDescriptionItemBinding
    ): RecyclerView.ViewHolder(binding.root), IViewPagerItemView {
        override fun setDescription(description: String) {
            binding.descriptionFortuneRune.text = description
        }

        override fun setNameFortuneRune(name: String) {
            binding.nameFortuneRune.text = name
        }

        override fun setAvRevName(avrevName: String) {
            binding.avrevNameFortuneRune.text = avrevName
        }

        override var pos: Int = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(
            CurrentFortuneDescriptionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PagerVH, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount(): Int = presenter.getCount()
}