package com.guotai.mall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.guotai.mall.uitl.Common;

/**
 * Created by zhangpan on 2018/6/5.
 */

public class HeaderPagerImage extends ImageView {

    public HeaderPagerImage(Context context) {
        super(context);
    }

    public HeaderPagerImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderPagerImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels - Common.dip2px(getContext(), 10);
        int height = dm.widthPixels/2;
        setMeasuredDimension(width, height);
    }
}
