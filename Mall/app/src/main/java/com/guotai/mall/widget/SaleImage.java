package com.guotai.mall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by ez on 2017/6/26.
 */

public class SaleImage extends ImageView {

    public SaleImage(Context context) {
        super(context);
    }

    public SaleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SaleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = widthMeasureSpec*360/960;
        setMeasuredDimension(widthMeasureSpec, height);
    }
}
