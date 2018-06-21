package com.guotai.mall.activity.regist;

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

public class RegistPresent implements IBasePresent {

    IRegistactivity iRegistactivity;

    public RegistPresent(IRegistactivity iRegistactivity){
        this.iRegistactivity = iRegistactivity;
    }

    @Override
    public void destroy() {
        iRegistactivity = null;
    }

    public void GetCode(String tag, String telephone){
        HttpFactory.getInstance().AsyncGet("api/Smsverify/SendVerifyCodeByMobile?Mobile="+telephone, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iRegistactivity!=null){
                    iRegistactivity.SendSuccess(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iRegistactivity!=null){
                    iRegistactivity.SendSuccess(true);
                }
            }
        }, tag);
    }

    public void Login(String tag, String username, String password, String telephone, String code){
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserName", username);
        map.put("Password", Common.md5(password));
        map.put("Mobile", telephone);
        map.put("SMSCode", code);
        HttpFactory.getInstance().AsyncPost("api/Users/Register", map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iRegistactivity!=null){
                    iRegistactivity.registSucc(false, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.has("Type") && object.get("Type").toString().equals("Success")){
                        if(iRegistactivity!=null){
                            iRegistactivity.registSucc(true, "注册成功");
                        }
                    }
                    else{
                        if(iRegistactivity!=null){
                            iRegistactivity.registSucc(false, object.has("Content")?object.getString("Content"):"注册失败");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if(iRegistactivity!=null){
                        iRegistactivity.registSucc(false, "注册失败");
                    }
                }
            }
        }, tag);
    }
}
