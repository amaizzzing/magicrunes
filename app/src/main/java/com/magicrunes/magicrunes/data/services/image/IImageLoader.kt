package com.magicrunes.magicrunes.data.services.image

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.view.MenuItem

interface IImageLoader<T> {
    fun loadInto(source: Int, container: T)

    fun loadInto(source: Bitmap, container: T)

    fun loadInto(url: String, container: T,  roundCorners: Int = 0, circleCrop: Boolean = false)

    fun loadInto(url: String, container: MenuItem, context: Context, res: Resources)

    fun loadInto(source: Int, container: MenuItem, context: Context, res: Resources)
}