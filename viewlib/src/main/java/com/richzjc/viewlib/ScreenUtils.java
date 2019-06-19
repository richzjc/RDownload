package com.richzjc.viewlib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by zhangyang on 16/7/6.
 */
public class ScreenUtils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(dpValue * scale + 0.5f);
    }


    public static int[] getDisplayWH(Context context) {
        int screenWidth, screenHeight;
        int[] wh = new int[2];
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
        screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）

        wh[0] = screenWidth;
        wh[1] = screenHeight;
        return wh;
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static float getSystemBarHeight(Activity activity) {
        Rect rectangle = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }

    public static float getY(View v) {
        if (v != null) {
            return v.getTop() + getParentY(v.getParent());
        }
        return 0;
    }

    private static float getParentY(ViewParent parent) {
        if (parent instanceof ViewGroup) {
            return ((ViewGroup) parent).getTop() + getParentY(parent.getParent());
        }
        return 0;
    }

}
