package com.guotai.mall;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

//    @Test
//    public void regist() throws Exception{
//        // Context of the app under test.
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("UserName", "张攀");
//        map.put("Password", Common.md5("123456"));
//        map.put("Mobile", "18314765334");
//        map.put("SMSCode", "357577");
//        HttpFactory.getInstance().Post("api/Users/Register", map, new ResultBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                try {
//                    JSONObject object = new JSONObject(response);
//                    Log.i("zpaaaaaaa", response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "test");
//    }
//
//    @Test
//    public void getCode(){
//        HttpFactory.getInstance().Get("api/Smsverify/SendVerifyCodeByMobile?Mobile=18314765334", new ResultBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                assertEquals("aa", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                Log.i("zp", response);
//            }
//        }, "test");
//    }
//
//    @Test
//    public void login_client() throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("grant_type", "client_credentials");
//        map.put("client_id", "99278d83-ac83-43ff-9558-33fb3310f0ad");
//        map.put("client_secret", "4ccbf12c-103d-477b-8a92-267a66df5241");
//        HttpFactory.getInstance().Post("token", map, new ResultBack(){
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                try {
//                    Log.d("zp", response);
//                    JSONObject jsonObject = new JSONObject(response);
//                    String access_token = jsonObject.getString("access_token");
//                    String refresh_token = jsonObject.getString("refresh_token");
//                    String expires = jsonObject.getString(".expires");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "test");
//
//    }
//
//    @Test
//    public void login(){
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("grant_type", "password");
//        map.put("UserName", "18314765334");
//        map.put("Password", Common.md5("123456"));
//        HttpFactory.getInstance().Post("token", map, new ResultBack(){
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                try {
//                    Log.d("zp", response);
//                    JSONObject jsonObject = new JSONObject(response);
//                    String access_token = jsonObject.getString("access_token");
//                    String refresh_token = jsonObject.getString("refresh_token");
//                    String expires = jsonObject.getString(".expires");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "test");
//    }
//
//    @Test
//    public void refresh_Token() throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("grant_type", "refresh_token");
//        map.put("refresh_token", "");
//        HttpFactory.getInstance().Post("token", map, new ResultBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                try {
//                    Log.d("zp", response);
//                    JSONObject jsonObject = new JSONObject(response);
//                    String access_token = jsonObject.getString("access_token");
//                    String refresh_token = jsonObject.getString("refresh_token");
//                    String expires = jsonObject.getString(".expires");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "test");
//    }
//
//    @Test
//    public void getAllCategory() throws Exception {
//        HttpFactory.getInstance().Get("api/Product/GetCategoryList", new ResultBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                Log.d("zp", response);
//            }
//        }, "test");
//
//    }
//
//    @Test
//    public void getProductList() throws Exception {
//        HttpFactory.getInstance().Get("api/Product/GetProductList?idxPage=1&sizePage=20", new ResultBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                List<ProductEx> list = Common.parseJsonArrayWithGson(response, ProductEx.class);
//                list.size();
//            }
//        }, "test");
//
//    }
//
//    @Test
//    public void getProductDetail() throws Exception {
//        HttpFactory.getInstance().Get("api/Product/GetProductDetail?ProductID=1", new ResultBack() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("zp", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, String response) {
//                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);
//                productEx.getAllowStatus();
//            }
//        }, "test");
//
//    }
}
