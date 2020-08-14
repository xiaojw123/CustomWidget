package com.xiaojw.customwidget.activity

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.xiaojw.customwidget.R
import com.xiaojw.customwidget.util.APPLOG
import com.xiaojw.customwidget.util.CommonUtil
import kotlinx.android.synthetic.main.activity_bmp.*
import java.text.SimpleDateFormat
import java.util.*

class BmpActivity : BaseActivity() {



    val BMP_URL="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1589779241260&di=352c4ab0a13133303766c0790c575fa9&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D3571592872%2C3353494284%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1200%26h%3D1290"

    override fun getLayoutRes(): Int {
        return R.layout.activity_bmp
    }

    @SuppressLint("CheckResult")
    override fun initView() {
        Glide.with(this).asBitmap().load(BMP_URL).into(object :CustomTarget<Bitmap>(){
            override fun onLoadCleared(placeholder: Drawable?) {
                APPLOG.printDebug("load clear__")
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                APPLOG.printDebug("resbmp__"+resource)
                mark_img.setImageBitmap(getMarkBitmap(resource))
            }

        })

    }


    //图片加水印
    fun  getMarkBitmap(resouse:Bitmap):Bitmap{
        val w=resouse.width
        val h=resouse.height
        val newBmp=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        val cv=Canvas(newBmp)
        cv.drawBitmap(resouse,0f,0f,null)

        val font=Typeface.create("宋体",Typeface.NORMAL)
        val textPaint=TextPaint()
        textPaint.color=Color.WHITE
        textPaint.typeface=font
        textPaint.alpha=200;
        val sdf=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val mask=sdf.format(System.currentTimeMillis())
        val fontSize=CommonUtil.dip2px(55f,this)
        textPaint.setTextSize(fontSize.toFloat());
        val rect=Rect()
        textPaint.getTextBounds(mask,0,mask.length,rect)
        cv.drawText(mask, ((w - rect.width()-textPaint.textSize)/2), (h-rect.height()-textPaint.textSize)/2, textPaint);
        return newBmp
    }
}


