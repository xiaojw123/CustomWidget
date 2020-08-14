package com.xiaojw.customwidget.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.xiaojw.customwidget.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        immersionBar {
            statusBarColor(android.R.color.transparent)
            navigationBarColor(android.R.color.transparent)
        }
        initView()

    }

    abstract fun getLayoutRes(): Int
    abstract fun initView()
}
