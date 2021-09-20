package com.xiaojw.customwidget.widget.lm

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaojw.customwidget.util.APPLOG
import kotlin.math.abs

class LinearLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {


        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler == null || state == null) {
            return
        }

        val height = getTotalSpace()
        //计算水平空间
        var toalSpace = height
        //分离屏幕可见view放入缓存
        detachAndScrapAttachedViews(recycler)
        //填充view
        var index = 0
        while (toalSpace > 0 && index < state.itemCount) {
            val child = recycler.getViewForPosition(index)
            addView(child)
            measureChildWithMargins(child, 0, 0)
            val left = paddingLeft
            val right = left + child.measuredWidth
            val top = paddingTop + index * child.measuredHeight
            val bottom = top + child.measuredHeight
            layoutDecoratedWithMargins(
                child,
                left, top, right, bottom
            )
            toalSpace = height - bottom
            APPLOG.printDebug("bottom__" + bottom + ", topsapce__" + toalSpace)
            index++

        }


    }

    private fun getTotalSpace() = height - paddingTop - paddingBottom


    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
    }


    override fun canScrollVertically(): Boolean {
        return true
    }

    var mToalDy = 0;
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        APPLOG.printDebug("scroll veritcal by___"+dy)
        if (recycler == null || state == null) {
            return 0;
        }
        mToalDy += dy
        //计算水平空间

        val height = getTotalSpace() - mToalDy
        //计算水平空间
        var toalSpace = height
        APPLOG.printDebug("toalSpace__" + toalSpace)
        //分离屏幕可见view放入缓存
        detachAndScrapAttachedViews(recycler)
        //填充view
        var index = 0
        var childCont = 0
        while (toalSpace > 0 && index < state.itemCount) {
            val child = recycler.getViewForPosition(index)
            addView(child)
            measureChildWithMargins(child, 0, 0)
            val left = paddingLeft
            val right = left + child.measuredWidth
            val top = paddingTop + index * child.measuredHeight
            val bottom = top + child.measuredHeight
            layoutDecoratedWithMargins(
                child,
                left, top, right, bottom
            )

            if (childCont == 0 && bottom > getTotalSpace()) {

                childCont = index + 1;

            }

            toalSpace = height - bottom;
//            if (index == state.itemCount - 1) {
//                APPLOG.printDebug("ddd__" + bottom)
//                if (dy < 0 && bottom < getTotalSpace()) {
//                    mToalDy -= dy
//                    APPLOG.printDebug("最后一个")
//                }
//
//            }
//            if (index == 0) {
//                if (dy > 0 && top <= 0) {
//                    mToalDy -= dy
//                    APPLOG.printDebug("第一个")
//                }
//            }
//            APPLOG.printDebug("index___" + index + ", itemCouont__" + state.itemCount)

            index++


        }
        offsetChildrenVertical(mToalDy)
        APPLOG.printDebug("childCOunt))"+childCont)
        val currentPos=index-1;
        for (i in 0..currentPos) {
            val view = recycler.getViewForPosition(i)
            if (i <currentPos-childCont-1) {
                removeAndRecycleView(view, recycler)
            }
            APPLOG.printDebug("las visible botoom: " + view.bottom + "  , y:" + view.y)
//            val loc= intArrayOf(0,0)
//            view.getLocationInWindow(loc!!)
//            APPLOG.printDebug("loc__x"+loc[0]+",Y "+loc[1])
//
//            if (view.x<= 0 || view.top > getTotalSpace()) {
//                removeAndRecycleView(view, recycler)
//            }
        }

//
//        for ( k in 0..childCount-1){
//
//        }

        return mToalDy
    }


}