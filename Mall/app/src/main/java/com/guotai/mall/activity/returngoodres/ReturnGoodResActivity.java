package com.guotai.mall.activity.returngoodres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.activity.logisticInput.LogisticInputActivity;
import com.guotai.mall.activity.logisticsDetail.LogisticsDetailActivity;
import com.guotai.mall.activity.returnGood.ReturnGoodActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.AfterSale;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderDetail;
import com.guotai.mall.model.OrderDetailEx;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.ProductDetail;
import com.guotai.mall.model.ReturnReason;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.ProductView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpan on 2018/4/13.
 */

public class ReturnGoodResActivity extends BaseActivity<ReturnGoodResPresent> implements IReturnGoodResActitivy, View.OnClickListener {

    public static AfterSale afterSale;
    TextView status_tv, status_detail_tv, order_num, back_reason, back_money, request_time, receive_name, receive_phone, receive_address;
    ProductView products;
    LinearLayout logistic_ll, logistic_state;
    Button do_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_good);

        present = new ReturnGoodResPresent(this);

        initTitle();
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

        logistic_ll = (LinearLayout) findViewById(R.id.logistic_ll);

        if(afterSale.AfterSaleStatusID>0 && afterSale.AfterSaleStatusID<8){
            title.setText("换货详情");
        }
        else if(afterSale.AfterSaleStatusID>7 && afterSale.AfterSaleStatusID<17){
            title.setText("退货详情");
        }
        else{
            title.setText("退款详情");
            logistic_ll.setVisibility(View.GONE);
        }

        order_num = (TextView) findViewById(R.id.order_num);
        OrderDetailEx detail = afterSale.OrderDetailList.get(0);
        order_num.setText(detail.OrderSN);

        back_reason = (TextView) findViewById(R.id.back_reason);
        back_reason.setText(afterSale.ReturnReasonName);

        back_money = (TextView) findViewById(R.id.back_money);
        back_money.setText(String.valueOf(afterSale.ReturnAmount));

        request_time = (TextView) findViewById(R.id.request_time);
        request_time.setText(afterSale.CreateTime);
    }

    private void initView(){
        status_tv = (TextView) findViewById(R.id.status_tv);
        status_tv.setText(afterSale.DetailStatusName);
        status_detail_tv = (TextView) findViewById(R.id.status_detail_tv);
        status_detail_tv.setText(afterSale.PromtMessage);

        receive_address = (TextView) findViewById(R.id.receive_address);
        receive_address.setText(afterSale.ProvinceName+afterSale.CityName+afterSale.DistrictName+afterSale.ReceiverAddress);
        receive_name = (TextView) findViewById(R.id.receive_name);
        receive_name.setText(afterSale.ReceiverName);
        receive_phone = (TextView) findViewById(R.id.receive_phone);
        receive_phone.setText(afterSale.ReceiverMobile);

        List<CarPro> list = new ArrayList<>();
        for(OrderDetailEx detail : afterSale.OrderDetailList){
            CarPro carPro = new CarPro();
            carPro.Price = detail.Price;
            carPro.FirstImage = detail.FirstImage;
            carPro.ProductName = detail.ProductName;
            carPro.ProductDescription = detail.ProductDescription;
            carPro.ProductPrice = detail.Price;
            carPro.Qty = detail.Qty;
            list.add(carPro);
        }
        products = (ProductView) findViewById(R.id.products);
        products.setData(list);

        logistic_state = (LinearLayout) findViewById(R.id.logistic_state);
        logistic_state.setOnClickListener(this);

        do_next = (Button) findViewById(R.id.do_next);
        do_next.setOnClickListener(this);

        do_next.setTag(afterSale.AfterSaleStatusID);
        if(afterSale.AfterSaleStatusID==1){//撤销
            do_next.setText("撤销售后请求");
        }
        else if(afterSale.AfterSaleStatusID==2){//填写物流
            do_next.setText("请填写物流信息");
        }
        else if(afterSale.AfterSaleStatusID==3){//可查询物流状态
            do_next.setVisibility(View.GONE);
        }
        else if(afterSale.AfterSaleStatusID==8){//撤销
            do_next.setText("撤销售后请求");
        }
        else if(afterSale.AfterSaleStatusID==9){//填写物流
            do_next.setText("请填写物流信息");
        }
        else if(afterSale.AfterSaleStatusID==10){//可查询物流状态
            do_next.setVisibility(View.GONE);
        }
        else if(afterSale.AfterSaleStatusID==17){//撤销
            do_next.setText("撤销售后请求");
        }
        else if(afterSale.AfterSaleStatusID==6){//换货未通过
            do_next.setText("再次申请");
        }
        else if(afterSale.AfterSaleStatusID==14){//退货未通过
            do_next.setText("再次申请");
        }
        else if(afterSale.AfterSaleStatusID==21){//退款未通过
            do_next.setText("再次申请");
        }
        else{//无按钮
            do_next.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
//        startActivity(new Intent(ReturnGoodResActivity.this, LogisticInputActivity.class));
        if(v.getId()==R.id.do_next){
            int tag = Integer.parseInt(v.getTag().toString());
            if(tag==1 || tag==8 ||tag==17) {//撤销退换货申请
                Map<String, String> map = new HashMap<>();
                map.put("UserID", Common.getUserID());
                map.put("AfterOrderID", afterSale.AfterSaleOrderID);
                present.CancelBackExchange("api/Order/CancleAfterOrder", map, ReturnGoodResActivity.this.getClass().getSimpleName());
            }
            else if(tag==2 || tag ==9) {//填写物流信息
                startActivity(new Intent(ReturnGoodResActivity.this, LogisticInputActivity.class));
            }
            else if(tag==6 || tag==14) {//重新申请退换货
                OrderEx orderEx = new OrderEx();
                orderEx.OrderSN = afterSale.OrderDetailList.get(0).OrderSN;
                orderEx.OrderDetailList = afterSale.OrderDetailList;
                startActivity(new Intent(ReturnGoodResActivity.this, ReturnGoodActivity.class));
            }
            else if(tag==21){//申请退款
                present.getReason("api/OrderExtra/GetReturnReasonList?AfterSaleType=3", ReturnGoodResActivity.this.getClass().getSimpleName());
            }
        }
        else if(v.getId()==R.id.logistic_state){
            present.QueryLogistics("api/Order/QueryAfterLogistics?AfterOrderID=" + afterSale.AfterSaleOrderID, ReturnGoodResActivity.this.getClass().getSimpleName());
        }
    }

    @Override
    public void LogisticRes(Boolean success, Logistics logistics) {
        if(success){
            LogisticsDetailActivity.logistics = logistics;
            startActivity(new Intent(this, LogisticsDetailActivity.class));
        }
        else{

        }
    }

    @Override
    public void doNext(Boolean res, String mess) {
        Common.showToastShort(mess);
        if(res){
            finish();
        }
    }

    @Override
    public void updateReason(List<ReturnReason> list) {
        if(list==null){
            Common.showToastLong("获取数据失败");
        }
        else {
            Map<String, String> map = new HashMap<>();
            map.put("UserID", Common.getUserID());
            map.put("OrderID", afterSale.OrderID);
            map.put("ReturnReasonID", list.get(0).ReturnReasonID);
            present.reqeustBack("api/Order/RefundOrder", map, this.getClass().getSimpleName());
        }
    }

    @Override
    public void requestBackRes(Boolean success) {
        if(success){
            Common.showToastLong("退款申请成功");
            finish();
        }
        else{
            Common.showToastLong("退款申请失败");
        }
    }
}
