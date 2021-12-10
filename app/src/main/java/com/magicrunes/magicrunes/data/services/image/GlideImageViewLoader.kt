package com.magicrunes.magicrunes.data.services.image

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


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

    override fun loadInto(url: String, container: ImageView, roundCorners: Int, circleCrop: Boolean) {
        if (circleCrop) {
            Glide.with(container.context)
                .load(url)
                .circleCrop()
                .into(container)
        } else {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(roundCorners))

            Glide.with(container.context)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .into(container)
        }
    }

    override fun loadInto(source: Int, container: MenuItem, context: Context, res: Resources) {
        Glide.with(context)
            .asBitmap()
            .load(source)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    container.icon =
                        BitmapDrawable(
                            res, Bitmap.createScaledBitmap(
                                resource, 150, 150, true
                            )
                        )
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    override fun loadInto(url: String, container: MenuItem, context: Context, res: Resources) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(20))

        Glide.with(context)
            .asBitmap()
            .load(url)
            .apply(requestOptions)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    container.icon =
                        BitmapDrawable(
                            res, Bitmap.createScaledBitmap(
                                resource, 150, 150, true
                            )
                        )
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }
}