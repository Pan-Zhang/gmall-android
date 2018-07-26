package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.SystemMsg;

import java.util.List;

public class SystemMsgAdapter extends MyAdapter<SystemMsg> {

    public SystemMsgAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_system_msg_item, null);
        }
        SystemMsg systemMsg = list.get(position);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(systemMsg.Createtime);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        content.setText(systemMsg.Description);
        return convertView;
    }
}
