package com.guotai.mall.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.ProductImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubPromotionAdapter extends MyAdapter<ProductEx> {

    private String saleTag;

    public SubPromotionAdapter(Context context, List list) {
        super(context, list);
    }

    public void setSaleTag(String tag){
        saleTag = tag;
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_sub_promotion_item, null);
        }
        ProductEx productEx = list.get(position);
        ProductImage product = (ProductImage) convertView.findViewById(R.id.product);
        Picasso.with(context).load(productEx.FirstImage).fit().into(product);
        TextView sale_tv = (TextView) convertView.findViewById(R.id.sale_tv);
        sale_tv.setText(saleTag);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(productEx.ProductName);

        TextView old_price = (TextView) convertView.findViewById(R.id.old_price);
        old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        old_price.setText(Common.get2Digital(productEx.getSuggestPrice()));

        TextView new_price = (TextView) convertView.findViewById(R.id.new_price);
        new_price.setText("¥" + Common.get2Digital(productEx.getPrice()));

        ImageView car_add = (ImageView) convertView.findViewById(R.id.car_add);
        car_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
}
