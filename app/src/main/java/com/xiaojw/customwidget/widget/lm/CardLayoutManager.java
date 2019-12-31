package com.xiaojw.customwidget.widget.lm;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xiaojw.customwidget.util.APPLOG;

import java.util.List;

public class CardLayoutManager extends RecyclerView.LayoutManager {
    private int mLastVisiblePos;


    private long mHorizontalOffset;
    private int childWidth;
    private int itemGap = 10;
    private float onceCompleteScrollLength = -1;
    /**
     * 屏幕可见第一个view的position
     */
    private int mFirstVisiPos;
    private int firstChildCompleteScrollLength;


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        onceCompleteScrollLength = -1;
        detachAndScrapAttachedViews(recycler);
        fill(recycler, state, 0);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        APPLOG.Companion.printDebug("scorllBy__dx__"+dx);
        if (dx == 0 || getChildCount() == 0) {
            return 0;
        }
        float realDx = dx / 1.0f;
        if (Math.abs(realDx) < 0.00000001f) {
            return 0;
        }
        mHorizontalOffset += dx;
        APPLOG.Companion.printDebug("H_OFFSET_"+mHorizontalOffset);
        dx = fill(recycler, state, dx);


        return dx;
    }

    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dx) {
        int resultDelata = fillHorizontalLeft(recycler, state, dx);
        recyclerChildren(recycler);
        return resultDelata;
    }

    private void recyclerChildren(RecyclerView.Recycler recycler) {
        List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        for (int i = 0; i < scrapList.size(); i++) {
            RecyclerView.ViewHolder holder = scrapList.get(i);
            removeAndRecycleView(holder.itemView, recycler);
        }
    }

    private int fillHorizontalLeft(RecyclerView.Recycler recycler, RecyclerView.State state, int dx) {
        if (dx < 0) {
            if (mHorizontalOffset < 0) {
                mHorizontalOffset = dx = 0;
            }
        }
        if (dx > 0) {
            if (mHorizontalOffset >= getMaxOffset()) {
                mHorizontalOffset = (long) getMaxOffset();
                dx = 0;
            }
        }
        detachAndScrapAttachedViews(recycler);
        float startX = 0;
        float fraction = 0f;
        boolean isChildLeft = true;
        View tmpView = null;
        int tmpPos = -1;
        if (onceCompleteScrollLength == -1) {
            tmpPos = mFirstVisiPos;
            tmpView = recycler.getViewForPosition(tmpPos);
            measureChildWithMargins(tmpView, 0, 0);
            childWidth = getDecoratedMeasurementHorizontal(tmpView);
        }
        firstChildCompleteScrollLength = getWidth() / 2 + childWidth / 2;
        if (mHorizontalOffset >= firstChildCompleteScrollLength) {
            startX = itemGap;
            onceCompleteScrollLength = childWidth + itemGap;
            mFirstVisiPos = (int) (Math.floor(Math.abs(mHorizontalOffset - firstChildCompleteScrollLength) / onceCompleteScrollLength) + 1);
            fraction = Math.abs((mHorizontalOffset - firstChildCompleteScrollLength) % onceCompleteScrollLength) / (onceCompleteScrollLength * 1.0f);
        } else {
            mFirstVisiPos = 0;
            startX = getMinOffset();
            onceCompleteScrollLength = firstChildCompleteScrollLength;
            fraction = (Math.abs(mHorizontalOffset) % onceCompleteScrollLength) / (onceCompleteScrollLength * 1.0f);
        }
        mLastVisiblePos = getItemCount() - 1;
        float noramlViewOffset = onceCompleteScrollLength * fraction;
        boolean isNormalViewOffsetSetted = false;
        for (int i = mFirstVisiPos; i <= mLastVisiblePos; i++) {
            View child;
            if (i == tmpPos && tmpView != null) {
                child = tmpView;
            } else {
                child = recycler.getViewForPosition(i);
            }
            addView(child);
            measureChildWithMargins(child, 0, 0);
            if (!isNormalViewOffsetSetted) {
                startX -= noramlViewOffset;
                isNormalViewOffsetSetted = true;
            }
            int l, t, r, b;
            l = (int) startX;
            t = getPaddingTop();
            r = l + getDecoratedMeasurementHorizontal(child);
            b = t + getDecoratedMeasurementVertical(child);
            final float minScale = 0.6f;
            float currentScale = 0f;
            final int childCenterX = (r + l) / 2;
            final int parentCenterX = getWidth() / 2;
            isChildLeft = childCenterX <= parentCenterX;
            if (isChildLeft) {
                final float fracScale = (parentCenterX - childCenterX) / (parentCenterX * 1.0f);
                currentScale = 1.0f - (1.0f - minScale) * fracScale;
            } else {
                final float fracScale = (childCenterX - parentCenterX) / (parentCenterX * 1.0f);
                currentScale = 1.0f - (1.0f - minScale) * fracScale;
            }
            child.setScaleY(currentScale);
            child.setScaleY(currentScale);
            child.setAlpha(currentScale);
            layoutDecoratedWithMargins(child, l, t, r, b);
            startX += (childWidth + itemGap);
            if (startX > getWidth() - getPaddingRight()) {
                mLastVisiblePos = i;
                break;
            }


        }
        return dx;
    }

    private int getDecoratedMeasurementVertical(View child) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        return getDecoratedMeasuredHeight(child) + params.topMargin + params.bottomMargin;
    }

    private float getMinOffset() {
        if (childWidth==0)return 0;
        return (getWidth()-childWidth)/2;
    }

    private int getDecoratedMeasurementHorizontal(View view) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin;
    }

    private int getMaxOffset() {
        if (childWidth == 0 || getChildCount() == 0) {
            return 0;
        }
        return (childWidth + itemGap) * (getItemCount() - 1);
    }
}
