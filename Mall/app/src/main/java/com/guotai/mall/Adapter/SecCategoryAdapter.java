package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ez on 2017/6/19.
 */

public class SecCategoryAdapter extends MyAdapter<Category> {

    public SecCategoryAdapter(Context context, List<Category> list){
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_seccategory_item, null);
        }
        Category category = list.get(position);
        ImageView sec_iv = (ImageView) convertView.findViewById(R.id.sec_iv);
        Picasso.with(context).load(category.ImagePath).resize(200, 200).centerInside().into(sec_iv);
        TextView sec_tv = (TextView) convertView.findViewById(R.id.sec_tv);
        sec_tv.setText(category.CategoryName);
        return convertView;
    }
}
