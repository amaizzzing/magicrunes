package com.magicrunes.magicrunes.utils.animationUtils

import android.graphics.PointF
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class ArcMetric {
    var mRadius = 0f
    var mStartPoint = PointF()
    var mEndPoint = PointF()
    var mMidPoint = PointF()
    var mAxisPoint = arrayOfNulls<PointF>(2)

    var mZeroPoint = PointF()
    var mStartEndSegment = 0f
    var mMidAxisSegment = 0f
    var mZeroStartSegment = 0f

    var mAnimationDegree = 0f
    var mSideDegree = 0f
    var mZeroStartDegree = 0f

    var startDegree = 0f

    var endDegree = 0f

    var mSide: Side? = null

    companion object {
        fun evaluate(
            startX: Float, startY: Float,
            endX: Float, endY: Float,
            degree: Float, side: Side?
        ): ArcMetric {
            val arcMetric = ArcMetric()
            arcMetric.mStartPoint[startX] = startY
            arcMetric.mEndPoint[endX] = endY
            arcMetric.setDegree(degree)
            arcMetric.mSide = side
            arcMetric.createAxisVariables()
            arcMetric.calcStartEndSeg()
            arcMetric.calcRadius()
            arcMetric.calcMidAxisSeg()
            arcMetric.calcMidPoint()
            arcMetric.calcAxisPoints()
            arcMetric.calcZeroPoint()
            arcMetric.calcDegrees()
            return arcMetric
        }
    }

    private fun createAxisVariables() {
        for (i in mAxisPoint.indices) mAxisPoint[i] = PointF()
    }

    private fun calcStartEndSeg() {
        mStartEndSegment =
            sqrt(
                (mStartPoint.x - mEndPoint.x).toDouble().pow(2.0) +
                        (mStartPoint.y - mEndPoint.y).toDouble().pow(2.0)
            ).toFloat()
    }

    private fun calcRadius() {
        mSideDegree = (180 - mAnimationDegree) / 2
        mRadius = mStartEndSegment / ArcUtils.sin(mAnimationDegree.toDouble()) * ArcUtils.sin(
            mSideDegree.toDouble()
        )
    }

    private fun calcMidAxisSeg() {
        mMidAxisSegment = mRadius * ArcUtils.sin(mSideDegree.toDouble())
    }

    private fun calcMidPoint() {
        mMidPoint.x =
            mStartPoint.x + mStartEndSegment / 2 * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
        mMidPoint.y =
            mStartPoint.y + mStartEndSegment / 2 * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
    }

    private fun calcAxisPoints() {
        if (mStartPoint.y > mEndPoint.y || mStartPoint.y == mEndPoint.y) {
            mAxisPoint[0]!!.x =
                mMidPoint.x + mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
            mAxisPoint[0]!!.y =
                mMidPoint.y - mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
            mAxisPoint[1]!!.x =
                mMidPoint.x - mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
            mAxisPoint[1]!!.y =
                mMidPoint.y + mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
        } else {
            mAxisPoint[0]!!.x =
                mMidPoint.x - mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
            mAxisPoint[0]!!.y =
                mMidPoint.y + mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
            mAxisPoint[1]!!.x =
                mMidPoint.x + mMidAxisSegment * (mEndPoint.y - mStartPoint.y) / mStartEndSegment
            mAxisPoint[1]!!.y =
                mMidPoint.y - mMidAxisSegment * (mEndPoint.x - mStartPoint.x) / mStartEndSegment
        }
    }

    private fun calcZeroPoint() {
        mSide?.let {
            when (it) {
                Side.RIGHT -> {
                    mZeroPoint.x = mAxisPoint[Side.RIGHT.value]!!.x + mRadius
                    mZeroPoint.y = mAxisPoint[Side.RIGHT.value]!!.y
                }
                Side.LEFT -> {
                    mZeroPoint.x = mAxisPoint[Side.LEFT.value]!!.x - mRadius
                    mZeroPoint.y = mAxisPoint[Side.LEFT.value]!!.y
                }
            }
        }
    }

    private fun calcDegrees() {
        mZeroStartSegment =
            sqrt(
                (mZeroPoint.x - mStartPoint.x).toDouble().pow(2.0) +
                        (mZeroPoint.y - mStartPoint.y).toDouble().pow(2.0)
            ).toFloat()
        mZeroStartDegree = ArcUtils.acos(
            (2 * mRadius.toDouble().pow(2.0) - mZeroStartSegment.toDouble().pow(2.0)) / (2 * mRadius.toDouble().pow(2.0))
        )
        mSide?.let {
            when (it) {
                Side.RIGHT -> if (mStartPoint.y <= mZeroPoint.y) {
                    if (mStartPoint.y > mEndPoint.y ||
                        mStartPoint.y == mEndPoint.y && mStartPoint.x > mEndPoint.x
                    ) {
                        startDegree = mZeroStartDegree
                        endDegree = startDegree + mAnimationDegree
                    } else {
                        startDegree = mZeroStartDegree
                        endDegree = startDegree - mAnimationDegree
                    }
                } else if (mStartPoint.y >= mZeroPoint.y) {
                    if (mStartPoint.y < mEndPoint.y ||
                        mStartPoint.y == mEndPoint.y && mStartPoint.x > mEndPoint.x
                    ) {
                        startDegree = 0 - mZeroStartDegree
                        endDegree = startDegree - mAnimationDegree
                    } else {
                        startDegree = 0 - mZeroStartDegree
                        endDegree = startDegree + mAnimationDegree
                    }
                }
                Side.LEFT -> if (mStartPoint.y <= mZeroPoint.y) {
                    if (mStartPoint.y > mEndPoint.y ||
                        mStartPoint.y == mEndPoint.y && mStartPoint.x < mEndPoint.x
                    ) {
                        startDegree = 180 - mZeroStartDegree
                        endDegree = startDegree - mAnimationDegree
                    } else {
                        startDegree = 180 - mZeroStartDegree
                        endDegree = startDegree + mAnimationDegree
                    }
                } else if (mStartPoint.y >= mZeroPoint.y) {
                    if (mStartPoint.y < mEndPoint.y ||
                        mStartPoint.y == mEndPoint.y && mStartPoint.x < mEndPoint.x
                    ) {
                        startDegree = 180 + mZeroStartDegree
                        endDegree = startDegree + mAnimationDegree
                    } else {
                        startDegree = 180 + mZeroStartDegree
                        endDegree = startDegree - mAnimationDegree
                    }
                }
            }
        }

    }

    fun setDegree(degree: Float) {
        var dg = degree
        dg = abs(dg)
        when {
            dg > 180 -> setDegree(dg % 180)
            dg == 180f -> setDegree(dg - 1)
            dg < 30 -> setDegree(
                30f
            )
            else -> mAnimationDegree = dg
        }
    }

    val axisPoint: PointF?
        get() = mAxisPoint[mSide!!.value]

    fun getDegree(percentage: Float): Float {
        return startDegree + (endDegree - startDegree) * percentage
    }
}