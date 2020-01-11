package com.xiaojw.customwidget.util;

import android.content.Context;

public class CommonUtil {
    private static float density = -1F;

    public static int dip2px(float dpValue, Context context) {
        return (int) (dpValue * getDensity(context) + 0.5F);
    }

    public static int px2dip(float pxValue,Context context) {
        return (int) (pxValue / getDensity(context) + 0.5F);
    }

    public static float getDensity(Context context) {
        if (density <= 0F) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }


}
