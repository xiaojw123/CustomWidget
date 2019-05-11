package com.xiaojw.customwidget.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.xiaojw.customwidget.R
import com.xiaojw.customwidget.util.ScreenSize

import java.lang.ref.WeakReference
import java.util.Calendar
import java.util.Date

class TimeClockView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    lateinit internal var mPaint: Paint
    lateinit internal var mTextPaint: Paint
    lateinit internal var mMinitePaint: Paint
    lateinit internal var mPointPaint: Paint
    internal var mRadius: Int = 0
    internal var mTextColor: Int = 0
    internal var mHCalibrationWidth = 35//时针刻度长度
    internal var mMCalibrationWidth = 20//分针刻度长度

    internal var offset = 2
    lateinit internal var rect: Rect
    lateinit internal var timeC: Calendar
    internal var handler: TimerHandler? = null
    internal var hour: Int = 0
    internal var min: Int = 0
    internal var sec: Int = 0

    class TimerHandler(internal var wr: WeakReference<TimeClockView>) : Handler() {

        override fun handleMessage(msg: Message) {
            if (msg.what == MSG_TIME_UPDATE) {
                val view = wr.get()
                if (view != null) {
                    view.timeChange()
                    sendEmptyMessageDelayed(MSG_TIME_UPDATE, 1000)
                }
            }
        }
    }

    private fun timeChange() {
        timeC.time = Date()
        hour = timeC.get(Calendar.HOUR)
        min = timeC.get(Calendar.MINUTE)
        sec = timeC.get(Calendar.SECOND)
        invalidate()
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TimeClockView)
        mRadius = ta.getDimension(R.styleable.TimeClockView_circle_raduis, 2f).toInt()
        mTextColor = ta.getColor(R.styleable.TimeClockView_circle_color, Color.BLACK)
        mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.color = mTextColor
        mPaint.strokeWidth = 5f
        mPointPaint = Paint()
        mPointPaint.style = Paint.Style.STROKE
        mPointPaint.color = mTextColor
        mMinitePaint = Paint(mPaint)
        mMinitePaint.strokeWidth = 2f
        mMinitePaint.style = Paint.Style.FILL
        mTextPaint = Paint(mPaint)
        mTextPaint.strokeWidth = 2f
        mTextPaint.textSize = 36f
        mTextPaint.style = Paint.Style.FILL
        rect = Rect()
        timeC = Calendar.getInstance()
    }


    override fun onDraw(canvas: Canvas) {
        mPaint.style = Paint.Style.STROKE
        canvas.translate((ScreenSize.width / 2).toFloat(), (ScreenSize.height / 2).toFloat())
        canvas.drawCircle(0f, 0f, 10f, mMinitePaint)
        canvas.save()
        canvas.rotate(90f)
        canvas.drawCircle(0f, 0f, mRadius.toFloat(), mPaint)
        for (i in 1..12) {
            canvas.drawLine(
                (-mRadius + offset).toFloat(),
                0f,
                (-mRadius + offset + mHCalibrationWidth).toFloat(),
                0f,
                mPaint
            )
            for (j in 1..5) {
                canvas.rotate(6f)
                canvas.drawLine(
                    (-mRadius + offset).toFloat(),
                    0f,
                    (-mRadius + offset + mMCalibrationWidth).toFloat(),
                    0f,
                    mMinitePaint
                )
            }
        }
        canvas.restore()
        for (i in 1..12) {
            val tempS = i.toDouble() * 30.0 * Math.PI / 180.0f
            mTextPaint.getTextBounds(i.toString(), 0, i.toString().length, rect)
            val textHeight = rect.height()
            val disctance = mRadius - mHCalibrationWidth * 2 - rect.height()
            val x = (disctance * Math.sin(tempS)).toFloat()
            val y = -(disctance * Math.cos(tempS)).toFloat()
            canvas.drawText(i.toString(), x, y + textHeight / 3, mTextPaint)
        }
        mPaint.style = Paint.Style.FILL
        val innerRadius = 5
        val hourPointWidth = mRadius * 2 / 5
        val minPointWidth = mRadius * 3 / 5
        val secPointWidth = mRadius * 4 / 5

        canvas.drawCircle(0f, 0f, innerRadius.toFloat(), mPaint)
        val code = timeC.get(Calendar.AM_PM)
        val codeStr = if (code == 0) "AM" else "PM"
        mTextPaint.getTextBounds(codeStr, 0, codeStr.length, rect)
        canvas.drawText(codeStr, (-rect.width() / 2).toFloat(), (-(innerRadius + 5)).toFloat(), mTextPaint)
        canvas.rotate(-90f)
        canvas.save()
        mPointPaint.color = Color.BLACK
        mPointPaint.strokeWidth = 4f
        val hDegrees = hour * 30.0f
        canvas.rotate(hDegrees)
        canvas.drawLine(innerRadius.toFloat(), 2f, hourPointWidth.toFloat(), 2f, mPointPaint)
        canvas.restore()
        canvas.save()
        mPointPaint.strokeWidth = 2f
        val mDegrees = (min * 6).toFloat()
        canvas.rotate(mDegrees)
        canvas.drawLine(innerRadius.toFloat(), 1f, minPointWidth.toFloat(), 1f, mPointPaint)
        canvas.restore()
        canvas.save()
        mPointPaint.color = Color.RED
        val sDegrees = (sec * 6).toFloat()
        canvas.rotate(sDegrees)
        canvas.drawLine(innerRadius.toFloat(), 1f, secPointWidth.toFloat(), 1f, mPointPaint)
        canvas.restore()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (handler == null) {
            handler = TimerHandler(WeakReference(this))
        }
        handler!!.removeMessages(MSG_TIME_UPDATE)
        if (visibility == View.VISIBLE) {
            handler!!.sendEmptyMessage(MSG_TIME_UPDATE)
        }
    }

    companion object {


        private val MSG_TIME_UPDATE = 0x10
    }


}
