package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.ReturnReason;

import java.util.List;

/**
 * Created by zhangpan on 2018/5/17.
 */

public class ReasonAdapter extends MyAdapter<ReturnReason> {

    public ReasonAdapter(Context context, List<ReturnReason> list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_reason_item, null);
        }
        ReturnReason returnReason = list.get(position);
        TextView reason = (TextView) convertView.findViewById(R.id.reason);
        reason.setText(returnReason.ReturnReasonName);

        ImageView choosed = (ImageView) convertView.findViewById(R.id.choosed);
        if(returnReason.choosed){
            choosed.setVisibility(View.VISIBLE);
        }
        else{
            choosed.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
