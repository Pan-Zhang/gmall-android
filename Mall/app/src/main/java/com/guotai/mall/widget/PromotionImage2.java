package com.guotai.mall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by zhangpan on 2018/6/5.
 */

public class PromotionImage2 extends ImageView {

    public PromotionImage2(Context context) {
        super(context);
    }

    public PromotionImage2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PromotionImage2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = width*400/1096;
        setMeasuredDimension(width, height);
    }
}
