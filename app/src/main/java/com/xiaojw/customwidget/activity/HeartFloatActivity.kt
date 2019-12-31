package com.xiaojw.customwidget.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import com.xiaojw.customwidget.R
import com.xiaojw.customwidget.widget.HeartFloatView

class HeartFloatActivity : BaseActivity() {
    lateinit var heartFloatView: HeartFloatView
    var mHanlder: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_heart)

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
