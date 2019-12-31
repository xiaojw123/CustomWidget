package com.xiaojw.customwidget.util

import android.util.Log

class APPLOG {



    companion object {
        private val TAG = "customwidget"
        fun printDebug(message: String) {
            Log.d(TAG, message)

        }
    }

}
