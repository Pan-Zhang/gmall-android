package com.guotai.mall.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by ez on 2017/6/26.
 */

public class HomeHeadPager extends ViewPager {

    public HomeHeadPager(Context context) {
        super(context);
    }

    public HomeHeadPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = width/2;
        setMeasuredDimension(width, height);
    }
}
