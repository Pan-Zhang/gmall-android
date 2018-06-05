package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.HotspotInfoList;
import com.guotai.mall.widget.HeaderHorizontalImage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangpan on 2018/6/5.
 */

public class HorizontalAdapter extends MyAdapter<HotspotInfoList> {

    public HorizontalAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_horizontal_item, null);
        }
        final HotspotInfoList hotspotInfoList = list.get(position);
        View divider = convertView.findViewById(R.id.divider);
        if(position==list.size()-1){
            divider.setVisibility(View.VISIBLE);
        }
        else{
            divider.setVisibility(View.GONE);
        }
        TextView horizontal_title = (TextView) convertView.findViewById(R.id.horizontal_title);
        horizontal_title.setText(hotspotInfoList.HotspotName);
        final HeaderHorizontalImage horizontal_iv = (HeaderHorizontalImage) convertView.findViewById(R.id.horizontal_iv);
        Picasso.with(context).load(hotspotInfoList.ImagePath).resize(720, 360).centerCrop().into(horizontal_iv);
        return convertView;
    }
}
