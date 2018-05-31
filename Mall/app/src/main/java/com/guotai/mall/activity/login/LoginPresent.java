package com.guotai.mall.activity.login;

import android.util.Log;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/6/28.
 */

public class LoginPresent implements IBasePresent {

    ILoginactivity iLoginactivity;

    public LoginPresent(ILoginactivity iLoginactivity){
        this.iLoginactivity = iLoginactivity;
    }

    @Override
    public void destroy() {
        iLoginactivity = null;
    }

    public void Login(String tag, final String telephone, String pass, int type){
        if(type==0){
            Map<String, String> map = new HashMap<String, String>();
            map.put("grant_type", "password");
            map.put("UserName", telephone);
            map.put("Password", Common.md5(pass));
            HttpFactory.getInstance().AsyncPostEx("token", map, new ResultBack(){

                @Override
                public void onFailure(Call call, String e) {
                    if(iLoginactivity!=null){
                        iLoginactivity.LoginSucc(false);
                    }
                }

                @Override
                public void onResponse(Call call, String response) {
                    try {
                        Log.d("zp", response);
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.has("access_token")){
                            String access_token = jsonObject.getString("access_token");
                            String refresh_token = jsonObject.getString("refresh_token");
                            String expires = jsonObject.getString(".expires");
                            String userID = jsonObject.getString("UserID");
                            String userName = jsonObject.getString("UserName");
                            String mobile = jsonObject.getString("Mobile");
                            String gender = jsonObject.getString("Gender");
                            String birthday = jsonObject.getString("Birthday");
                            Common.saveToken(access_token);
                            Common.saveRefreshToken(refresh_token);
                            Common.saveExpire(expires);
                            Common.saveUser(userName);
                            Common.saveUserID(userID);
                            Common.saveMobile(mobile);
                            Common.saveGender(gender);
                            Common.saveBirthday(birthday);
                            if(iLoginactivity!=null){
                                iLoginactivity.LoginSucc(true);
                            }
                        }
                        else{
                            if(iLoginactivity!=null){
                                iLoginactivity.LoginSucc(false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if(iLoginactivity!=null){
                            iLoginactivity.LoginSucc(false);
                        }
                    }
                }
            }, tag);
        }
        else{
            Map<String, String> map = new HashMap<String, String>();
            map.put("grant_type", "password");
            map.put("UserName", telephone);
            map.put("Password", "SMSCode="+pass);
            HttpFactory.getInstance().AsyncPostEx("token", map, new ResultBack(){

                @Override
                public void onFailure(Call call, String e) {
                    if(iLoginactivity!=null){
                        iLoginactivity.LoginSucc(false);
                    }
                }

                @Override
                public void onResponse(Call call, String response) {
                    try {
                        Log.d("zp", response);
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.has("access_token")){
                            String access_token = jsonObject.getString("access_token");
                            String refresh_token = jsonObject.getString("refresh_token");
                            String expires = jsonObject.getString(".expires");
                            String userID = jsonObject.getString("UserID");
                            String userName = jsonObject.getString("UserName");
                            String mobile = jsonObject.getString("Mobile");
                            String gender = jsonObject.getString("Gender");
                            String birthday = jsonObject.getString("Birthday");
                            Common.saveToken(access_token);
                            Common.saveRefreshToken(refresh_token);
                            Common.saveExpire(expires);
                            Common.saveUser(userName);
                            Common.saveUserID(userID);
                            Common.saveMobile(mobile);
                            Common.saveGender(gender);
                            Common.saveBirthday(birthday);
                            if(iLoginactivity!=null){
                                iLoginactivity.LoginSucc(true);
                            }
                        }
                        else{
                            if(iLoginactivity!=null){
                                iLoginactivity.LoginSucc(false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if(iLoginactivity!=null){
                            iLoginactivity.LoginSucc(false);
                        }
                    }
                }
            }, tag);
        }
    }

    public void getCode(String phoneNum, String tag){
        HttpFactory.getInstance().AsyncGet("api/SmsVerify/SendVerifyCodeByMobile?Mobile="+phoneNum, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iLoginactivity!=null){
                    iLoginactivity.GetSms(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iLoginactivity!=null){
                    iLoginactivity.GetSms(true);
                }
            }
        }, tag);
    }
}
