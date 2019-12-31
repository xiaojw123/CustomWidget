package com.xiaojw.customwidget.widget

import android.animation.*
import android.content.Context
import android.graphics.PointF
import android.os.Handler
import android.util.AttributeSet
import android.widget.RelativeLayout

import java.util.Random

class HeartFloatView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private var mContext: Context? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    var mHanlder: Handler


    init {
        mContext = context
        mHanlder = Handler()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
    }


    fun addHeart() {
        val heartView = WaveHeartView(mContext!!)
        addView(heartView)
        val heartWidth = heartView.measuredWidth
        val heartHegiht = heartView.measuredHeight
        //        PointF
        val p0 = PointF((mWidth / 2).toFloat(), mHeight.toFloat())
        val p1 = PointF(Random().nextInt(mWidth).toFloat(), (mHeight / 2 + Random().nextInt(mHeight / 2)).toFloat())
        val p2 = PointF(Random().nextInt(mWidth).toFloat(), (mHeight / 2 - Random().nextInt(mHeight / 2)).toFloat())
        val p3 = PointF(Random().nextInt(mWidth).toFloat(), 0f)
        val bizAnimator = ValueAnimator.ofObject(FlapEvaluator(p1, p2), p0, p3)
        bizAnimator.duration = 3000
        bizAnimator.addUpdateListener { animation ->
            val point = animation.animatedValue as PointF
            val x = Math.max(point.x - heartWidth, 0f)
            val y = Math.max(point.y - heartHegiht, 0f)
            heartView.x = x
            heartView.y = y
            heartView.alpha = 1 - animation.animatedFraction
        }
//        val alphaAnimtoer = ObjectAnimator.ofFloat(heartView, "alpha", 0.1f, 1f)
//        val scalexAnimtoer = ObjectAnimator.ofFloat(heartView, "scaleX", 0.1f, 1f)
//        val scalceYAnimtoer = ObjectAnimator.ofFloat(heartView, "scaleY", 0.1f, 1f)
//        val set = AnimatorSet()
//        set.duration = 2000
//        set.playTogether(alphaAnimtoer, scalexAnimtoer, scalceYAnimtoer, scalceYAnimtoer)
//        val set2 = AnimatorSet()
//        set2.playSequentially(set, bizAnimator)
        bizAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                mHanlder.post(object : Runnable {
                    override fun run() {
                        removeView(heartView)
                    }
                })
            }
        })
        bizAnimator.start()
    }

    internal inner class FlapEvaluator internal constructor(var p1: PointF, var p2: PointF) : TypeEvaluator<PointF> {

        override fun evaluate(fraction: Float, p0: PointF, p3: PointF): PointF {
            val x =
                p0.x * (1 - fraction) * (1 - fraction) * (1 - fraction) + p1.x * (1 - fraction) * (1 - fraction) + p2.x * (1 - fraction) + p3.x * fraction * fraction * fraction
            val y =
                p0.y * (1 - fraction) * (1 - fraction) * (1 - fraction) + p1.y * (1 - fraction) * (1 - fraction) + p2.y * (1 - fraction) + p3.y * fraction * fraction * fraction
            return PointF(x, y)
        }
    }

}
