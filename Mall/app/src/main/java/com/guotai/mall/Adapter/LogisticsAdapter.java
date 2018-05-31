package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.LogisticsDetail;

import java.util.List;

/**
 * Created by zhangpan on 17/11/17.
 */

public class LogisticsAdapter extends MyAdapter<LogisticsDetail> {

    public LogisticsAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_logistics_item, null);
        }
        LogisticsDetail logisticsDetail = list.get(position);
        TextView logistics_time = (TextView) convertView.findViewById(R.id.logistics_time);
        logistics_time.setText(logisticsDetail.AcceptTime);
        TextView logistics_state = (TextView) convertView.findViewById(R.id.logistics_state);
        logistics_state.setText(logisticsDetail.AcceptStation);
        return convertView;
    }
}
