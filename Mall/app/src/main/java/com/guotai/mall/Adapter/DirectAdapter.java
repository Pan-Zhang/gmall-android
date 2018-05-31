package com.guotai.mall.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by zhangpan on 2018/4/23.
 */

public class DirectAdapter extends PagerAdapter {

    Context context;
    int[] pics;
    ImageView[] imageViews;

    public DirectAdapter(Context context, int[] pics){
        this.context = context;
        this.pics = pics;
        imageViews = new ImageView[pics.length];
        for(int i=0; i<pics.length; i++){
            imageViews[i] = new ImageView(context);
            imageViews[i].setBackgroundResource(pics[i]);
        }
    }

    @Override
    public int getCount() {
        return pics.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(imageViews[position]);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews[position]);
        return imageViews[position];
    }
}
