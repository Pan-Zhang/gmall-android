package com.guotai.mall.uitl;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.guotai.mall.MyApplication;
import com.guotai.mall.activity.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by ez on 2017/6/16.
 */

public class HttpFactory {

//    private static final String baseUrl = "http://www.storeappapi.test/";
    private static final String baseUrl = "http:api.equalcost.com/";
//    private static final String baseUrl = "http://183.63.51.130:8889／";

    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_IMG = MediaType.parse("image/*");
    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
    private static HttpFactory instance;
    private OkHttpClient okHttpClient;
    private Handler handler;
    private ArrayMap<String, List<Call>> map;

    public HttpFactory(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "bearer "+Common.getToken())
                        .addHeader("MachID", MyApplication.getInstance().getImei())
                        .build();
                return chain.proceed(request);
            }

        });
        builder.retryOnConnectionFailure(true);
        //设置cookie，保持状态
        builder.cookieJar(new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
        okHttpClient = builder.build();
        handler = new Handler(Looper.getMainLooper());
        map = new ArrayMap<>();
    }

    public static HttpFactory getInstance(){
        if (instance == null)
        {
            synchronized (HttpFactory.class)
            {
                if (instance == null)
                {
                    instance = new HttpFactory();
                }
            }
        }
        return instance;
    }

    public void Get(String url, final ResultBack resultBack, String tag){
        url = baseUrl+url;
        Call call = okHttpClient.newCall(new Request.Builder().url(url).build());
        putCall(tag, call);
        try {
            Response response = call.execute();
            resultBack.onResponse(call, response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
            resultBack.onFailure(call, e.toString());
        }
    }

    public void AsyncGet(String url, final ResultBack resultBack, String tag){
        url = baseUrl+url;
        Call call = okHttpClient.newCall(new Request.Builder().url(url).build());
        putCall(tag, call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DealFailure(resultBack, call, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String res  = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    if(jsonObject.has("code") && jsonObject.getInt("code")==200){
                        DealResponse(resultBack, call, jsonObject.getString("data"));
                    }
                    else if(jsonObject.has("code") && jsonObject.getInt("code")==401){
                        DealFailure(resultBack, call, null);
                        Common.saveToken("");
                        Intent intent = new Intent(MyApplication.getInstance(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyApplication.getInstance().startActivity(intent);
                    }
                    else {
                        DealFailure(resultBack, call, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    DealFailure(resultBack, call, e.toString());
                }
            }
        });
    }

    public void Post(String url, Map<String, String> map, final ResultBack resultBack, String tag){
        url = baseUrl + url;
        FormBody.Builder builder = new FormBody.Builder();
        for (String key: map.keySet()) {
            builder.add(key, map.get(key));
        }

        RequestBody formBody = builder.build();
//        StringBuffer sb = new StringBuffer();
//        //设置表单参数
//        for (String key: map.keySet()) {
//            sb.append(key+"="+map.get(key)+"&");
//        }
//        String str = Common.jsonEnclose(map).toString().replaceAll("\"", "\\\"");
//        Log.i("apJson", str);
//        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, str);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        putCall(tag, call);
        try {
            Response response = call.execute();
            resultBack.onResponse(call, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            resultBack.onFailure(call, e.toString());
        }
    }

    public void Post(String token, String url, Map<String, String> map, final ResultBack resultBack, String tag){
        url = baseUrl + url;
        FormBody.Builder builder = new FormBody.Builder();
        for (String key: map.keySet()) {
            builder.add(key, map.get(key));
        }

        RequestBody formBody = builder.build();
//        StringBuffer sb = new StringBuffer();
//        //设置表单参数
//        for (String key: map.keySet()) {
//            sb.append(key+"="+map.get(key)+"&");
//        }
//        String str = Common.jsonEnclose(map).toString().replaceAll("\"", "\\\"");
//        Log.i("apJson", str);
//        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, str);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        putCall(tag, call);
        try {
            Response response = call.execute();
            resultBack.onResponse(call, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            resultBack.onFailure(call, e.toString());
        }
    }

    public void AsyncPost(String url, Map<String, String> map, final ResultBack resultBack, String tag){
        url = baseUrl + url;
        FormBody.Builder body = new FormBody.Builder();
        for (String key : map.keySet()) {
            body.add(key, map.get(key));
        }
        RequestBody formBody = body.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        putCall(tag, call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DealFailure(resultBack, call, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String res  = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    if(jsonObject.has("code") && jsonObject.getInt("code")==200){
                        DealResponse(resultBack, call, jsonObject.getString("data"));
                    }
                    else if(jsonObject.has("code") && jsonObject.getInt("code")==401){
                        DealFailure(resultBack, call, null);
                        Intent intent = new Intent(MyApplication.getInstance(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyApplication.getInstance().startActivity(intent);
                    }
                    else{
                        DealFailure(resultBack, call, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    DealFailure(resultBack, call, e.toString());
                }
            }
        });
    }

    public void AsyncPostEx(String url, Map<String, String> map, final ResultBack resultBack, String tag){
        url = baseUrl + url;
        FormBody.Builder body = new FormBody.Builder();
        for (String key : map.keySet()) {
            body.add(key, map.get(key));
        }
        RequestBody formBody = body.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        putCall(tag, call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DealFailure(resultBack, call, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String res  = response.body().string();
                    DealResponse(resultBack, call, res);
                } catch (Exception e) {
                    DealFailure(resultBack, call, e.toString());
                }
            }
        });
    }

    public void UploadImage(String url, Map<String, String> map, Map<String, String> img_map, final ResultBack resultBack, String tag){
        url = baseUrl + url;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(String key : map.keySet()){
            builder.addFormDataPart(key, map.get(key));
        }
        for(String key : img_map.keySet()){
            File file = new File(img_map.get(key));
            if(file.exists()){
                builder.addFormDataPart(key, file.getName(), RequestBody.create(MEDIA_TYPE_IMG, file));
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        putCall(tag, call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DealFailure(resultBack, call, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String res  = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    if(jsonObject.has("code") && jsonObject.getInt("code")==200){
                        DealResponse(resultBack, call, jsonObject.getString("data"));
                    }
                    else if(jsonObject.has("code") && jsonObject.getInt("code")==401){
                        DealFailure(resultBack, call, null);
                        Intent intent = new Intent(MyApplication.getInstance(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyApplication.getInstance().startActivity(intent);
                    }
                    else{
                        DealFailure(resultBack, call, jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    DealFailure(resultBack, call, e.toString());
                }
            }
        });
    }

    public void DealFailure(final ResultBack resultBack, final Call call, final String mess){
        handler.post(new Runnable() {
            @Override
            public void run() {
                resultBack.onFailure(call, mess);
            }
        });
    }

    public void DealResponse(final ResultBack resultBack, final Call call, final String str){
        handler.post(new Runnable() {
            @Override
            public void run() {
                resultBack.onResponse(call, str);
            }
        });
    }

    public void putCall(String tag, Call call){
        List<Call> list = map.get(tag);
        if(list==null){
            list = new ArrayList<>();
            list.add(call);
            map.put(tag, list);
        }
        else{
            list.add(call);
        }
    }

    public void cancelCall(String tag){
        List<Call> callList = map.get(tag);
        if(null != callList){
            for(Call call : callList){
                if(!call.isCanceled())
                    call.cancel();
            }
            map.remove(tag);
        }
    }
}
