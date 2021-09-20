package com.xiaojw.customwidget.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import com.xiaojw.customwidget.R
import java.io.ByteArrayOutputStream
import kotlin.random.Random

class WaveHeartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var mPaint: Paint
    var path: Path
    var dst: Path
    var pm: PathMeasure
    var animatorValue = 0f
    var colorRes = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.GRAY)


    constructor(context: Context) : this(context, null)

    init {
        mPaint = Paint();
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3f
        path = Path()
        val startX = 300f
        val startY = startX
        path.moveTo(startX, startY)
        path.cubicTo(startX - 170, startY - 150, startX - 150, startY - 300, startX, startY - 200);
        path.cubicTo(startX + 150, startY - 300, startX + 170, startY - 150, startX, startY)
        pm = PathMeasure()
        pm.setPath(path, true)
        dst = Path();
        val va = ValueAnimator.ofFloat(0f, 1f);
        va.duration = 5000
        va.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                animatorValue = animation?.getAnimatedValue() as Float
                invalidate()
            }

        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(500,500)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
//        dst.reset()
//        dst.lineTo(0f,0f)
//        val stopLen=pm.length*animatorValue
//        pm.getSegment(0f,stopLen,dst,true)
//        canvas?.drawPath(path, mPaint)
        mPaint.color = colorRes[Random.nextInt(3)]
        canvas?.drawPath(path, mPaint)
        val bos = ByteArrayOutputStream();
        val soureDrawable = resources.getDrawable(R.drawable.avater2) as BitmapDrawable
        val bmp = soureDrawable.bitmap
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
        val data = bos.toByteArray()
//        val brd = BitmapRegionDecoder.newInstance(data, 0, data.size, false)
//        val rect = Rect(100, 50, 600, 500)
//        val bmp = brd.decodeRegion(rect, null)
//        canvas?.drawBitmap()
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avater2, options)
        var inSize = 1
        var bmpWidth = options.outWidth
        var bmpHiehgt = options.outHeight
        while (bmpWidth / measuredWidth > 0 || bmpHiehgt / measuredHeight > 0) {
            inSize *= 2
            bmpWidth/=inSize
            bmpHiehgt/=inSize
        }
        options.inSampleSize=inSize
        options.inJustDecodeBounds = false
        val dstBmp=BitmapFactory.decodeResource(resources, R.drawable.avater2, options)
        canvas?.clipPath(path)
        canvas?.drawBitmap(dstBmp,0f,0f,mPaint)
//        soureDrawable.setBounds(0, 0, 500, 500)
//        soureDrawable.draw(canvas)


    }


}