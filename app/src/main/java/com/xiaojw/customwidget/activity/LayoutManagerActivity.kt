package com.xiaojw.customwidget.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.xiaojw.customwidget.R
import com.xiaojw.customwidget.util.APPLOG
import com.xiaojw.customwidget.util.DrawableUtil
import com.xiaojw.customwidget.widget.lm.CardLayoutManager


class LayoutManagerActivity : BaseActivity() {

    var dataList = arrayListOf(
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575800953731&di=1f57608a2d00f2a4d40f5eca088af04a&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201807%2F21%2F20180721171556_zybxh.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575801273978&di=b94406cc23786474d71d0a090ea13626&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20170826%2Fb6fc1b92d3384f7f96a0e7a7e073d579.jpeg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575801317470&di=ac9655d4f86674ee33309a28e3d4506f&imgtype=0&src=http%3A%2F%2Fpic.38fan.com%2Fallimg%2F170331%2F60_170331160206_1.png",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577450120449&di=fafd83fbedb8f10388e1b4a40a9830ea&imgtype=0&src=http%3A%2F%2Fyouimg1.c-ctrip.com%2Ftarget%2F100q070000002hopt63C0.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577450170490&di=78e68188b570b842a5ef48e73b3e8a6a&imgtype=0&src=http%3A%2F%2Fhubei.sinaimg.cn%2F2012%2F0326%2FU7189P1190DT20120326095112.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577450239865&di=7c64bca83e44376435c1fc1efd079f45&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F86d6277f9e2f0708986f4382e524b899a801f2ce.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577768748961&di=498e1688eb74862e432d514f389d5e29&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20190617%2F356e979bca0e49bba22b84ae53974a63.jpeg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_manager)
        val recylerView: RecyclerView = findViewById(R.id.lm_recyclerview)
        recylerView.layoutManager = CardLayoutManager()
        recylerView.adapter = DataAdapter()

//        LinearLayoutManager

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
