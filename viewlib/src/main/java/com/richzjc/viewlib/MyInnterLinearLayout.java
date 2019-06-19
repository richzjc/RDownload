package com.richzjc.viewlib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class MyInnterLinearLayout extends LinearLayout {
    public MyInnterLinearLayout(Context context) {
        super(context);
    }

    public MyInnterLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyInnterLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("test", "inner + onMeasure : " + this.getId());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i("test", "inner + OnLayouter : " + this.getId());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("test", "inner + onDraw : " + this.getId());
    }
}
