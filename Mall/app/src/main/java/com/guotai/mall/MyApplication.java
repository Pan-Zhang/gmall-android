package com.guotai.mall;

import android.app.Application;

import com.guotai.mall.uitl.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by ez on 2017/6/16.
 */

public class MyApplication extends Application {

    static MyApplication instance;
    public IWXAPI api;
    public int goCar;
    public int number;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        regToWx();
    }

    public static MyApplication getInstance(){
        return instance;
    }

    private void regToWx(){
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(Constants.APP_ID);
    }
}
