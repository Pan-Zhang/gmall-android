package com.guotai.mall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by ez on 2017/6/26.
 */

public class HomeHeadImage extends ImageView {

    public HomeHeadImage(Context context) {
        super(context);
    }

    public HomeHeadImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeHeadImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = width/2+100;
        setMeasuredDimension(width, height);
    }
}
