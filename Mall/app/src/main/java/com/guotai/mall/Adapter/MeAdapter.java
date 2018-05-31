package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ez on 2017/6/16.
 */

public class MeAdapter extends BaseAdapter {

    int[] images = new int[]{R.mipmap.manage_address, R.mipmap.suggest, R.mipmap.collection, R.mipmap.brower_record};
    String[] titles = new String[]{"地址", "投诉建议", "收藏", "最近阅览"};
    Context context;

    public MeAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_me_grid, null);
        }
        ImageView me_iv = (ImageView) convertView.findViewById(R.id.me_iv);
        Picasso.with(context).load(images[position]).resize(250, 250).centerInside().into(me_iv);
        TextView me_content = (TextView) convertView.findViewById(R.id.me_content);
        me_content.setText(titles[position]);
        return convertView;
    }
}
