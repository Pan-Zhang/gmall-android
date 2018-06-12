package com.guotai.mall.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.OrderDetailEx;
import com.guotai.mall.model.OrderEx;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangpan on 17/7/25.
 */

public class ProductView2 extends LinearLayout {

    Context context;
    boolean isShowLine = true;
    int type;
    BackExchangeClickListener backExchangeClickListener;

    public ProductView2(Context context) {
        super(context);
        init(context);
    }

    public ProductView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProductView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
    }

    public void setBackExchangeClickListener(BackExchangeClickListener backExchangeClickListener){
        this.backExchangeClickListener = backExchangeClickListener;
    }

    public void setType(int type){
        this.type = type;
    }

    public void setShowLine(boolean show){
        isShowLine = show;
    }

    public void setData(OrderEx orderEx){
        removeAllViews();
        List<OrderDetailEx> list = orderEx.OrderDetailList;
        for(final OrderDetailEx product:list){
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
            price.setText("¥"+product.Price);

            TextView count = (TextView) linearLayout.findViewById(R.id.count);
            count.setText("x"+product.Qty);

            final Button back_exchange_btn = (Button) linearLayout.findViewById(R.id.back_exchange_btn);
            TextView status_tv = (TextView) linearLayout.findViewById(R.id.status_tv);

            if(orderEx.IsRefund==null || orderEx.IsRefund){
                back_exchange_btn.setVisibility(View.GONE);
                status_tv.setText(product.AfterSaleStatusName);
                status_tv.setVisibility(View.VISIBLE);
            }
            else{
                if(type==4 || (type==3  && product.IsAfterSale==0)){//没有申请过售后

                    back_exchange_btn.setVisibility(View.VISIBLE);
                    back_exchange_btn.setText("退换货");
                    status_tv.setVisibility(View.GONE);
                }
                else if(type==3 && product.IsAfterSale==1){
                    back_exchange_btn.setVisibility(View.VISIBLE);
                    back_exchange_btn.setText("查看详情");
                    status_tv.setText(product.AfterSaleStatusName);
                    status_tv.setVisibility(View.VISIBLE);
                }
                else {
                    back_exchange_btn.setVisibility(View.GONE);
                    status_tv.setVisibility(View.GONE);
                }
                back_exchange_btn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(backExchangeClickListener!=null){
                            backExchangeClickListener.OnClick(product);
                        }
                    }
                });
            }

            TextView bottom_line = (TextView) linearLayout.findViewById(R.id.bottom_line);
            if(isShowLine){
                bottom_line.setVisibility(View.VISIBLE);
            }else {
                bottom_line.setVisibility(View.GONE);
            }
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(backExchangeClickListener!=null){
                        backExchangeClickListener.GotoDetail(product);
                    }
                }
            });
            addView(linearLayout);
        }
    }

    public interface BackExchangeClickListener{

        void OnClick(OrderDetailEx orderDetailEx);
        void GotoDetail(OrderDetailEx orderDetailEx);
    }

}
