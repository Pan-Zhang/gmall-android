package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;

import java.util.List;

/**
 * Created by zhangpan on 17/6/28.
 */

public class SettingAdapter extends MyAdapter<String> {

    public SettingAdapter(Context context, List<String> list){
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_setting_item, null);
        }
        TextView setting_title = (TextView) convertView.findViewById(R.id.setting_title);
        setting_title.setText(list.get(position));
        return convertView;
    }
}
