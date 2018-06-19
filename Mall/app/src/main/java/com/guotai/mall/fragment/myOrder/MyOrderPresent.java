package com.guotai.mall.fragment.myOrder;


import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.AfterSale;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderDetailEx;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/7/25.
 */

public class MyOrderPresent implements IBasePresent {

    IMyOrderfragment iMyOrderfragment;

    public MyOrderPresent(IMyOrderfragment iMyOrderfragment){
        this.iMyOrderfragment = iMyOrderfragment;
    }

    public void GetData(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.Refresh(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<OrderEx> list = Common.parseJsonArrayWithGson(response, OrderEx.class);

                if(iMyOrderfragment!=null){
                    iMyOrderfragment.Refresh(list);
                }
            }
        }, tag);
    }

    public void GetMoreData(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.RefreshMoreData(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<OrderEx> list = Common.parseJsonArrayWithGson(response, OrderEx.class);

                if(iMyOrderfragment!=null){
                    iMyOrderfragment.RefreshMoreData(list);
                }
            }
        }, tag);
    }

    public void cancelOrder(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.cancelSuccess(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.cancelSuccess(true);
                }
            }
        }, tag);
    }

    public void ensureReceive(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.ensureReceive(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.ensureReceive(true);
                }
            }
        }, tag);
    }

    public void deleteOrder(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.deleteOrder(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.deleteOrder(true);
                }
            }
        }, tag);
    }

    public void getLogistics(String url, final OrderEx orderEx, final int position, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.getLogistics(false, null, 0, orderEx);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Logistics logistics = Common.parseJsonWithGson(response, Logistics.class);
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.getLogistics(true, logistics, position, orderEx);
                }
            }
        }, tag);
    }

    public void getDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.getDetail(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                OrderEx orderEx = Common.parseJsonWithGson(response, OrderEx.class);
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.getDetail(true, orderEx);
                }
            }
        }, tag);
    }

    public void getSubDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.getSubDetail(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                OrderDetailEx orderDetailEx = Common.parseJsonWithGson(response, OrderDetailEx.class);
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.getSubDetail(true, orderDetailEx);
                }
            }
        }, tag);
    }

    public void getAddress(String url, final int position, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.updateAddress(null, position);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Address> list = Common.parseJsonArrayWithGson(response, Address.class);
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.updateAddress(list, position);
                }
            }
        }, tag);
    }

    public void getAfterSaleDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.getAfterSaleDetailRes(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iMyOrderfragment!=null){
                    AfterSale afterSale = Common.parseJsonWithGson(response, AfterSale.class);
                    iMyOrderfragment.getAfterSaleDetailRes(true, afterSale);
                }
            }
        }, tag);
    }

    public void GetDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.gotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.gotoDetail(productEx);
                }
            }
        }, tag);
    }

    public void CancelBackExchange(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.cancelBack(false, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.cancelBack(true, response);
                }
            }
        }, tag);
    }

    public void buyAgain(String url, Map<String, String> param, String tag){
        HttpFactory.getInstance().AsyncPost(url, param, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.gotoBuycar(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iMyOrderfragment!=null){
                    iMyOrderfragment.gotoBuycar(true);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iMyOrderfragment = null;
    }
}
