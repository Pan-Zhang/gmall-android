package com.guotai.mall.activity.personInfo;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/8/3.
 */

public class PersonInfoPresent implements IBasePresent {

    IPersonInfoactivity iPersonInfoactivity;

    public PersonInfoPresent(IPersonInfoactivity iPersonInfoactivity){
        this.iPersonInfoactivity = iPersonInfoactivity;
    }

    public void editInfo(String url, Map<String, String> map, final int position, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iPersonInfoactivity!=null){
                    iPersonInfoactivity.refreshSuccess(false, 0, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iPersonInfoactivity!=null){
                    iPersonInfoactivity.refreshSuccess(true, position, response);
                }
            }
        }, tag);
    }

    public void getCode(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iPersonInfoactivity!=null){
                    iPersonInfoactivity.sendCode(false, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iPersonInfoactivity!=null){
                    iPersonInfoactivity.sendCode(true, "验证码已发送，请注意接收");
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iPersonInfoactivity = null;
    }
}
