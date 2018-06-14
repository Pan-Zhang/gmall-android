package com.guotai.mall.activity.payed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.MyApplication;
import com.guotai.mall.R;
import com.guotai.mall.activity.orderDetail.OrderDetailActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.Order;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.uitl.Common;

/**
 * Created by zhangpan on 2018/5/10.
 */

public class PayedActivity extends BaseActivity<PayedPresent> implements View.OnClickListener, IPayedActivity {

    TextView order_num, receive_name, receive_address, pay_money;
    Button go_shop, see_order;
    public static OrderEx detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = new PayedPresent(this);
        setContentView(R.layout.activity_payed);
        initTitle();
        detail = OrderDetailActivity.detail;
        initView();

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
        title.setText(R.string.str_payed_success);
    }

    public void initView(){
        order_num = (TextView) findViewById(R.id.order_num);
        order_num.setText(detail.OrderSN);
        receive_name = (TextView) findViewById(R.id.receive_name);
        receive_name.setText(detail.ReceiverName);
        receive_address = (TextView) findViewById(R.id.receive_address);
        if(detail.ProvinceName.equals(detail.CityName)){
            receive_address.setText(detail.CityName + detail.DistrictName);
        }
        else{
            receive_address.setText(detail.ProvinceName + detail.CityName + detail.DistrictName + detail.ReceiverAddress);
        }
        pay_money = (TextView) findViewById(R.id.pay_money);
        pay_money.setText(Common.get2Digital(detail.getPayAmount()));
        go_shop = (Button) findViewById(R.id.go_shop);
        go_shop.setOnClickListener(this);
        see_order = (Button) findViewById(R.id.see_order);
        see_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_shop:
                MyApplication.getInstance().goHome = true;
                finish();
                break;

            case R.id.see_order:
                dialogUtils.showWaitDialog(PayedActivity.this);
                present.getDetail("api/Order/GetOrderDetail?UserID=" + Common.getUserID() + "&OrderID=" + detail.OrderID, PayedActivity.this.getClass().getSimpleName());
                finish();
                break;
        }
    }

    @Override
    public void getDetail(boolean success, OrderEx orderEx) {
        if(success){
            present.getLogistics("api/Order/QueryLogistics?OrderID=" + orderEx.OrderID, orderEx, 0, this.getClass().getSimpleName());
        }
        else{
            dialogUtils.disMiss();
            Common.showToastLong(R.string.str_getdata_failed);
        }
    }

    @Override
    public void getLogistics(boolean success, Logistics logistics, int position, OrderEx orderEx) {
        dialogUtils.disMiss();
        if(success){
            OrderDetailActivity.detail = orderEx;
            OrderDetailActivity.logistics = logistics;
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("type", 5);
            startActivity(intent);
        }
        else{
            OrderDetailActivity.detail = orderEx;
            Logistics logistics1 = new Logistics();
            logistics1.Reason = "无相关物流信息";
            OrderDetailActivity.logistics = logistics;
            Intent intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra("type", 5);
            startActivity(intent);
        }
    }
}
