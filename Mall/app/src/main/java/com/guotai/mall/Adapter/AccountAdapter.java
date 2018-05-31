package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;

import java.util.List;

/**
 * Created by zhangpan on 17/8/3.
 */

public class AccountAdapter extends MyAdapter<AccountAdapter.AccountSafe> {

    public AccountAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_accoutn_safe, null);
        }
        AccountSafe accountSafe = list.get(position);
        ImageView account_img = (ImageView) convertView.findViewById(R.id.account_img);
        TextView account_title = (TextView) convertView.findViewById(R.id.account_title);
        TextView account_content = (TextView) convertView.findViewById(R.id.account_content);
        account_img.setBackgroundResource(accountSafe.img);
        account_title.setText(accountSafe.title);
        account_content.setText(accountSafe.content);
        return convertView;
    }

    public static class AccountSafe{
        public int img;
        public String title;
        public String content;
    }
}
