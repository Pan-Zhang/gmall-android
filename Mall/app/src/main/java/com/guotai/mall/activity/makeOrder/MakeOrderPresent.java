package com.guotai.mall.activity.makeOrder;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.LogisticFee;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;
import java.util.Map;
import okhttp3.Call;

/**
 * Created by zhangpan on 17/7/25.
 */

public class MakeOrderPresent implements IBasePresent {

    IMakeOrderactivity iMakeOrderactivity;

    public MakeOrderPresent(IMakeOrderactivity iMakeOrderactivity){
        this.iMakeOrderactivity = iMakeOrderactivity;
    }

    @Override
    public void destroy() {
        iMakeOrderactivity = null;
    }

    public void uploadOrder(String url, Map<String, String> map, String tag) {
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMakeOrderactivity!=null){
                    iMakeOrderactivity.uploadRes(false, null);
                    Common.showToastLong(e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                OrderEx result = Common.parseJsonWithGson(response, OrderEx.class);
                if(iMakeOrderactivity!=null){
                    iMakeOrderactivity.uploadRes(true, result);
                }
            }
        }, tag);
    }

    public void getLogisticFee(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMakeOrderactivity!=null){
                    iMakeOrderactivity.updateLogisticFee(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<LogisticFee> list_fee = Common.parseJsonArrayWithGson(response, LogisticFee.class);

                if(iMakeOrderactivity!=null){
                    for(LogisticFee logisticFee : list_fee){
                        if(logisticFee.IsDefault.equals("true")){
                            iMakeOrderactivity.updateLogisticFee(true, logisticFee);
                        }
                    }
                }
            }
        }, tag);
    }

    public void getAddress(String url, String tag) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if (iMakeOrderactivity != null) {
                    iMakeOrderactivity.updateAddress(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Address> list = Common.parseJsonArrayWithGson(response, Address.class);
                if (iMakeOrderactivity != null) {
                    iMakeOrderactivity.updateAddress(list);
                }
            }
        }, tag);
    }
}
