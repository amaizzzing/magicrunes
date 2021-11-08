package com.magicrunes.magicrunes.ui.customView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

@SuppressLint("AppCompatCustomView")
class RuneImageView: ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?,defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(width: Int, height: Int) {
        super.onMeasure(width, height)
        setMeasuredDimension((measuredHeight*0.7093).toInt(), measuredHeight)
    }
}