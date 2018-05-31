package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Category;

import java.util.List;

/**
 * Created by ez on 2017/6/19.
 */

public class CategoryAdapter extends MyAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> list){
        super(context, list);
    }
    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_category_item, null);
        }
        TextView category_name = (TextView) convertView.findViewById(R.id.category_name);
        Category category = list.get(position);
        category_name.setText(category.CategoryName);
        if(category.isSelected){
            category_name.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }else{
            category_name.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
        }
        return convertView;
    }
}
