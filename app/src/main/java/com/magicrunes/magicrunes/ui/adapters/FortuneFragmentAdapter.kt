package com.magicrunes.magicrunes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.databinding.FortuneItemBinding
import com.magicrunes.magicrunes.ui.adapters.items.FortuneItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.fortune.IFortuneListPresenter
import com.magicrunes.magicrunes.R
import javax.inject.Inject

class FortuneFragmentAdapter (
    private val presenter: IFortuneListPresenter
): RecyclerView.Adapter<FortuneFragmentAdapter.ViewHolder>() {
    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    @Inject
    lateinit var imageService: ImageService

    inner class ViewHolder(
        val binding: FortuneItemBinding
    ): RecyclerView.ViewHolder(binding.root), FortuneItemView {

    override fun setFortuneName(fortuneName: String) {
            binding.fortuneNameItem.text = fortuneName
        }

        override fun setCountRunes(countRunes: String) {
            binding.fortuneCountRunes.text =
                binding.fortuneCountRunes.context.getString(R.string.count_runes, countRunes)
        }

        override fun setFortuneImage(fortuneName: String) {
            imageLoader.loadInto(
                imageService.getImageResource(fortuneName),
                binding.fortuneImageItem,
                24
            )
        }

        override fun setDate(date: String) {
            binding.dateFortuneItem.text = date
        }

        override fun setFavouriteImage(favouriteImage: Int) {
            imageLoader.loadInto(
                favouriteImage,
                binding.favFortuneItem
            )
        }

        override var pos: Int = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FortuneFragmentAdapter.ViewHolder =
        ViewHolder(
            FortuneItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.modelClickListener?.invoke(this.adapterPosition)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.favFortuneItem.setOnClickListener {
            presenter.favouriteClickListener?.invoke(holder.adapterPosition)
        }
        holder.binding.descriptionFortuneItem.setOnClickListener {
            presenter.descriptionClickListener?.invoke(holder.adapterPosition)
        }

        return presenter.bindView(holder.apply { pos = position })
    }


    override fun getItemCount(): Int = presenter.getCount()
}