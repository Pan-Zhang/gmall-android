package com.guotai.mall.activity.returngoodres;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.ReturnReason;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/4/26.
 */

public class ReturnGoodResPresent implements IBasePresent {

    IReturnGoodResActitivy iReturnGoodResActitivy;

    public ReturnGoodResPresent(IReturnGoodResActitivy iReturnGoodResActitivy){
        this.iReturnGoodResActitivy = iReturnGoodResActitivy;
    }

    public void CancelBackExchange(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iReturnGoodResActitivy!=null){
                    iReturnGoodResActitivy.doNext(false, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iReturnGoodResActitivy!=null){
                    iReturnGoodResActitivy.doNext(true, response);
                }
            }
        }, tag);
    }

    public void reqeustBack(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iReturnGoodResActitivy!=null){
                    iReturnGoodResActitivy.requestBackRes(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iReturnGoodResActitivy!=null){
                    iReturnGoodResActitivy.requestBackRes(true);
                }
            }
        }, tag);
    }

    public void getReason(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iReturnGoodResActitivy!=null){
                    iReturnGoodResActitivy.updateReason(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<ReturnReason> list = Common.parseJsonArrayWithGson(response, ReturnReason.class);
                if(iReturnGoodResActitivy!=null){
                    iReturnGoodResActitivy.updateReason(list);
                }
            }
        }, tag);
    }

    public void QueryLogistics(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iReturnGoodResActitivy!=null){
                    iReturnGoodResActitivy.LogisticRes(false, null);
                    Common.showToastLong(e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Logistics logistics = Common.parseJsonWithGson(response, Logistics.class);
                if(iReturnGoodResActitivy!=null){
                    iReturnGoodResActitivy.LogisticRes(true, logistics);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iReturnGoodResActitivy = null;
    }
}
