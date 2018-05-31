package com.guotai.mall.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;

/**
 * Created by zhangpan on 17/10/26.
 */

public class SegmentLayout extends LinearLayout {

    String[] titles;
    Context context;
    TextView[] textViews;
    SegmentClickListener segmentClickListener;

    public SegmentLayout(Context context) {
        super(context);
        this.context = context;
    }

    public SegmentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public SegmentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void init(String[] titles){
        this.titles = titles;
        textViews = new TextView[titles.length];
        setBackgroundDrawable(getResources().getDrawable(R.drawable.segment_bg_all));
        for(int i=0; i<titles.length; i++){
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
            tv.setPadding(12, 12, 12, 12);

            tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            if(i==0){
                tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.segment_bg3));
                tv.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else{
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.segment_bg4));
            }
            tv.setTag(i);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = Integer.parseInt(v.getTag().toString());
                    if(tag==0){
                        textViews[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.segment_bg3));
                        textViews[0].setTextColor(getResources().getColor(R.color.colorWhite));
                        textViews[1].setTextColor(getResources().getColor(R.color.colorPrimary));
                        textViews[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.segment_bg4));

                    }
                    if(tag==1){
                        textViews[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.segment_bg));
                        textViews[0].setTextColor(getResources().getColor(R.color.colorPrimary));
                        textViews[1].setTextColor(getResources().getColor(R.color.colorWhite));
                        textViews[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.segment_bg2));
                    }

                    if(segmentClickListener!=null){
                        segmentClickListener.Click((int)v.getTag());
                    }
                }
            });
            tv.setText(titles[i]);
            textViews[i] = tv;
            addView(tv);
        }
    }

    public void setSegmentClickListener(SegmentClickListener segmentClickListener){
        this.segmentClickListener = segmentClickListener;
    }

    public interface SegmentClickListener{
        void Click(int index);
    }

}
