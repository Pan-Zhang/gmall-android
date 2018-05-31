package com.guotai.mall.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangpan on 17/8/3.
 */

public class PersonInfoAdapter extends MyAdapter<PersonInfoAdapter.InfoItem> {

    public PersonInfoAdapter(Context context, List<InfoItem> list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_person_info, null);
        }
        InfoItem infoItem = list.get(position);
        TextView info_title = (TextView) convertView.findViewById(R.id.info_title);
        TextView info_content = (TextView) convertView.findViewById(R.id.info_content);
        ImageView info_img = (ImageView) convertView.findViewById(R.id.info_img);
        info_content.setVisibility(View.VISIBLE);
        info_img.setVisibility(View.GONE);
        info_content.setText(infoItem.content);
        info_title.setText(infoItem.title);
        return convertView;
    }

    public static class InfoItem{
        public String title;
        public String content;
    }
}
