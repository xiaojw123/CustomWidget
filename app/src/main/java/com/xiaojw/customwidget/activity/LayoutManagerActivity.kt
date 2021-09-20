package com.xiaojw.customwidget.activity

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.xiaojw.customwidget.R
import com.xiaojw.customwidget.constants.dataList
import com.xiaojw.customwidget.util.APPLOG
import com.xiaojw.customwidget.util.DrawableUtil
import com.xiaojw.customwidget.widget.lm.CardLayoutManager
import com.xiaojw.customwidget.widget.lm.EchelonLayoutManager
import com.xiaojw.customwidget.widget.lm.LinearLayoutManager


class LayoutManagerActivity : BaseActivity() {



    override fun getLayoutRes(): Int {
        return R.layout.activity_layout_manager
    }

    override fun initView() {
        val recylerView: RecyclerView = findViewById(R.id.lm_recyclerview)
        recylerView.layoutManager = CardLayoutManager()
        recylerView.adapter = DataAdapter()
    }


    inner class DataAdapter : RecyclerView.Adapter<DataHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DataHolder {
            APPLOG.printDebug("createViewhoder")
            return DataHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_lm_card,p0,false))
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(p0: DataHolder, p1: Int) {
            APPLOG.printDebug("bindViewHodler:"+p1)
            DrawableUtil.load(
                this@LayoutManagerActivity,
                dataList.get(p1),
                p0.card_img
            )
        }
    }

    class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       lateinit  var card_img:ImageView
        init {
            card_img=itemView.findViewById(R.id.item_card_img)
        }
    }


}
