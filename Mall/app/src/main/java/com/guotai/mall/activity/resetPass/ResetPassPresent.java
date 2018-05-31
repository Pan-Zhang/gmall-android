package com.guotai.mall.activity.resetPass;

import com.guotai.mall.activity.resetPass.IResetPasswordActivity;
import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/10/26.
 */

public class ResetPassPresent implements IBasePresent {

    IResetPasswordActivity iResetPasswordActivity;

    public ResetPassPresent(IResetPasswordActivity iResetPasswordActivity){
        this.iResetPasswordActivity = iResetPasswordActivity;
    }

    public void reset(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iResetPasswordActivity!=null){
                    iResetPasswordActivity.reset(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iResetPasswordActivity!=null){
                    iResetPasswordActivity.reset(true);
                }
            }
        }, tag);
    }

    public void getCode(String phoneNum, String tag){
        HttpFactory.getInstance().AsyncGet("api/SmsVerify/SendVerifyCodeByMobile?Mobile="+phoneNum, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iResetPasswordActivity!=null){
                    iResetPasswordActivity.GetSms(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iResetPasswordActivity!=null){
                    iResetPasswordActivity.GetSms(true);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iResetPasswordActivity = null;
    }
}
