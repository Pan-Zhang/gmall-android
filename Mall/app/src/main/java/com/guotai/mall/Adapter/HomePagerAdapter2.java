package com.guotai.mall.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guotai.mall.R;
import com.guotai.mall.model.HotspotInfoList;
import com.guotai.mall.model.News;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.HomeHeadImage;
import com.guotai.mall.widget.HomeHeadImage2;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ez on 2017/6/20.
 */

public class HomePagerAdapter2 extends PagerAdapter {

    List<HotspotInfoList> list;
    Context context;
    HomeClickListener homeClickListener;

    public HomePagerAdapter2(Context context, List<HotspotInfoList> list){
        this.context = context;
        this.list = list;
    }

    public void update(List<HotspotInfoList> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setHomeClickListener(HomeClickListener homeClickListener){
        this.homeClickListener = homeClickListener;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product_item3, null);
        final HotspotInfoList hotspotInfoList = list.get(position);
        HomeHeadImage2 product_iv = (HomeHeadImage2) view.findViewById(R.id.product_iv1);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels- Common.dip2px(context, 20);
        int height = width/2 + Common.dip2px(context, 20);
        Picasso.with(context).load(hotspotInfoList.ImagePath).resize(width, height).centerCrop().into(product_iv);
        product_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeClickListener!=null){
                    homeClickListener.OnClick(hotspotInfoList);
                }
            }
        });
        container.addView(view);
        return view;
    }


}
