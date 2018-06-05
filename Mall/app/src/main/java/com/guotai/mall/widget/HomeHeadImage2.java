package com.guotai.mall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.guotai.mall.uitl.Common;

/**
 * Created by ez on 2017/6/26.
 */

public class HomeHeadImage2 extends ImageView {

    public HomeHeadImage2(Context context) {
        super(context);
    }

    public HomeHeadImage2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeHeadImage2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels- Common.dip2px(getContext(), 20);
        int height = width/2 + Common.dip2px(getContext(), 20);
        setMeasuredDimension(width, height);
    }
}
