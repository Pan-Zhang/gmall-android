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

public class PromotionImage1 extends ImageView {

    public PromotionImage1(Context context) {
        super(context);
    }

    public PromotionImage1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PromotionImage1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels/2;
        int height = width*720/540;
        setMeasuredDimension(width, height);
    }
}
