package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Product;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.ProductImage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ez on 2017/6/16.
 */

public class HomeAdapter extends MyAdapter<ProductEx> implements View.OnClickListener{

    OnItemClickListener onItemClickListener;

    public HomeAdapter(Context context, List<ProductEx> list){
        super(context, list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size()/2+list.size()%2;
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_home_grid, null);
        }
        ProductEx product = list.get(position*2);
        ProductImage product_img = (ProductImage) convertView.findViewById(R.id.product_img);
        product_img.setTag(position*2);
        product_img.setOnClickListener(this);
        Picasso.with(context).load(product.FirstImage).resize(720, 720).centerInside().into(product_img);
        TextView product_name = (TextView) convertView.findViewById(R.id.product_name);
        product_name.setText(product.ProductName);
        TextView product_price = (TextView) convertView.findViewById(R.id.product_price);
        product_price.setText("¥" + Common.get2Digital(product.getPrice()));

        LinearLayout right_ll = (LinearLayout) convertView.findViewById(R.id.right_ll);
        if(list.size()>position*2+1){
            right_ll.setVisibility(View.VISIBLE);
            ProductEx product1 = list.get(position*2+1);
            ProductImage product_img1 = (ProductImage) convertView.findViewById(R.id.product_img1);
            product_img1.setTag(position*2+1);
            product_img1.setOnClickListener(this);
            Picasso.with(context).load(product1.FirstImage).resize(720, 720).centerInside().into(product_img1);
            TextView product_name1 = (TextView) convertView.findViewById(R.id.product_name1);
            product_name1.setText(product1.ProductName);
            TextView product_price1 = (TextView) convertView.findViewById(R.id.product_price1);
            product_price1.setText("¥" + Common.get2Digital(product1.getPrice()));
        }else{
            right_ll.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null){
            onItemClickListener.OnClick(v);
        }
    }

    public interface OnItemClickListener{
        void OnClick(View view);
    }
}
