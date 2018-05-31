package com.guotai.mall;

import android.util.Log;

import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangpan on 17/9/27.
 */

public class mytest {

//    @Test
//    public void testAdd() {
//
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("UserName", "zptest");
//        map.put("Password", Common.md5("123456"));
//        map.put("Mobile", "18314765334");
//        map.put("SMSCode", "238416");
//        HttpFactory.getInstance().AsyncPost("api/Users/Register", map, new ResultBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                try {
//                    JSONObject object = new JSONObject(response);
//                    System.out.print(response);
//                    Log.i("zpregit", response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "test");
//    }
//
//    @Test
//    public void testGetcode(){
//        HttpFactory.getInstance().AsyncGet("api/Smsverify/SendVerifyCodeByMobile?Mobile=18314765334", new ResultBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                Log.i("zp", response);
//            }
//        }, "test");
//    }
}
