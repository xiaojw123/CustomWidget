package com.xiaojw.customwidget

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.xiaojw.customwidget.util.ScreenSize

class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        ScreenSize.width = metrics.widthPixels
        ScreenSize.height = metrics.heightPixels
        val screen_tv: TextView = findViewById(R.id.main_screen_tv)
        screen_tv.setText("屏幕分辨率："+ScreenSize.width+"*"+ScreenSize.height)
    }

    fun gotoTimeClock(view: View) {
        startActivity(Intent(this@MainActivity,TimeCActivity::class.java))
    }
}
