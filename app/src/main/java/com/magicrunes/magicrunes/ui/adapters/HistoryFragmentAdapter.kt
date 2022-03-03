package com.magicrunes.magicrunes.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.databinding.HistoryItemBinding
import com.magicrunes.magicrunes.ui.adapters.items.HistoryItemView
import com.magicrunes.magicrunes.ui.adapters.presenters.history.IHistoryListPresenter
import javax.inject.Inject

class HistoryFragmentAdapter(
    private val presenter: IHistoryListPresenter
): RecyclerView.Adapter<HistoryFragmentAdapter.ViewHolder>() {
    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    @Inject
    lateinit var imageService: ImageService

    inner class ViewHolder(
        val binding: HistoryItemBinding
    ): RecyclerView.ViewHolder(binding.root), HistoryItemView {
        override fun setRuneName(runeName: String) {
            binding.titleHistoryRune.text = runeName
        }

        override fun setDescription(description: String) {
            binding.textHistoryRune.text = description
        }

        override fun setRuneImage(runeName: String, isReverse: Boolean) {
            imageLoader.loadInto(
                imageService.getImageResource(runeName),
                binding.imageHistoryRune,
                16
            )
            binding.imageHistoryRune.rotation =
                if (isReverse) 180f else 0f
        }

        override fun setDate(date: String) {
           binding.dateHistoryItem.text = date
        }

        override fun setCommentImage(commentImage: Int?) {
            commentImage?.let {
                binding.commentHistoryItem.visibility = View.VISIBLE
                imageLoader.loadInto(
                    commentImage,
                    binding.commentHistoryItem
                )
            } ?: run {
                binding.commentHistoryItem.visibility = View.GONE
            }
        }

        override var pos: Int = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryFragmentAdapter.ViewHolder =
        ViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
            binding.commentHistoryItem.setOnClickListener {
                presenter.commentClickListener?.invoke(this)
            }
        }

    override fun getItemCount(): Int = presenter.getCount()
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })
}