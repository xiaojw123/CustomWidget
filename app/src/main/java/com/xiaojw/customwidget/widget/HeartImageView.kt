package com.xiaojw.customwidget.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView
import com.xiaojw.customwidget.R

class HeartImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    ImageView(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    var mWidth = 0.0f
    var mHeight = 0.0f
    lateinit var heartPath: Path
    lateinit var paint: Paint
    val CIRLE = 0
    val HEART = 1;
    var shape = CIRLE;

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.HeartImageView)
        shape = ta.getInt(R.styleable.HeartImageView_shape_style, CIRLE)

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat();
        mHeight = h.toFloat();
        paint = (drawable as BitmapDrawable).paint
        heartPath = Path()
        if (shape == CIRLE) {
            val raidus = Math.min(mWidth, mHeight) / 3
            heartPath.addCircle(mWidth / 2, mHeight / 2, raidus, Path.Direction.CCW)
        } else if (shape == HEART) {
            heartPath.moveTo(mWidth / 2f, mHeight / 4f);
            heartPath.cubicTo(
                mWidth / 10f, mHeight / 12f,
                mWidth / 9f, (mHeight * 3) / 5f,
                mWidth / 2f, (mHeight * 5) / 6f
            );
            heartPath.cubicTo(
                mWidth * 8 / 9f, (mHeight * 3) / 5f,
                mWidth * 9 / 10f, mHeight / 12f,
                mWidth / 2f, mHeight / 4f
            )
            //10 9 2
        }

    }


    override fun onDraw(canvas: Canvas?) {
        val saveCount: Int = canvas?.saveLayer(RectF(0f, 0f, mWidth, mHeight), null, Canvas.ALL_SAVE_FLAG)!!
        paint.style = Paint.Style.FILL
        canvas.drawPath(heartPath, paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        super.onDraw(canvas)
        paint.setXfermode(null)
        canvas.restoreToCount(saveCount)
//        canvas?.drawPath(heartPath,paint)
    }


}