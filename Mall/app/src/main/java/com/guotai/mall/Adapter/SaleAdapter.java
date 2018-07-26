package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Sale;
import com.guotai.mall.widget.SaleImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SaleAdapter extends MyAdapter<Sale> {

    public SaleAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        Sale sale = list.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_promotion_item, null);
        }
        TextView date_tv = (TextView) convertView.findViewById(R.id.date_tv);
        TextView title_tv = (TextView) convertView.findViewById(R.id.title_tv);
        SaleImage image = (SaleImage) convertView.findViewById(R.id.image);
        TextView content_tv = (TextView) convertView.findViewById(R.id.content_tv);
        date_tv.setText(sale.Createtime);
        title_tv.setText(sale.Title);
        Picasso.with(context).load(sale.ImagePath).resize(960, 360).centerCrop().into(image);
        content_tv.setText(sale.Description);
        return convertView;
    }
}
