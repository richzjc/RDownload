package com.richzjc.viewlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    int lastX;
    int lastY;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.RED);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("test", "MyView: onDraw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(100, 100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("test", "myView : onTouchEvent");

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //调用layout方法来重新放置它的位置   第一种实现方式
//                layout(getLeft()+offsetX, getTop()+offsetY,
//                        getRight()+offsetX , getBottom()+offsetY);

                //第二种实现方式
                ((View)getParent()).scrollBy(-offsetX,-offsetY);


                break;
        }
            return true;
    }
}
