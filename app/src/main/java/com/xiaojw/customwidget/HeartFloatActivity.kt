package com.xiaojw.customwidget

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.MotionEvent
import android.view.View
import com.xiaojw.customwidget.widget.HeartFloatView

class HeartFloatActivity : Activity() {
    lateinit var heartFloatView: HeartFloatView

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
