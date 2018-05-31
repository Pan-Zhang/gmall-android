package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.County;

import java.util.List;

/**
 * Created by zhangpan on 17/10/20.
 */

public class CountyAdapter extends MyAdapter<County> {

    public CountyAdapter(Context context, List<County> list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_choose_add_item, null);
        }
        TextView address_item = (TextView) convertView.findViewById(R.id.address_item);
        address_item.setText(list.get(position).DistrictName);
        return convertView;
    }
}
