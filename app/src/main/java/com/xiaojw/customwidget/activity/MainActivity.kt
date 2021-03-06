package com.xiaojw.customwidget.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.xiaojw.customwidget.R
import com.xiaojw.customwidget.util.ScreenSize
class MainActivity : BaseActivity() {



    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
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
        startActivity(Intent(this@MainActivity, TimeCActivity::class.java))
    }

    fun gotoWaveHeart(view: View){
        startActivity(Intent(this@MainActivity, HeartFloatActivity::class.java))
    }
    fun gotoHeartImg(view:View){
        startActivity(Intent(this@MainActivity, NonRectImgActivity::class.java))
    }
    fun gotoLayoutManger(view:View){
        startActivity(Intent(this@MainActivity,LayoutManagerActivity::class.java))

    }

    fun gotoCirleRecycler(view: View) {
        startActivity(Intent(this@MainActivity,CircleRecyclerActivity::class.java))


    }

    fun gotoBmp(view: View) {
        startActivity(Intent(this@MainActivity,BmpActivity::class.java))

    }
}
