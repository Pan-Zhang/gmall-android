package com.guotai.mall.fragment.buycar;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by ez on 2017/6/26.
 */

public class BuycarPresent implements IBasePresent {


    IBuycarfragment iBuycarfragment;
    List<CarPro> list = new ArrayList<CarPro>();

    public BuycarPresent(IBuycarfragment iBuycarfragment){
        this.iBuycarfragment = iBuycarfragment;
    }

    @Override
    public void destroy() {

    }

    public void getData(String tag, String url) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
//                Common.showToastShort("获取购物车列表失败");
                if(iBuycarfragment!=null){
                    iBuycarfragment.refresh(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {

                list = Common.parseJsonArrayWithGson(response, CarPro.class);

                if(iBuycarfragment!=null){
                    iBuycarfragment.refresh(list);
                }
            }
        }, tag);
    }

    public void updateCount(String url, String tag, final CarPro carPro, final boolean isAdd){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iBuycarfragment!=null){
                    iBuycarfragment.updateCount(false, carPro, isAdd);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iBuycarfragment!=null){
                    iBuycarfragment.updateCount(true, carPro, isAdd);
                }
            }
        }, tag);
    }

    public void deletePro(String url, String param, String tag){
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserID", Common.getUserID());
        map.put("ShopCartIDS", param);
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iBuycarfragment!=null){
                    iBuycarfragment.delete(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iBuycarfragment!=null){
                    iBuycarfragment.delete(true);
                }
            }
        }, tag);
    }

    public void GetDetail(String link, String tag) {
        HttpFactory.getInstance().AsyncGet(link, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iBuycarfragment!=null){
                    iBuycarfragment.GotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);

                if(iBuycarfragment!=null){
                    iBuycarfragment.GotoDetail(productEx);
                }
            }
        }, tag);
    }

    public void getAddress(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iBuycarfragment!=null){
                    iBuycarfragment.updateAddress(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Address> list = Common.parseJsonArrayWithGson(response, Address.class);
                if(iBuycarfragment!=null){
                    iBuycarfragment.updateAddress(list);
                }
            }
        }, tag);
    }
}
