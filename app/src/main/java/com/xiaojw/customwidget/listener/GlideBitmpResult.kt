package com.xiaojw.customwidget.listener

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiaojw.customwidget.util.APPLOG

abstract class GlideBitmpResult<R>:RequestListener<R>{

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<R>?,
        isFirstResource: Boolean
    ): Boolean {

        APPLOG.printDebug("onFailed__")
        return false
    }

    override fun onResourceReady(
        resource: R,
        model: Any?,
        target: Target<R>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        APPLOG.printDebug("onSuccess__resouse__"+resource)
        onResourceReady(resource)
        return true
    }

    abstract fun onResourceReady(resouse: R)

}