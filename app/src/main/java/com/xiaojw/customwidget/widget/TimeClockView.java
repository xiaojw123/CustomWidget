package com.xiaojw.customwidget.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.xiaojw.customwidget.R;
import com.xiaojw.customwidget.util.ScreenSize;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeClockView extends View {

    Paint mPaint, mTextPaint, mMinitePaint, mPointPaint;
    int mRadius, mTextColor;
    int mHCalibrationWidth = 35;//时针刻度长度
    int mMCalibrationWidth = 20;//分针刻度长度

    int offset = 2;
    Rect rect;
    Calendar timeC;
    TimerHandler handler;
    int hour, min, sec;


    private static final int MSG_TIME_UPDATE = 0x10;

    public static class TimerHandler extends Handler {

        WeakReference<TimeClockView> wr;

        private TimerHandler(WeakReference<TimeClockView> wr) {
            this.wr = wr;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_TIME_UPDATE) {
                TimeClockView view = wr.get();
                if (view != null) {
                    view.timeChange();
                    sendEmptyMessageDelayed(MSG_TIME_UPDATE, 1000);
                }
            }
        }
    }

    private void timeChange() {
        timeC.setTime(new Date());
        hour = timeC.get(Calendar.HOUR);
        min = timeC.get(Calendar.MINUTE);
        sec = timeC.get(Calendar.SECOND);
        invalidate();
    }


    public TimeClockView(Context context) {
        this(context, null);
    }

    public TimeClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimeClockView);
        mRadius = (int) ta.getDimension(R.styleable.TimeClockView_circle_raduis, 2);
        mTextColor = ta.getColor(R.styleable.TimeClockView_circle_color, Color.BLACK);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mTextColor);
        mPaint.setStrokeWidth(5f);
        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.STROKE);
        mPointPaint.setColor(mTextColor);
        mMinitePaint = new Paint(mPaint);
        mMinitePaint.setStrokeWidth(2f);
        mMinitePaint.setStyle(Paint.Style.FILL);
        mTextPaint = new Paint(mPaint);
        mTextPaint.setStrokeWidth(2f);
        mTextPaint.setTextSize(36f);
        mTextPaint.setStyle(Paint.Style.FILL);
        rect = new Rect();
        timeC = Calendar.getInstance();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.translate(ScreenSize.width / 2, ScreenSize.height / 2);
        canvas.drawCircle(0, 0, 10f, mMinitePaint);
        canvas.save();
        canvas.rotate(90);
        canvas.drawCircle(0, 0, mRadius, mPaint);
        for (int i = 1; i <= 12; i++) {
            canvas.drawLine(-mRadius + offset, 0, -mRadius + offset + mHCalibrationWidth, 0, mPaint);
            for (int j = 1; j <= 5; j++) {
                canvas.rotate(6);
                canvas.drawLine(-mRadius + offset, 0, -mRadius + offset + mMCalibrationWidth, 0, mMinitePaint);
            }
        }
        canvas.restore();
        for (int i = 1; i <= 12; i++) {
            double tempS = i * 30.0F * Math.PI / 180.0F;
            mTextPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), rect);
            int textHeight = rect.height();
            int disctance = mRadius - mHCalibrationWidth * 2 - rect.height();
            float x = (float) (disctance * Math.sin(tempS));
            float y = -(float) (disctance * Math.cos(tempS));
            canvas.drawText(String.valueOf(i), x, y + textHeight / 3, mTextPaint);
        }
        mPaint.setStyle(Paint.Style.FILL);
        int innerRadius = 5;
        int hourPointWidth = mRadius * 2 / 5;
        int minPointWidth = mRadius * 3 / 5;
        int secPointWidth = mRadius * 4 / 5;

        canvas.drawCircle(0, 0, innerRadius, mPaint);
        int code = timeC.get(Calendar.AM_PM);
        String codeStr = code == 0 ? "AM" : "PM";
        mTextPaint.getTextBounds(codeStr, 0, codeStr.length(), rect);
        canvas.drawText(codeStr, -rect.width() / 2, -(innerRadius + 5), mTextPaint);
        canvas.rotate(-90);
        canvas.save();
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setStrokeWidth(4);
        float hDegrees = hour * 30.0F;
        canvas.rotate(hDegrees);
        canvas.drawLine(innerRadius, 2, hourPointWidth, 2, mPointPaint);
        canvas.restore();
        canvas.save();
        mPointPaint.setStrokeWidth(2);
        float mDegrees = min * 6;
        canvas.rotate(mDegrees);
        canvas.drawLine(innerRadius, 1, minPointWidth, 1, mPointPaint);
        canvas.restore();
        canvas.save();
        mPointPaint.setColor(Color.RED);
        float sDegrees = sec * 6;
        canvas.rotate(sDegrees);
        canvas.drawLine(innerRadius, 1, secPointWidth, 1, mPointPaint);
        canvas.restore();
    }

    @Override
    protected void onVisibilityChanged(@NotNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (handler == null) {
            handler = new TimerHandler(new WeakReference<>(this));
        }
        handler.removeMessages(MSG_TIME_UPDATE);
        if (visibility == View.VISIBLE) {
            handler.sendEmptyMessage(MSG_TIME_UPDATE);
        }
    }


}
