package com.guotai.mall.activity.payed;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.LogisticFee;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/5/10.
 */

public class PayedPresent implements IBasePresent {

    IPayedActivity iPayedActivity;

    public PayedPresent(IPayedActivity iPayedActivity){
        this.iPayedActivity = iPayedActivity;
    }

    public void getDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iPayedActivity!=null){
                    iPayedActivity.getDetail(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                OrderEx orderEx = Common.parseJsonWithGson(response, OrderEx.class);
                if(iPayedActivity!=null){
                    iPayedActivity.getDetail(true, orderEx);
                }
            }
        }, tag);
    }

    public void getLogistics(String url, final OrderEx orderEx, final int position, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iPayedActivity!=null){
                    iPayedActivity.getLogistics(false, null, 0, orderEx);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Logistics logistics = Common.parseJsonWithGson(response, Logistics.class);
                if(iPayedActivity!=null){
                    iPayedActivity.getLogistics(true, logistics, position, orderEx);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iPayedActivity = null;
    }
}
