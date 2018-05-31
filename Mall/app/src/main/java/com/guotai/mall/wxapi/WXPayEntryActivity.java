package com.guotai.mall.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;

import com.guotai.mall.R;
import com.guotai.mall.activity.orderDetail.OrderDetailActivity;
import com.guotai.mall.activity.payed.PayedActivity;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by zhangpan on 17/7/13.
 */

public class WXPayEntryActivity extends Activity  implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    public static Boolean PAY_RESULT = false;

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        PAY_RESULT = false;

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish" + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            PAY_RESULT = true;
            switch (resp.errCode){
                case 0:
                    Common.showToastShort("支付成功");
                    break;

                case -1:
//                    Common.showToastShort("支付失败");
                    break;

                case -2:
                    Common.showToastShort("取消支付");
                    PAY_RESULT = false;
                    break;
            }
            finish();
        }
    }
}
