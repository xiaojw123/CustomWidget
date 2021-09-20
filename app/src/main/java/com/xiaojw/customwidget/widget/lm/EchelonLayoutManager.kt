package com.xiaojw.customwidget.widget.lm

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiaojw.customwidget.util.APPLOG

class EchelonLayoutManager : RecyclerView.LayoutManager() {

    var mItemWidth: Int = 0
    var mItemHeight: Int = 0
    var mItemHeightOffset = 30


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {

        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        mItemWidth = width
        mItemHeight = (mItemWidth * 1.5).toInt()
        if (state!!.isPreLayout || state.itemCount == 0) {
            return
        }
        removeAndRecycleAllViews(recycler!!)


        mItemHeightOffset = (height - mItemHeight) / (itemCount - 1)
        APPLOG.printDebug("offset__" + mItemHeightOffset)
        APPLOG.printDebug("itemCount__" + itemCount)

        for (i in 0 until itemCount) {

            val view = recycler.getViewForPosition(i)
            view.let {

                addView(view)
                childMeasure(view)
                mItemWidth=width-(itemCount-1-i)*30
                APPLOG.printDebug("mItemWidht__"+mItemWidth)
                val left = (width-mItemWidth)/2
                val right = left + mItemWidth
                val top =
                    Math.max(height - mItemHeight - (itemCount - 1 - i) * mItemHeightOffset, 0)
                APPLOG.printDebug("height_ffs_" + mItemHeight)
                val bottom = top + mItemHeight
                APPLOG.printDebug(
                    "add view left_" + left + "，top_" + top + ", right_" + right
                            + ", bottom_" + bottom
                )
                layoutDecoratedWithMargins(view, left, top, right, bottom)

            }
        }


    }

    private fun childMeasure(view: View?) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(mItemWidth, View.MeasureSpec.EXACTLY)

        val heightSepc = View.MeasureSpec.makeMeasureSpec(mItemHeight, View.MeasureSpec.EXACTLY)
        view?.measure(widthSpec, heightSepc)
    }


}