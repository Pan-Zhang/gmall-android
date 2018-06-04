package com.guotai.mall.activity.orderDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.guotai.mall.MyApplication;
import com.guotai.mall.R;
import com.guotai.mall.activity.addAddress.AddAddressActivty;
import com.guotai.mall.activity.logisticsDetail.LogisticsDetailActivity;
import com.guotai.mall.activity.makeOrder.MakeOrderActivity;
import com.guotai.mall.activity.payed.PayedActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.fragment.myOrder.MyOrderFragment;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.ReturnReason;
import com.guotai.mall.model.WxParam;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.DialogUtils;
import com.guotai.mall.widget.BackMoneyReasonDialog;
import com.guotai.mall.widget.BottomAnimDialog;
import com.guotai.mall.widget.ProductView2;
import com.guotai.mall.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.lang.ref.SoftReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by zhangpan on 17/7/27.
 */

public class OrderDetailActivity extends BaseActivity<OrderDetailPresent> implements IOrderDetailactivity {

    ProductView2 products;
    public static OrderEx detail;
    public static Logistics logistics;
    TextView name_tel, receive_address, invoice_head, total_money, order_time, order_num, state_tv, time_tv;
    Button pay_money;
    LinearLayout detail_ll;
    BottomAnimDialog dialog;

    AliPayHandler mHandler;

    public int type;

    private static final int PAY_RESULT = 0;

    private static final String PAY_OK = "9000";// 支付成功
    private static final String PAY_WAIT_CONFIRM = "8000";// 交易待确认
    private static final String PAY_NET_ERR = "6002";// 网络出错
    private static final String PAY_CANCLE = "6001";// 交易取消
    private static final String PAY_FAILED = "4000";// 交易失败

    /*内部类，处理支付宝支付结果*/
    static class AliPayHandler extends Handler {
        private SoftReference<OrderDetailActivity> activitySoftReference;// 使用软引用防止内存泄漏

        public AliPayHandler(OrderDetailActivity activity) {
            activitySoftReference = new SoftReference<OrderDetailActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            OrderDetailActivity activity = activitySoftReference.get();

            int what = msg.what;
            Map<String, String> result = (Map<String, String>) msg.obj;
            String resutStatus = result.get("resultStatus");

            switch (what){
                case PAY_RESULT:
                    if (resutStatus.equals(PAY_OK) || resutStatus.equals(PAY_FAILED)) {
                        activity.dialogUtils.showWaitDialog(activity);
                        activity.present.getAliPayResult("api/Order/GetPayResult?OrderID=" + detail.OrderID, activity.getClass().getSimpleName());

                    } else if (resutStatus.equals(PAY_CANCLE)) {
                        Common.showToastShort("取消支付");
                    } else if (resutStatus.equals(PAY_NET_ERR)) {
                        Common.showToastShort("网络错误");
                    } else if (resutStatus.equals(PAY_WAIT_CONFIRM)) {
                        Common.showToastShort("等待确认");
                    } else {

                    }
                    break;
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        present = new OrderDetailPresent(this);
        mHandler = new AliPayHandler(this);
        initTitle();

        type = getIntent().getIntExtra("type", 0);

        products = (ProductView2)findViewById(R.id.products);
        products.setData(detail);

        name_tel = (TextView) findViewById(R.id.name_tel);
        name_tel.setText(detail.ReceiverName + "  " + detail.ReceiverMobile);
        receive_address = (TextView) findViewById(R.id.receive_address);
        if(detail.ProvinceName.equals(detail.CityName)){
            receive_address.setText(detail.CityName + detail.DistrictName);
        }
        else{
            receive_address.setText(detail.ProvinceName + detail.CityName + detail.DistrictName + detail.ReceiverAddress);
        }
        invoice_head = (TextView) findViewById(R.id.invoice_head);
        invoice_head.setText(detail.InvoiceName);
        total_money = (TextView) findViewById(R.id.total_money);
        total_money.setText(detail.PayAmount + "(含运费" + detail.TranportFee + "元)");
        order_time = (TextView) findViewById(R.id.order_time);
        order_time.setText(detail.OrderTime);
        order_num = (TextView) findViewById(R.id.order_num);
        order_num.setText(detail.OrderSN);

        detail_ll = (LinearLayout) findViewById(R.id.detail_ll);
        detail_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logistics!=null && logistics.Traces!=null &&logistics.Traces.size()>0){
                    LogisticsDetailActivity.logistics = logistics;
                    startActivity(new Intent(OrderDetailActivity.this, LogisticsDetailActivity.class));
                }
            }
        });

        state_tv = (TextView) findViewById(R.id.state_tv);

        time_tv = (TextView) findViewById(R.id.time_tv);

        if(logistics!=null && logistics.Traces!=null && logistics.Traces.size()>0){
            state_tv.setText(logistics.Traces.get(logistics.Traces.size()-1).AcceptStation);
            time_tv.setText(logistics.Traces.get(logistics.Traces.size()-1).AcceptTime);
        }
        else{
            state_tv.setText(R.string.str_no_logistics);
            time_tv.setVisibility(View.GONE);
        }

        pay_money = (Button) findViewById(R.id.pay_money);
        switch (type){
            case 0:

                break;

            case 1:
                pay_money.setText("申请退款");
                break;

            case 2:
                pay_money.setText("确认收货");
                break;

            case 4:
            case 5:
                pay_money.setText("再次购买");
                break;
        }
        pay_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==0){//支付
                    if(dialog==null){
                        dialog = new BottomAnimDialog(OrderDetailActivity.this, "微信支付", "支付宝", "取消");
                        dialog.setClickListener(new BottomAnimDialog.BottonAnimDialogListener() {
                            @Override
                            public void onItem1Listener() {
                                Map<String, String> params = new HashMap<>();
                                params.put("UserID", Common.getUserID());
                                params.put("OrderID", detail.OrderID);
                                present.wxPay("api/Order/WeiXinPay", params, OrderDetailActivity.this.getClass().getSimpleName());
                                dialog.dismiss();
                            }

                            @Override
                            public void onItem2Listener() {
                                Map<String, String> params = new HashMap<>();
                                params.put("UserID", Common.getUserID());
                                params.put("OrderID", detail.OrderID);
                                present.alPay("api/Order/AliPayPostData", params, OrderDetailActivity.this.getClass().getSimpleName());
                                dialog.dismiss();
                            }

                            @Override
                            public void onItem3Listener() {
                                dialog.dismiss();
                            }
                        });
                    }
                    dialog.show();
                }
                else if(type==1){//申请退款
                    present.getReason("api/OrderExtra/GetReturnReasonList?AfterSaleType=3", OrderDetailActivity.this.getClass().getSimpleName());
                }
                else if(type==2){//确认收货

                }
                else if(type==4 || type==5){//再次购买
                    present.getAddress("api/UserReceiver/GetUserReceiverList?UserID="+Common.getUserID(), OrderDetailActivity.this.getClass().getSimpleName());

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
        title.setText(R.string.str_order_detail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detail = null;
        logistics = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(WXPayEntryActivity.PAY_RESULT){
            dialogUtils.showWaitDialog(this);
            WXPayEntryActivity.PAY_RESULT = false;
            present.getAliPayResult("api/Order/GetPayResult?OrderID=" + detail.OrderID, getClass().getSimpleName());
        }
    }

    @Override
    public void wxPay(boolean sucess, WxParam wxParam, String reason) {
        if(sucess){
            PayReq request = new PayReq();
            request.appId = wxParam.appid;
            request.partnerId = wxParam.partnerid;
            request.prepayId= wxParam.prepayid;
            request.packageValue = wxParam._package;
            request.nonceStr= wxParam.noncestr;
            request.timeStamp= wxParam.timestamp;
            request.sign= wxParam.sign;

//            // 把参数的值传进去SortedMap集合里面
//            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
//            parameters.put("appid", request.appId);
//            parameters.put("noncestr", request.nonceStr);
//            parameters.put("package", request.packageValue);
//            parameters.put("partnerid", request.partnerId);
//            parameters.put("prepayid", request.prepayId);
//            parameters.put("timestamp", request.timeStamp);
//
//            String characterEncoding = "UTF-8";
//            String mySign = createSign(characterEncoding, parameters);
//
//            request.sign= mySign;

            MyApplication.getInstance().api.sendReq(request);
        }
        else{
            Common.showToastLong(reason);
        }
    }

    /**
     * 微信支付签名算法sign
     *
     * @param characterEncoding
     * @param parameters
     * @return
     */
    public static String createSign(String characterEncoding,
                                    SortedMap<Object, Object> parameters) {

        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + "4HzphaXqV6rFEyO0aZ9cME0FXEDfvtzX"); //KEY是商户秘钥
        String sign = md5(sb.toString())
                .toUpperCase();
        return sign; // D3A5D13E7838E1D453F4F2EA526C4766
        // D3A5D13E7838E1D453F4F2EA526C4766
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void alPay(boolean success, final String param) {
        if(success){
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Map<String, String> map = Common.parseJsonWithGson(param, Map.class);
                    PayTask payTask = new PayTask(OrderDetailActivity.this);
                    Map<String, String> result = payTask.payV2(String.valueOf(map.get("resultdata")), true);
                    Message message = mHandler.obtainMessage();
                    message.what = PAY_RESULT;
                    message.obj = result;
                    mHandler.sendMessage(message);
                }
            }.start();
        }
    }

    @Override
    public void updateAddress(List<Address> list) {
        if(list==null){
            Common.showToastShort("获取地址失败");
        }
        else if(list.size()==0){
            startActivity(new Intent(this, AddAddressActivty.class));
        }
        else{
            List<CarPro> list_pro = new ArrayList<>();
            OrderEx ex = detail;
            for(int i=0; i<ex.OrderDetailList.size(); i++){
                CarPro carPro = new CarPro();
                carPro.Qty = ex.OrderDetailList.get(i).Qty;
                carPro.FirstImage = ex.OrderDetailList.get(i).FirstImage;
                carPro.ProductDescription = ex.OrderDetailList.get(i).ProductDescription;
                carPro.ProductPrice = ex.OrderDetailList.get(i).Price;
                carPro.ProductID = ex.OrderDetailList.get(i).ProductID;
                carPro.ProductSubID = ex.OrderDetailList.get(i).ProductSubID;
                carPro.ProductName = ex.OrderDetailList.get(i).ProductName;
                list_pro.add(carPro);
            }
            MakeOrderActivity.list_pro = list_pro;
            MakeOrderActivity.list_address = list;
            startActivity(new Intent(OrderDetailActivity.this, MakeOrderActivity.class));
        }
    }

    @Override
    public void updateReason(final List<ReturnReason> list) {
        if(list==null){
            Common.showToastLong("获取数据失败");
        }
        else {
            list.get(0).choosed = true;
            BackMoneyReasonDialog dialog = new BackMoneyReasonDialog(this, list);
            dialog.setEnsureClickListener(new BackMoneyReasonDialog.EnsureClickListener() {
                @Override
                public void ensure(int position) {
                    Map<String, String> map = new HashMap<>();
                    map.put("UserID", Common.getUserID());
                    map.put("OrderID", detail.OrderID);
                    map.put("ReturnReasonID", list.get(position).ReturnReasonID);
                    present.reqeustBack("api/Order/RefundOrder", map, this.getClass().getSimpleName());
                }
            });
            dialog.show();
        }
    }

    @Override
    public void requestBackRes(Boolean success) {
        if(success){
            Common.showToastLong("退款申请成功");
            finish();
        }
        else{
//            Common.showToastLong("退款申请失败");
        }
    }

    @Override
    public void alPayRes(boolean success, String reason) {
        dialogUtils.disMiss();
        if(success){
            Common.showToastShort("支付成功");
            startActivity(new Intent(OrderDetailActivity.this, PayedActivity.class));
            finish();
        }
        else{
            Common.showToastShort("支付失败");
        }
    }
}
