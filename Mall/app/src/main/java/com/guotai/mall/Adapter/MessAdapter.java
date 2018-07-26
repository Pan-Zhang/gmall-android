package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Message;
import com.squareup.picasso.Picasso;

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
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(mess.MsgTypeName);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(mess.MessageTime);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        if(mess.MsgTypeID==1){
            image.setBackgroundResource(R.mipmap.systrem);
        }
        else if(mess.MsgTypeID==2){
            image.setBackgroundResource(R.mipmap.sale);
        }
        else if(mess.MsgTypeID==3){
            image.setBackgroundResource(R.mipmap.order);
        }
        TextView content = convertView.findViewById(R.id.content);
        content.setText(mess.FirstMessage);
        return convertView;
    }
}
