package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.Order;
import com.guotai.mall.model.OrderDetail;
import com.guotai.mall.model.OrderDetailEx;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.Product;
import com.guotai.mall.widget.ProductView;
import com.guotai.mall.widget.ProductView2;

import java.sql.BatchUpdateException;
import java.util.List;

import static com.guotai.mall.activity.product.ProductActivity.product;

/**
 * Created by zhangpan on 17/7/26.
 */

public class OrderAdapter extends MyAdapter<OrderEx> implements View.OnClickListener {

    public static final int CANCEL_BACK = -1;//取消退款
    public static final int CANCEL_ORDER = 0;//马上支付
    public static final int PAY_NOW = 1;//取消订单
    public static final int BUY_AGAIN = 2;//再次购买
    public static final int ENSURE_RECEIVE = 3;//确认收货
    public static final int DELETE_ORDER = 4;//删除订单
    public static final int REQUEST_BACK = 5;//申请退款
    public static final int DETAIL = 6;//详情
    public static final int BACK_EXCHANGE = 7;//退换货

    public int type;


    OnClickButton onClickButton;

    public OrderAdapter(Context context, List list, int type) {
        super(context, list);
        this.type = type;
    }

    public void setOnClickButton(OnClickButton onClickButton){
        this.onClickButton = onClickButton;
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        HoldView holdView;
        if(convertView==null){
            holdView = new HoldView();
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item, null);
            holdView.order_num = (TextView) convertView.findViewById(R.id.order_num);
            holdView.order_status = (TextView) convertView.findViewById(R.id.order_status);
            holdView.button1 = (Button) convertView.findViewById(R.id.button1);
            holdView.button2 = (Button) convertView.findViewById(R.id.button2);
            holdView.button3 = (Button) convertView.findViewById(R.id.button3);
            holdView.button4 = (Button) convertView.findViewById(R.id.button4);
            holdView.order_ll = (LinearLayout) convertView.findViewById(R.id.order_ll);
            holdView.products = (ProductView2) convertView.findViewById(R.id.products);
            holdView.count = (TextView) convertView.findViewById(R.id.total_count);
            holdView.total = (TextView) convertView.findViewById(R.id.total);
            convertView.setTag(holdView);
        }
        else{
            holdView = (HoldView) convertView.getTag();
        }
        OrderEx order = list.get(position);

        holdView.order_num.setText("订单号："+order.OrderSN);

        holdView.button1.setFocusable(false);
        holdView.button1.setOnClickListener(this);

        holdView.button2.setFocusable(false);
        holdView.button2.setOnClickListener(this);

        holdView.button3.setFocusable(false);
        holdView.button3.setOnClickListener(this);

        holdView.button4.setFocusable(false);
        holdView.button4.setOnClickListener(this);

//        holdView.order_ll.setVisibility(View.VISIBLE);
        switch(type){
            case 0:
                holdView.button1.setVisibility(View.INVISIBLE);
                holdView.button2.setVisibility(View.VISIBLE);
                holdView.button2.setText("订单详情");
                holdView.button2.setTag(position);
                holdView.button2.setTag(R.id.tag_order, DETAIL);
                holdView.button3.setVisibility(View.VISIBLE);
                holdView.button3.setText("付款");
                holdView.button3.setTag(position);
                holdView.button3.setTag(R.id.tag_order, PAY_NOW);
                holdView.button4.setVisibility(View.VISIBLE);
                holdView.button4.setText("取消订单");
                holdView.button4.setTag(position);
                holdView.button4.setTag(R.id.tag_order, CANCEL_ORDER);
                break;

            case 1:
                holdView.button1.setVisibility(View.INVISIBLE);
                holdView.button2.setVisibility(View.VISIBLE);
                holdView.button2.setText("订单详情");
                holdView.button2.setTag(position);
                holdView.button2.setTag(R.id.tag_order, DETAIL);
                holdView.button3.setVisibility(View.VISIBLE);
                holdView.button3.setText("申请退款");
                holdView.button3.setTag(position);
                holdView.button3.setTag(R.id.tag_order, REQUEST_BACK);
                holdView.button4.setVisibility(View.VISIBLE);
                holdView.button4.setText("再次购买");
                holdView.button4.setTag(position);
                holdView.button4.setTag(R.id.tag_order, BUY_AGAIN);
                break;

            case 2:
                holdView.button1.setVisibility(View.INVISIBLE);
                holdView.button2.setVisibility(View.VISIBLE);
                holdView.button2.setText("订单详情");
                holdView.button2.setTag(position);
                holdView.button2.setTag(R.id.tag_order, DETAIL);
                holdView.button3.setVisibility(View.VISIBLE);
                holdView.button3.setText("确认收货");
                holdView.button3.setTag(position);
                holdView.button3.setTag(R.id.tag_order, ENSURE_RECEIVE);
                holdView.button4.setVisibility(View.VISIBLE);
                holdView.button4.setText("再次购买");
                holdView.button4.setTag(position);
                holdView.button4.setTag(R.id.tag_order, BUY_AGAIN);
                break;

            case 3:
//                holdView.order_ll.setVisibility(View.GONE);
                holdView.button1.setVisibility(View.INVISIBLE);

                if(order.RefundStatus==1){
                    holdView.button2.setVisibility(View.VISIBLE);
                    holdView.button2.setText("撤销退款");
                    holdView.button2.setTag(position);
                    holdView.button2.setTag(R.id.tag_order, CANCEL_BACK);
                }
                else if(order.RefundStatus==2){
                    holdView.button2.setVisibility(View.INVISIBLE);
                }
                else if(order.RefundStatus==3){
                    holdView.button2.setVisibility(View.VISIBLE);
                    holdView.button2.setText("申请退款");
                    holdView.button2.setTag(position);
                    holdView.button2.setTag(R.id.tag_order, REQUEST_BACK);
                }

                holdView.button3.setVisibility(View.VISIBLE);
                holdView.button3.setTag(R.id.tag_order, DETAIL);
                holdView.button3.setTag(position);
                holdView.button3.setText("订单详情");
                holdView.button4.setVisibility(View.VISIBLE);
                holdView.button4.setTag(R.id.tag_order, BUY_AGAIN);
                holdView.button4.setTag(position);
                holdView.button4.setText("再次购买");

                break;

            case 4:
                holdView.button1.setVisibility(View.INVISIBLE);
                holdView.button2.setVisibility(View.VISIBLE);
                holdView.button2.setText("订单详情");
                holdView.button2.setTag(position);
                holdView.button2.setTag(R.id.tag_order, DETAIL);
                holdView.button3.setVisibility(View.VISIBLE);
                holdView.button3.setText("再次购买");
                holdView.button3.setTag(position);
                holdView.button3.setTag(R.id.tag_order, BUY_AGAIN);
                holdView.button4.setVisibility(View.VISIBLE);
                holdView.button4.setText("删除订单");
                holdView.button4.setTag(position);
                holdView.button4.setTag(R.id.tag_order, DELETE_ORDER);
                break;

            case 5:
                holdView.button1.setVisibility(View.INVISIBLE);
                holdView.button2.setVisibility(View.VISIBLE);
                holdView.button2.setText("订单详情");
                holdView.button2.setTag(position);
                holdView.button2.setTag(R.id.tag_order, DETAIL);
                holdView.button3.setVisibility(View.VISIBLE);
                holdView.button3.setText("再次购买");
                holdView.button3.setTag(position);
                holdView.button3.setTag(R.id.tag_order, BUY_AGAIN);
                holdView. button4.setVisibility(View.VISIBLE);
                holdView.button4.setText("删除订单");
                holdView. button4.setTag(position);
                holdView. button4.setTag(R.id.tag_order, DELETE_ORDER);
                break;
        }

        holdView.order_status.setText(order.OrderStatusName);

        holdView.products.setShowLine(false);
        holdView.products.setBackExchangeClickListener(new ProductView2.BackExchangeClickListener() {
            @Override
            public void OnClick(OrderDetailEx orderDetailEx) {
                if(onClickButton!=null){
                    onClickButton.BackExchangeClickListener(orderDetailEx);
                }
            }

            @Override
            public void GotoDetail(OrderDetailEx orderDetailEx) {
                if(onClickButton!=null){
                    onClickButton.GotoDetail(orderDetailEx);
                }
            }
        });
        holdView.products.setType(type);
        holdView.products.setData(order);

        if(type==0){
            holdView.count.setText("共"+order.TotalQty+"件商品，需付款：");
        }
        else{
            holdView.count.setText("共"+order.TotalQty+"件商品，实付款：");
        }


        holdView.total.setText("¥" + String.valueOf(order.PayAmount));

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
                if(onClickButton!=null){
                    onClickButton.ClickButton(v);
                }
                break;
        }
    }

    static class HoldView{
        TextView order_num;
        TextView order_status;
        Button button1, button2, button3, button4;
        LinearLayout order_ll;
        ProductView2 products;
        TextView count;
        TextView total;
    }

    public interface OnClickButton{
        void ClickButton(View button);
        void BackExchangeClickListener(OrderDetailEx orderDetailEx);
        void GotoDetail(OrderDetailEx orderDetailEx);
    }
}
