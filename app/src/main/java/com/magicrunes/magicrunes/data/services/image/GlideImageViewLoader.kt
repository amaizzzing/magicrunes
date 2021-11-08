package com.magicrunes.magicrunes.data.services.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageViewLoader(): IImageLoader<ImageView> {
    override fun loadInto(source: Int, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(source)
            .into(container)
    }

    override fun loadInto(source: Bitmap, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(source)
            .into(container)
    }
}