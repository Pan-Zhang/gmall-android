package com.guotai.mall.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guotai.mall.R;
import com.guotai.mall.model.ProductImage;
import com.guotai.mall.widget.ProductBigImage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ez on 2017/6/16.
 */

public class ProductAdapter extends PagerAdapter {

    List<ProductImage> list;
    Context context;

    public ProductAdapter(Context context, List<ProductImage> list){
        this.context = context;
        this.list = list;
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
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product_item2, null);
        ProductBigImage product_iv = (ProductBigImage) view.findViewById(R.id.product_iv);
        Picasso.with(context).load(list.get(position).getImagePath()).resize(720, 720).centerInside().into(product_iv);
        container.addView(view);
        return view;
    }
}
