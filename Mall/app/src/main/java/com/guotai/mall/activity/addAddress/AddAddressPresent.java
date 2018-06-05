package com.guotai.mall.activity.addAddress;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.City;
import com.guotai.mall.model.County;
import com.guotai.mall.model.Province;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by ez on 2017/6/20.
 */

public class AddAddressPresent implements IBasePresent {

    IAddAddressactivity iAddAddressactivity;

    public AddAddressPresent(IAddAddressactivity iAddAddressactivity){
        this.iAddAddressactivity = iAddAddressactivity;
    }

    public void loadProvince(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.refreshProvince(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Province> list = Common.parseJsonArrayWithGson(response, Province.class);
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.refreshProvince(list);
                }
            }
        }, tag);
    }

    public void loadCity(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.refreshCity(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<City> list = Common.parseJsonArrayWithGson(response, City.class);
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.refreshCity(list);
                }
            }
        }, tag);
    }

    public void loadCounty(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.refreshCounty(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<County> list = Common.parseJsonArrayWithGson(response, County.class);
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.refreshCounty(list);
                }
            }
        }, tag);
    }

    public void addAddress(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iAddAddressactivity!=null){
                    Common.showToastShort(e);
                    iAddAddressactivity.addSuccess(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.addSuccess(true);
                }
            }
        }, tag);
    }

    public void editAddress(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.addSuccess(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iAddAddressactivity!=null){
                    iAddAddressactivity.addSuccess(true);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iAddAddressactivity = null;
    }
}
