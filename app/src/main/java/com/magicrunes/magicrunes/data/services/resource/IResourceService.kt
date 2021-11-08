package com.magicrunes.magicrunes.data.services.resource

import android.util.DisplayMetrics

interface IResourceService {
    fun getTextFromResource(idResource: Int): String

    fun getReverseName(isReverse: Boolean): String

    fun getDisplayMetrics(): DisplayMetrics

    fun convertPixelsToDp(px: Float): Float

    fun convertDpToPixel(dp: Float): Float
}