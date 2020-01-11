package com.xiaojw.customwidget.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.xiaojw.customwidget.R
import com.xiaojw.customwidget.util.CommonUtil
import com.xiaojw.customwidget.util.DrawableUtil
import com.xiaojw.customwidget.widget.rv.CircleRecyclerView
import com.xiaojw.customwidget.widget.rv.CircularViewMode

import kotlinx.android.synthetic.main.activity_circle_recycler.*

class CircleRecyclerActivity : BaseActivity(){

    var IMG_DATA= arrayListOf("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579176121&di=e0bcfb9f209e6f06ff2cc0f7910ce1af&imgtype=jpg&er=1&src=http%3A%2F%2Fimgbdb2.bendibao.com%2Fshbdb%2F201811%2F27%2F20181127183246_88041.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578581417235&di=ddfae82cc3faed6bb2dc3d52c309b311&imgtype=0&src=http%3A%2F%2Fpic.rmb.bdstatic.com%2Fe907d419d3a60b304c85eec786589e5a.jpeg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578581477825&di=49460bd8a4514cd75f782ed58367a7ab&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fent%2Ftransform%2F712%2Fw630h882%2F20191217%2Fbff0-ikvenft4570830.jpg",
        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3424984643,4050694920&fm=26&gp=0.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579176232&di=7d88ac5ee8a324caeccd01bbda193c45&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.hebnews.cn%2F2019-02%2F08%2Fe0e8e6b7-61b1-4bd3-a44a-30814c83b3e6.jpg",
        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2226030789,3225572379&fm=26&gp=0.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579176298&di=067960d58263c896ed308e4095f51eb9&imgtype=jpg&er=1&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_mini%2Cc_zoom%2Cw_640%2Fimages%2F20170913%2Fcdf15937c76041d08115278e1d231a1f.jpeg",
        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=883229944,808284171&fm=26&gp=0.jpg")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_recycler)
        cr_menu_rlv.layoutManager = LinearLayoutManager(this)
        cr_menu_rlv.setViewMode(CircularViewMode())
        cr_menu_rlv.setNeedLoop(true)
        cr_menu_rlv.setNeedCenterForce(true)
        cr_menu_rlv.setOnCenterItemClickListener(object:CircleRecyclerView.OnCenterItemClickListener{
            override fun onCenterItemClick(view: View?) {
                cr_menu_rlv.visibility=View.GONE
                val url= view?.tag as String
                DrawableUtil.load(view.context,url,cr_bg_img)

            }


        })
        cr_menu_rlv.adapter=CircleRecylcerAdapter(IMG_DATA)


    }


    inner class CircleRecylcerAdapter(data: List<String>) :
        RecyclerView.Adapter<CircleViewHodler>() {


        var mData: List<String>

        init {
            mData = data
        }


        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CircleViewHodler {

            val  container = FrameLayout(this@CircleRecyclerActivity)
            val params1 =RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            container.setLayoutParams(params1);
            val pad = CommonUtil.dip2px(5f, this@CircleRecyclerActivity);
            container.setPadding(0, pad, 0, pad);
            val item_tv =  TextView(this@CircleRecyclerActivity);
            val w = CommonUtil.dip2px(84f, this@CircleRecyclerActivity);
            val h = CommonUtil.dip2px(34f, this@CircleRecyclerActivity);
            val params2 =  FrameLayout.LayoutParams(w, h);
            item_tv.setLayoutParams(params2)
            item_tv.setTextColor(Color.GREEN)
            item_tv.textSize=22f
            container.addView(item_tv)
            return CircleViewHodler(container)
        }

        override fun getItemCount(): Int {
            return CircleRecyclerView.DEFAULT_SELECTION * 2;
        }

        override fun onBindViewHolder(p0: CircleViewHodler, p1: Int) {
            val index=p1 %mData.size
            val imgUrl=mData[p1 %mData.size]
            p0.itemView.setTag(imgUrl)
            p0.itemTv.setText("海报"+index)
//            DrawableUtil.load(this@CircleRecyclerActivity,imgUrl , p0.itemImg)



        }

    }

    inner class CircleViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView){

        var itemTv:TextView
        init {
            itemTv=(itemView as ViewGroup).getChildAt(0) as TextView
        }

    }

    fun ctrlMenu(view: View) {
        if (cr_menu_rlv.visibility==View.VISIBLE){
            cr_menu_rlv.visibility=View.GONE
        }else{
            cr_menu_rlv.visibility=View.VISIBLE
        }

    }


}
