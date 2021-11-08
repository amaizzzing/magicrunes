package com.magicrunes.magicrunes.utils.animationUtils

import android.view.View

object ArcUtils {
    fun sin(degree: Double): Float {
        return kotlin.math.sin(Math.toRadians(degree)).toFloat()
    }

    fun cos(degree: Double): Float {
        return kotlin.math.cos(Math.toRadians(degree)).toFloat()
    }

    fun asin(value: Double): Float {
        return Math.toDegrees(kotlin.math.asin(value)).toFloat()
    }

    fun acos(value: Double): Float {
        return Math.toDegrees(kotlin.math.acos(value)).toFloat()
    }

    fun centerX(view: View): Float {
        return view.x + view.width / 2f
    }

    fun centerY(view: View): Float {
        return view.y + view.height / 2f
    }
}