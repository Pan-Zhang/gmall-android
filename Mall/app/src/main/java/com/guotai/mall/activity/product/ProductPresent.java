package com.guotai.mall.activity.product;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by ez on 2017/6/16.
 */

public class ProductPresent implements IBasePresent {

    IProductactivity iProductactivity;

    public ProductPresent(IProductactivity iProductactivity){
        this.iProductactivity = iProductactivity;
    }

    @Override
    public void destroy() {
        iProductactivity = null;
    }

    public void getData(String tag, String url) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {

            }

            @Override
            public void onResponse(Call call, String response) {
                ProductEx product = new ProductEx();

                if(iProductactivity!=null){
                    iProductactivity.refresh(product);
                }
            }
        }, tag);
    }

    public void addToCollect(ProductEx productEx, String url, String tag){
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserID", Common.getUserID());
        map.put("ProductID", productEx.ProductID);
        if(productEx.ProductDetail!=null && productEx.ProductDetail.size()!=0){

            map.put("ProductSubID", String.valueOf((int)Float.parseFloat(String.valueOf(productEx.ProductDetail.get(0).get("ProductSubID")))));
        }
        map.put("Price", productEx.Price);
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort(e);
            }

            @Override
            public void onResponse(Call call, String response) {
                Common.showToastShort("添加成功");
            }
        }, tag);
    }

    public void addToCar(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort("添加失败");
                if(iProductactivity!=null){
                    iProductactivity.addCar(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Common.showToastShort("添加成功");
                if(iProductactivity!=null){
                    iProductactivity.addCar(true);
                }
            }
        }, tag);
    }

    public void getAddress(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iProductactivity!=null){
                    iProductactivity.updateAddress(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Address> list = Common.parseJsonArrayWithGson(response, Address.class);
                if(iProductactivity!=null){
                    iProductactivity.updateAddress(list);
                }
            }
        }, tag);
    }

//    public void deleteFromCar(String url, )
}
