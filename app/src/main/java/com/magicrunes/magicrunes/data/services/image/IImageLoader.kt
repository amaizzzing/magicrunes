package com.magicrunes.magicrunes.data.services.image

import android.graphics.Bitmap

interface IImageLoader<T> {
    fun loadInto(source: Int, container: T)

    fun loadInto(source: Bitmap, container: T)
}