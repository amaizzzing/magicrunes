package com.magicrunes.magicrunes.data.services.resource

import android.content.Context
import android.util.DisplayMetrics
import com.magicrunes.magicrunes.R

class ResourceService(val context: Context): IResourceService {
    override fun getTextFromResource(idResource: Int): String =
        context.getText(idResource).toString()

    override fun getReverseName(isReverse: Boolean): String =
        if (isReverse) {
            getTextFromResource(R.string.rev_state)
        } else {
            getTextFromResource(R.string.av_state)
        }

    override fun getDisplayMetrics(): DisplayMetrics =
        context.resources.displayMetrics

    override fun convertPixelsToDp(px: Float): Float =
        px / (getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

    override fun convertDpToPixel(dp: Float): Float =
        dp * (getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}