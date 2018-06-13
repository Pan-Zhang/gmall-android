package com.guotai.mall.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangpan on 17/7/25.
 */

public class ProductView extends LinearLayout {

    Context context;
    boolean isShowLine = true;

    public ProductView(Context context) {
        super(context);
        init(context);
    }

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProductView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
    }

    public void setShowLine(boolean show){
        isShowLine = show;
    }

    public void setData(List<CarPro> list){
        removeAllViews();
        for(CarPro product:list){
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_product, null);
            ImageView image = (ImageView) linearLayout.findViewById(R.id.image);
            if(!TextUtils.isEmpty(product.FirstImage)) {
                Picasso.with(context).load(product.FirstImage).resize(300, 300).centerInside().into(image);
            }

            TextView name = (TextView) linearLayout.findViewById(R.id.name);
            name.setText(product.ProductName);

            TextView content = (TextView) linearLayout.findViewById(R.id.content);
            content.setText(product.ProductDescription);

            TextView price = (TextView) linearLayout.findViewById(R.id.price);
            price.setText("¥"+product.getProductPrice());

            TextView count = (TextView) linearLayout.findViewById(R.id.count);
            count.setText("x"+product.Qty);

            TextView bottom_line = (TextView) linearLayout.findViewById(R.id.bottom_line);
            if(isShowLine){
                bottom_line.setVisibility(View.VISIBLE);
            }else {
                bottom_line.setVisibility(View.GONE);
            }

            addView(linearLayout);
        }
    }

}
