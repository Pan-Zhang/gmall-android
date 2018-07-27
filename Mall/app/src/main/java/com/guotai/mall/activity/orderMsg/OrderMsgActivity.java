package com.guotai.mall.activity.orderMsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.OrderMsgAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.orderDetail.OrderDetailActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.OrderMsg;
import com.guotai.mall.uitl.Common;

import java.util.List;

public class OrderMsgActivity extends BaseActivity<OrderMsgPresent> implements IOrderMsgActivity {

    public static List<OrderMsg> list;
    ListView listView;
    OrderMsgAdapter orderMsgAdapter;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_msg);

        initTitle();

        present = new OrderMsgPresent(this);

        listView = (ListView) findViewById(R.id.list);
        orderMsgAdapter = new OrderMsgAdapter(this, list);
        listView.setAdapter(orderMsgAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogUtils.showWaitDialog(OrderMsgActivity.this);
                OrderMsg orderMsg = list.get(position);
                if(orderMsg.IsApplyAfterSale==1){//售后订单详情
                    type = 3;
                    present.getDetail("api/Order/GetAfterOrderDetail?UserID=" + Common.getUserID() + "&OrderID=" + orderMsg.OrderID, OrderMsgActivity.this.getClass().getSimpleName());
                }
                else{//普通订单详情
                    if(orderMsg.OrderStatusID==1){
                        type = 0;
                    }
                    else if(orderMsg.OrderStatusID==2 || orderMsg.OrderStatusID==3 || orderMsg.OrderStatusID==4 || orderMsg.OrderStatusID==5){
                        type = 1;
                    }
                    else if(orderMsg.OrderStatusID==6){
                        type = 2;
                    }
                    else if(orderMsg.OrderStatusID==7){
                        type = 4;
                    }
                    else if(orderMsg.OrderStatusID==8){
                        type = 5;
                    }
                    present.getDetail("api/Order/GetOrderDetail?UserID=" + Common.getUserID() + "&OrderID=" + orderMsg.OrderID, OrderMsgActivity.this.getClass().getSimpleName());

                }
            }
        });
    }

    private void initTitle() {
        ImageView left_iv = (ImageView) findViewById(R.id.left_iv);
        left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        left_iv.setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("订单消息");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list = null;
    }

    @Override
    public void getDetail(boolean success, OrderEx orderEx) {
        dialogUtils.disMiss();
        if(success){
            present.getLogistics("api/Order/QueryLogistics?OrderID=" + orderEx.OrderID, orderEx, type, OrderMsgActivity.this.getClass().getSimpleName());
        }
        else{
            Common.showToastLong(R.string.str_getdata_failed);
        }
    }

    @Override
    public void getLogistics(boolean success, Logistics logistics, int position, OrderEx orderEx) {
        if(success){
            OrderDetailActivity.detail = orderEx;
            OrderDetailActivity.logistics = logistics;
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
        else{
            OrderDetailActivity.detail = orderEx;
            Logistics logistics1 = new Logistics();
            logistics1.Reason = "无相关物流信息";
            OrderDetailActivity.logistics = logistics;
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }
}
