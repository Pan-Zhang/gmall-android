package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.MyApplication;
import com.guotai.mall.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ez on 2017/6/15.
 */

public class TabGridAdapter extends BaseAdapter {

    String[] titles = new String[]{"首页", "分类", "购物车", "我的"};
    int[] images = new int[]{R.mipmap.home, R.mipmap.category, R.mipmap.car, R.mipmap.me};
    int[] images_cover = new int[]{R.mipmap.home_cover, R.mipmap.category_cover, R.mipmap.car_cover, R.mipmap.me_cover};
    Context context;
    public int pos;
    public TabGridAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_tab_grid, null);
        }
        TextView tab_tv = (TextView) convertView.findViewById(R.id.tab_tv);
        tab_tv.setText(titles[position]);
        ImageView tab_iv = (ImageView) convertView.findViewById(R.id.tab_iv);
        TextView pro_num = (TextView) convertView.findViewById(R.id.pro_num);
        if(position==2){
            if(MyApplication.getInstance().number>0){
                pro_num.setVisibility(View.VISIBLE);
                pro_num.setText(String.valueOf(MyApplication.getInstance().number));
            }
            else{
                pro_num.setVisibility(View.INVISIBLE);
            }
        }
        else{
            pro_num.setVisibility(View.INVISIBLE);
        }
        if(position==pos){
            tab_iv.setBackgroundResource(images_cover[position]);
            tab_tv.setTextColor(context.getResources().getColor(R.color.colorApp));
        }else{
            tab_tv.setTextColor(context.getResources().getColor(R.color.colorTextGray));
            tab_iv.setBackgroundResource(images[position]);
        }

        return convertView;
    }
}
