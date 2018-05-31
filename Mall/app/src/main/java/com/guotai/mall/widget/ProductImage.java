package com.guotai.mall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by ez on 2017/6/16.
 */

public class ProductImage extends ImageView {

    public ProductImage(Context context) {
        super(context);
    }

    public ProductImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels/2-40;
        setMeasuredDimension(width, width);
    }
}
