package com.guotai.mall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by zhangpan on 17/6/29.
 */

public class SlidingMenu extends HorizontalScrollView implements View.OnClickListener
{
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 菜单的宽度
     */
    private int mMenuWidth;
    private int mHalfMenuWidth;

    private boolean once;

    public boolean isShow;

    ViewGroup content;
    ViewGroup menu;

    OnClickListener onClickListener;

    public SlidingMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        /**
         * 显示的设置一个宽度
         */
        if (!once)
        {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            menu = (ViewGroup) wrapper.getChildAt(1);
            content = (ViewGroup) wrapper.getChildAt(0);

            mMenuWidth = 220;
            mHalfMenuWidth = 120;
            menu.getLayoutParams().width = mMenuWidth;
            content.getLayoutParams().width = mScreenWidth;
            content.setOnClickListener(this);
            menu.setOnClickListener(this);

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        if (changed)
        {
            // 将菜单隐藏
            this.scrollTo(0, 0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        int action = ev.getAction();
        switch (action)
        {
            // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mHalfMenuWidth) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isShow = true;
                }
                else{
                    this.smoothScrollTo(0, 0);
                    isShow = false;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    public void hide(){
        this.smoothScrollTo(0, 0);
        isShow = false;
    }

    public void hideSmooth(){
        this.smoothScrollTo(0, 0);
        isShow = false;
    }

    public void show(){
        this.smoothScrollTo(mMenuWidth, 0);
        isShow = true;
    }

    @Override
    public void onClick(View view) {
        if(view == menu){
            if(onClickListener!=null){
                hide();
                onClickListener.ClickRight(view);
            }
        }
        else if(view == content){
            if(isShow){
                hideSmooth();
                return;
            }
            if(onClickListener!=null){
                onClickListener.ClickLeft(view);
            }
        }
    }

    public interface OnClickListener{
        void ClickLeft(View view);
        void ClickRight(View view);
    }
}