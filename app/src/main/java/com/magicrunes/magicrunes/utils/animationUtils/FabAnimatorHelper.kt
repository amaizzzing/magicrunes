package com.magicrunes.magicrunes.utils.animationUtils

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class FabAnimatorHelper(
    var fab: FloatingActionButton? = null,
    var startWidth: Float = fab?.width?.toFloat() ?: 0f,
    var endWidth: Float = -1f,
    var startHeight: Float = fab?.height?.toFloat() ?: 0f,
    var endHeight: Float = -1f,
    var startX: Float = fab?.x  ?: 0f,
    var startY: Float = fab?.y  ?: 0f,
    var endX: Float = -1f,
    var endY: Float = -1f,
    var isArcPath: Boolean = false,
    var startAlpha: Float = 0f,
    var endAlpha: Float = 1f,
    var startScale: Float = 1f,
    var endScale: Float = 1f,
    var duration: Long = 300L,
    var interpolator: TimeInterpolator = AccelerateDecelerateInterpolator()
) {
    fun setFabButton(fab: FloatingActionButton) {
        this.fab = fab
    }

    private var progress: Float = 0f
        set(value) {
            field = value

            if (!isArcPath) {
                fab?.let { fab ->
                    if (endWidth >= 0) fab.layoutParams.width = getProgressValue(startWidth, endWidth, progress).toInt()
                    if (endHeight >= 0) fab.layoutParams.height = getProgressValue(startHeight, endHeight, progress).toInt()
                    if (endWidth >= 0 || endHeight >= 0) fab.requestLayout()

                    if (endX >= 0) fab.x = getProgressValue(startX, endX, progress)
                    if (endY >= 0) fab.y = getProgressValue(startY, endY, progress)
                }
            } else {
                val arcMetric = ArcMetric.evaluate(startX, startY, endX, endY, 90f, Side.LEFT)
                val degree = arcMetric.getDegree(progress).toDouble()
                fab?.let { fab ->
                    fab.x = arcMetric.axisPoint?.x!! + arcMetric.mRadius * ArcUtils.cos(degree)
                    fab.y = arcMetric.axisPoint?.y!! - arcMetric.mRadius * ArcUtils.sin(degree)
                }
            }
            fab?.let { fab ->
                fab.alpha = getProgressValue(startAlpha, endAlpha, progress)
                fab.scaleX = getProgressValue(startScale, endScale, progress)
                fab.scaleY = getProgressValue(startScale, endScale, progress)
            }
        }

    fun getAnimator(
        forward: Boolean = true,
        updateListener: ((progress: Float) -> Unit)? = null
    ): ValueAnimator {
        val a =
            if (forward) ValueAnimator.ofFloat(0f, 1f)
            else ValueAnimator.ofFloat(1f, 0f)
        a.addUpdateListener {
            val progress = (it.animatedValue as Float)
            this.progress = progress
            updateListener?.invoke(progress)
        }
        a.duration = duration
        a.interpolator = interpolator
        return a
    }

    private fun getProgressValue(start: Float, end: Float, progress: Float): Float =
        start + (end - start) * progress

}