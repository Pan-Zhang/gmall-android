package com.guotai.mall.activity.orderDetail;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.AlParam;
import com.guotai.mall.model.ReturnReason;
import com.guotai.mall.model.WxParam;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/7/27.
 */

public class OrderDetailPresent implements IBasePresent {

    IOrderDetailactivity iOrderDetailactivity;

    public OrderDetailPresent(IOrderDetailactivity iOrderDetailactivity){
        this.iOrderDetailactivity = iOrderDetailactivity;
    }

    public void wxPay(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.wxPay(false, null, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                WxParam wxParam = Common.parseJsonWithGson(response, WxParam.class);
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.wxPay(true, wxParam, response);
                }
            }
        }, tag);
    }

    public void alPay(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.alPay(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.alPay(true, response);
                }
            }
        }, tag);
    }

    public void getAddress(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.updateAddress(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Address> list = Common.parseJsonArrayWithGson(response, Address.class);
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.updateAddress(list);
                }
            }
        }, tag);
    }

    public void getReason(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.updateReason(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<ReturnReason> list = Common.parseJsonArrayWithGson(response, ReturnReason.class);
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.updateReason(list);
                }
            }
        }, tag);
    }

    public void reqeustBack(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.requestBackRes(false);
                    Common.showToastShort(e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.requestBackRes(true);
                }
            }
        }, tag);
    }

    public void getAliPayResult(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.alPayRes(false, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iOrderDetailactivity!=null){
                    iOrderDetailactivity.alPayRes(true, response);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iOrderDetailactivity = null;
    }

}
