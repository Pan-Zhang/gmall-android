package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Message;

import java.util.List;

/**
 * Created by zhangpan on 17/6/28.
 */

public class MessAdapter extends MyAdapter<Message> {

    public MessAdapter(Context context, List<Message> list){
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_mess_item, null);
        }
        Message mess = list.get(position);
        TextView mess_tv = (TextView) convertView.findViewById(R.id.mess_tv);
        mess_tv.setText(mess.mess);
        TextView mess_date = (TextView) convertView.findViewById(R.id.date_tv);
        mess_date.setText(mess.date);
        return convertView;
    }
}
