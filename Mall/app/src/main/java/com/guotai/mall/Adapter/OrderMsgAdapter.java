package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.OrderMsg;

import java.util.List;

public class OrderMsgAdapter extends MyAdapter<OrderMsg> {

    public OrderMsgAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_order_msg_item, null);
        }
        OrderMsg orderMsg = list.get(position);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(orderMsg.OrderTime);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(orderMsg.OrderSN);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        content.setText(orderMsg.OrderMessage);
        return convertView;
    }
}
