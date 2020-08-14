package com.xiaojw.customwidget.activity

import android.view.MotionEvent
import com.xiaojw.customwidget.R
import com.xiaojw.customwidget.widget.HeartFloatView

class HeartFloatActivity : BaseActivity() {
    private lateinit var heartFloatView: HeartFloatView


    override fun getLayoutRes(): Int {
        return R.layout.activity_wave_heart
    }

    override fun initView() {
        heartFloatView = findViewById(R.id.float_heartview);
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            heartFloatView.addHeart()
        }
        return super.onTouchEvent(event)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            heartFloatView.addHeart()
        }
    }

}
