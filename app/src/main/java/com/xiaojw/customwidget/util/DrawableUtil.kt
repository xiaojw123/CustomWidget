package com.xiaojw.customwidget.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class DrawableUtil {


    companion object {
        fun load(context: Context, url: String, imgView: ImageView) {
            Glide.with(context).load(url).into(imgView)
        }

    }

}