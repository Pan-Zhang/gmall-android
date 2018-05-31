package com.guotai.mall.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpan on 17/7/26.
 */

public class MyIndicator  extends HorizontalScrollView implements ViewPager.OnPageChangeListener{

    private static final int COLOR_TEXT_NORMAL = 0xFF000000;
    private static final int COLOR_INDICATOR_COLOR = Color.BLACK;

    private Context context;
    private  int tabWidth;
    private String[] titles;
    private int count;
    private Paint mPaint;
    private float mTranslationX;
    private ViewPager viewPager;
    private int SCREEN_WIDTH;
    private float lineheight = 4.0f;
    List<TextView> list;

    public MyIndicator(Context context) {
        this(context, null);
    }

    public MyIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorApp));
        mPaint.setStrokeWidth(lineheight);//底部指示线的宽度
        setHorizontalScrollBarEnabled(false);
        SCREEN_WIDTH = context.getResources().getDisplayMetrics().widthPixels;
        list = new ArrayList<TextView>();
    }

    public void setLineheight(float height){
        this.lineheight = height;
        mPaint.setStrokeWidth(lineheight);//底部指示线的宽度
    }

    public void setViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
    }

    public void setTitles(String[] titles){
        this.titles = titles;
        count = titles.length;
        tabWidth = SCREEN_WIDTH/4;
        generateTitleView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        tabWidth = SCREEN_WIDTH/4;
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, getHeight() - lineheight);
        canvas.drawLine(0, 0, tabWidth, 0, mPaint);//（startX, startY, stopX, stopY, paint）
        canvas.restore();
    }

    public void scroll(int position, float offset)
    {
        mTranslationX = tabWidth * (position + offset);
        scrollTo((int)mTranslationX-(SCREEN_WIDTH-tabWidth)/2, 0);
        invalidate();
    }

    private void generateTitleView()
    {
        if (getChildCount() > 0)
            this.removeAllViews();
        count = titles.length;

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(count*tabWidth, LinearLayout.LayoutParams.MATCH_PARENT));
        for (int i = 0; i < count; i++)
        {
            TextView tv = new TextView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(tabWidth,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            tv.setGravity(Gravity.CENTER);
            if(i==0){
                tv.setTextColor(getResources().getColor(R.color.colorApp));
            }else{
                tv.setTextColor(getResources().getColor(R.color.colorProduct));
            }

            tv.setText(titles[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);//字体大小
            tv.setLayoutParams(lp);
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(viewPager!=null){
                        viewPager.setCurrentItem(finalI);
                    }
                }
            });
            linearLayout.addView(tv);
            list.add(tv);
        }
        addView(linearLayout);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        scroll(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0; i<titles.length; i++){
            if(i==position){
                list.get(position).setTextColor(getResources().getColor(R.color.colorApp));
            }else{
                list.get(i).setTextColor(getResources().getColor(R.color.colorProduct));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
