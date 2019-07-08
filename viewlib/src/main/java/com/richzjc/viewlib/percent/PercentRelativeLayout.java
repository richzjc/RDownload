package com.richzjc.viewlib.percent;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.richzjc.viewlib.R;

public class PercentRelativeLayout extends RelativeLayout {
    public PercentRelativeLayout(Context context) {
        super(context);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        for(int i = 0; i < count; i++){
            View view = getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if(checkLayoutParams(params)){
                LayoutParams params1 = (LayoutParams) params;
                if(params1.width > 0){
                    params.width = (int) (width * params1.width);
                }

                if(params1.height > 0){
                    params.height = (int) (height * params1.height);
                }
                view.setLayoutParams(params);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    public RelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams {

        public float width;
        public float height;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.Percent);
            width = array.getFloat(R.styleable.Percent_width_percent, 0);
            height = array.getFloat(R.styleable.Percent_height_percent, 0);
            array.recycle();
        }

    }
}
