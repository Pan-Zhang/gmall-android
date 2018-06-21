package com.guotai.mall.activity.makeOrder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guotai.mall.R;
import com.guotai.mall.activity.orderDetail.OrderDetailActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.LogisticFee;
import com.guotai.mall.model.OrderDetail;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.AddressPopWindow;
import com.guotai.mall.widget.ProductView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpan on 17/7/25.
 */

public class MakeOrderActivity extends BaseActivity<MakeOrderPresent> implements IMakeOrderactivity, View.OnClickListener{

    ProductView productView;
    Button make_order_btn;
    TextView name_tel, location_tv, total_price, true_pay;
    RelativeLayout choose_address;
    public static List<CarPro> list_pro;
    public static List<Address> list_address;
    EditText invoice_head, remark;
    CheckBox invoice;
    Address current_address;
    LogisticFee logisticFee;
    BigDecimal money;
    AddressPopWindow popWindow;
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        present = new MakeOrderPresent(this);

        initTitle();

        isFirst = true;

        name_tel = (TextView) findViewById(R.id.name_tel);
        location_tv = (TextView) findViewById(R.id.location_tv);
        true_pay = (TextView) findViewById(R.id.true_pay);
        money = new BigDecimal("0");
        if(list_pro==null){
            return;
        }
        for(int i=0; i<list_pro.size(); i++){
            BigDecimal b1 = new BigDecimal(list_pro.get(i).getProductPrice());
            BigDecimal d = b1.multiply(new BigDecimal(list_pro.get(i).Qty));
            money = money.add(d);
        }
        for(int i=0; i<list_address.size(); i++){
            if(list_address.get(i).IsDefault){
                current_address = list_address.get(i);
                name_tel.setText(current_address.ReceiverName + "  " + current_address.ReceiverMobile);
                if(current_address.ProvinceName.equals(current_address.CityName)){
                    location_tv.setText(current_address.ProvinceName + current_address.ReceiverAddress);
                }
                else{
                    location_tv.setText(current_address.ProvinceName + current_address.CityName + current_address.ReceiverAddress);
                }

                break;
            }
        }
        total_price = (TextView) findViewById(R.id.total_price);
        total_price.setText("¥" + Common.get2Digital(money.floatValue()));

        productView = (ProductView) findViewById(R.id.productView);

        make_order_btn = (Button) findViewById(R.id.make_order_btn);
        make_order_btn.setOnClickListener(this);

        productView.setData(list_pro);

        invoice = (CheckBox) findViewById(R.id.invoice);

        invoice_head = (EditText) findViewById(R.id.invoice_head);
        remark = (EditText) findViewById(R.id.remark);
        choose_address = (RelativeLayout) findViewById(R.id.choose_address);
        choose_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popWindow==null){
                    popWindow = new AddressPopWindow(MakeOrderActivity.this, list_address);
                    popWindow.setOnItemClick(new AddressPopWindow.OnItemClick() {
                        @Override
                        public void ItemClick(int position) {
                            current_address = list_address.get(position);
                            name_tel.setText(current_address.ReceiverName + "  " + current_address.ReceiverMobile);
                            if(current_address.ProvinceName.equals(current_address.CityName)){
                                location_tv.setText(current_address.ProvinceName + current_address.ReceiverAddress);
                            }
                            else{
                                location_tv.setText(current_address.ProvinceName + current_address.CityName + current_address.ReceiverAddress);
                            }
                        }
                    });
                }
                popWindow.showPopupWindow(choose_address);
            }
        });

        present.getLogisticFee("api/OrderExtra/GetLogisticsList", this.getClass().getSimpleName());
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
        title.setText(R.string.str_order);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.make_order_btn:
                if(!invoice.isChecked() && !TextUtils.isEmpty(invoice_head.getText())){
                    Common.showToastShort("您填写了发票信息但是没有勾选发票复选框");
                    return;
                }
                dialogUtils.showWaitDialog(MakeOrderActivity.this);
                Map<String, String> map = new HashMap<String, String>();
                map.put("UserID", Common.getUserID());
                map.put("ProtectCostTypeID", "0");
                map.put("UserReceiverID", current_address.UserReceiverID);
                map.put("LogisticsID", "0");
                map.put("Remark", remark.getText().toString());
                if(invoice.isChecked()){
                    map.put("InvoiceFlag", "true");
                    map.put("InvoiceName", invoice_head.getText().toString());
                }
                else{
                    map.put("InvoiceFlag", "false");
                    map.put("InvoiceName", "");
                }
                Gson gson = new Gson();
                List<OrderDetail> list_detail = new ArrayList<>();
                for(CarPro pro : list_pro){
                    OrderDetail detail = new OrderDetail();
                    detail.ProductID = pro.ProductID;
                    detail.ProductSubID = pro.ProductSubID;
                    detail.setPrice(pro.getProductPrice());
                    detail.Qty = pro.Qty;
                    list_detail.add(detail);
                }
                String jsonstring = gson.toJson(list_detail);
                map.put("OrderDetailList", jsonstring);
                present.uploadOrder("api/Order/AddOrder", map, getClass().getSimpleName());
                break;
        }
    }

    @Override
    public void uploadRes(boolean res, OrderEx result) {
        dialogUtils.disMiss();
        if(res){
            OrderDetailActivity.detail = result;
            startActivity(new Intent(MakeOrderActivity.this, OrderDetailActivity.class));
            finish();
        }
        else{
//            Common.showToastShort("提交订单失败");
        }
    }

    @Override
    public void updateLogisticFee(boolean res, LogisticFee logisticFee) {
        if(res){
            this.logisticFee = logisticFee;
            if(logisticFee.getTranportFee()==0){
                true_pay.setText("¥" + Common.get2Digital(money.add(new BigDecimal(logisticFee.getTranportFee())).floatValue()) + "包邮");
            }
            else{
                true_pay.setText("¥" + Common.get2Digital(money.add(new BigDecimal(logisticFee.getTranportFee())).floatValue()) + "(含运费:¥" + Common.get2Digital(logisticFee.getTranportFee()) + ")");
            }
        }
    }

    @Override
    public void updateAddress(List<Address> list) {
        list_address = list;
        if(popWindow!=null && popWindow.isShowing()){
            popWindow.dismiss();
            popWindow=null;
            popWindow = new AddressPopWindow(MakeOrderActivity.this, list_address);
            popWindow.setOnItemClick(new AddressPopWindow.OnItemClick() {
                @Override
                public void ItemClick(int position) {
                    current_address = list_address.get(position);
                    name_tel.setText(current_address.ReceiverName + "  " + current_address.ReceiverMobile);
                    if(current_address.ProvinceName.equals(current_address.CityName)){
                        location_tv.setText(current_address.ProvinceName + current_address.ReceiverAddress);
                    }
                    else{
                        location_tv.setText(current_address.ProvinceName + current_address.CityName + current_address.ReceiverAddress);
                    }
                }
            });
            popWindow.showPopupWindow(choose_address);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list_pro = null;
        list_address = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isFirst){
            isFirst = false;
        }
        else{
            present.getAddress("api/UserReceiver/GetUserReceiverList?UserID="+Common.getUserID(), this.getClass().getSimpleName());
        }
    }
}
