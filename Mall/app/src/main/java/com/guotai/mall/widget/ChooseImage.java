package com.guotai.mall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by zhangpan on 17/11/2.
 */

public class ChooseImage extends ImageView {

    public ChooseImage(Context context) {
        super(context);
    }

    public ChooseImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = (dm.widthPixels-12)/3;
        setMeasuredDimension(width, width);
    }
}
