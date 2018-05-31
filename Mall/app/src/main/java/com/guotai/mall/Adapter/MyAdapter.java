package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ez on 2017/6/19.
 */

public abstract class MyAdapter<T> extends BaseAdapter {

    public Context context;
    public List<T> list;

    public MyAdapter(Context context, List<T> list){
        this.context = context;
        this.list = list;
    }

    public void update(List<T> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    abstract View createView(int position, View convertView, ViewGroup parent);
}
