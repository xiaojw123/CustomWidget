package com.xiaojw.customwidget.widget.rv;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.xiaojw.customwidget.util.CommonUtil;


public class CircularViewMode implements ItemViewMode {
    private int mCircleOffset = 780;
    private float mDegToRad = 1.0f / 180.0f * (float) Math.PI;
    private float mTranslationRatio = 0.18f;
    private int tranxOffset;





    @Override
    public void applyToView(View v, RecyclerView parent) {
        float halfHeight = v.getHeight() * 0.5f;
        float parentHalfHeight = parent.getHeight() * 0.5f;
        float y = v.getY();
        float rot = parentHalfHeight - halfHeight - y;

        ViewCompat.setPivotX(v, v.getWidth() / 2);
        ViewCompat.setPivotY(v, halfHeight);

        float tranX = (float) (-Math.cos(rot * mTranslationRatio * mDegToRad) + 1) * mCircleOffset;
        int index = parent.indexOfChild(v);

//        float pwidht=parent.getWidth();

//        pwidht/0.2*x=/pwidht/3/0.5
        float maxTranx = parent.getWidth() - v.getWidth();
        tranX = Math.min(tranX, maxTranx);
        if (tranX < 100) {
            tranX *= 1.2;
        }



        if (index==1||index==3){
            if (tranxOffset==0){
                tranxOffset= CommonUtil.dip2px(16, parent.getContext());
            }
            tranX+=tranxOffset;
        }

        ViewCompat.setTranslationX(v, tranX);


        float scale = 1 - (tranX / parent.getWidth()) * 1.7f;

        if (scale < 0) {
            scale = 0f;

        }

        if (scale > 1) {
            scale = 1.0f;
        }
        if (scale > 0.5 && scale < 1) {
            scale *=0.9;
        }


        ViewCompat.setScaleX(v, scale);
        ViewCompat.setScaleY(v, scale);
        ViewCompat.setAlpha(v, scale);

    }


}
