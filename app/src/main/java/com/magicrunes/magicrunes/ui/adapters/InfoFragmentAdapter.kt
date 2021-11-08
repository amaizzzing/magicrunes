package com.magicrunes.magicrunes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.databinding.ItemInfoBinding
import com.magicrunes.magicrunes.ui.adapters.items.InfoItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.info.IInfoListPresenter
import javax.inject.Inject

class InfoFragmentAdapter(
    private val presenter: IInfoListPresenter
): RecyclerView.Adapter<InfoFragmentAdapter.ViewHolder>() {
    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    @Inject
    lateinit var imageService: ImageService

    inner class ViewHolder(
        private val binding: ItemInfoBinding
     ): RecyclerView.ViewHolder(binding.root), InfoItemView {
        override fun setRuneName(runeName: String) {
            binding.titleItemInfo.text = runeName
        }

        override fun setDescription(description: String) {
            binding.textItemInfo.text = description
        }

        override fun setRuneImage(runeImage: String) {
            imageLoader.loadInto(
                imageService.getImageResource(runeImage),
                binding.imageItemInfo
            )
        }

        override var pos: Int = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount(): Int = presenter.getCount()
}