package com.xiaojw.customwidget.util

import android.util.Log

class APPLOG {

    fun printDebug(message: String) {
        Log.d(TAG, message)

    }

    companion object {
        private val TAG = "customwidget"
    }

}
