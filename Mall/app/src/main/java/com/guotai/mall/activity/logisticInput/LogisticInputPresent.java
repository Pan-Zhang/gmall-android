package com.guotai.mall.activity.logisticInput;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/4/26.
 */

public class LogisticInputPresent implements IBasePresent {

    ILogisticInputActivity iLogisticInputActivity;

    public LogisticInputPresent(ILogisticInputActivity iLogisticInputActivity){
        this.iLogisticInputActivity = iLogisticInputActivity;
    }

    public void SubmitLogistic(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {

            }

            @Override
            public void onResponse(Call call, String response) {

            }
        }, tag);
    }

    @Override
    public void destroy() {
        iLogisticInputActivity = null;
    }
}
