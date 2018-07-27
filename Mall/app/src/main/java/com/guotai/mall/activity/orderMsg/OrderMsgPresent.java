package com.guotai.mall.activity.orderMsg;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import okhttp3.Call;

public class OrderMsgPresent implements IBasePresent {

    IOrderMsgActivity iOrderMsgActivity;

    public OrderMsgPresent(IOrderMsgActivity iOrderMsgActivity){
        this.iOrderMsgActivity = iOrderMsgActivity;
    }

    public void getDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iOrderMsgActivity!=null){
                    iOrderMsgActivity.getDetail(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                OrderEx orderEx = Common.parseJsonWithGson(response, OrderEx.class);
                if(iOrderMsgActivity!=null){
                    iOrderMsgActivity.getDetail(true, orderEx);
                }
            }
        }, tag);
    }

    public void getLogistics(String url, final OrderEx orderEx, final int position, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iOrderMsgActivity!=null){
                    iOrderMsgActivity.getLogistics(false, null, 0, orderEx);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Logistics logistics = Common.parseJsonWithGson(response, Logistics.class);
                if(iOrderMsgActivity!=null){
                    iOrderMsgActivity.getLogistics(true, logistics, position, orderEx);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iOrderMsgActivity = null;
    }
}
