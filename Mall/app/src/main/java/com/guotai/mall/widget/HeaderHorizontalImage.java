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

public class HeaderHorizontalImage extends ImageView {

    public HeaderHorizontalImage(Context context) {
        super(context);
    }

    public HeaderHorizontalImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderHorizontalImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels - Common.dip2px(getContext(), 60);
        int height = width/2;
        setMeasuredDimension(width, height);
    }
}
