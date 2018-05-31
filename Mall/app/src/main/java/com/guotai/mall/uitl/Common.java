package com.guotai.mall.uitl;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.guotai.mall.MyApplication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ez on 2017/6/16.
 */

public class Common {

    public static void showToastLong(String mess) {
        Toast.makeText(MyApplication.getInstance(), mess, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(String mess) {
        Toast.makeText(MyApplication.getInstance(), mess, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(int mess) {
        Toast.makeText(MyApplication.getInstance(), mess, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(int mess) {
        Toast.makeText(MyApplication.getInstance(), mess, Toast.LENGTH_SHORT).show();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String getProductDetailURL(String ProductID, String UserId){
        return "api/Product/GetProductDetail?ProductID=" + ProductID + "&UserID=" + (UserId.equals("")?"0":UserId);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> List<T> parseJsonArrayWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(jsonData).getAsJsonArray();
        for(final JsonElement elem : array){
            mList.add(gson.fromJson(elem, type));
        }
        return mList;
    }

    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    public static void saveToken(String token){
        SharedPreferencesUtils.setParam(MyApplication.getInstance(), "token", token);
    }

    public static String getToken(){
        return SharedPreferencesUtils.getParam(MyApplication.getInstance(), "token", "").toString();
    }

    public static void saveRefreshToken(String token){
        SharedPreferencesUtils.setParam(MyApplication.getInstance(), "refresh_token", token);
    }

    public static String getRefreshToken(){
        return SharedPreferencesUtils.getParam(MyApplication.getInstance(), "refresh_token", "").toString();
    }

    public static void saveExpire(String expire){
        SharedPreferencesUtils.setParam(MyApplication.getInstance(), "expire", expire);
    }

    public static String getExpire(){
        return SharedPreferencesUtils.getParam(MyApplication.getInstance(), "expire", "").toString();
    }

    public static void saveUser(String userName){
        SharedPreferencesUtils.setParam(MyApplication.getInstance(), "KEY_USER_NAME", userName);
    }

    public static String getUser(){
        return SharedPreferencesUtils.getParam(MyApplication.getInstance(), "KEY_USER_NAME", "").toString();
    }

    public static void saveUserID(String userID){
        SharedPreferencesUtils.setParam(MyApplication.getInstance(), "userID", userID);
    }

    public static String getUserID(){
        return SharedPreferencesUtils.getParam(MyApplication.getInstance(), "userID", "").toString();
    }

    public static void saveMobile(String Mobile){
        SharedPreferencesUtils.setParam(MyApplication.getInstance(), "KEY_MOBILE", Mobile);
    }

    public static String getMobile(){
        return SharedPreferencesUtils.getParam(MyApplication.getInstance(), "KEY_MOBILE", "").toString();
    }

    public static void saveGender(String gender){
        SharedPreferencesUtils.setParam(MyApplication.getInstance(), "KEY_GENDER", gender);
    }

    public static String getGender(){
        return SharedPreferencesUtils.getParam(MyApplication.getInstance(), "KEY_GENDER", "").toString();
    }

    public static void saveBirthday(String birthday){
        SharedPreferencesUtils.setParam(MyApplication.getInstance(), "KEY_BIRTHDAY", birthday);
    }

    public static String getBirthday(){
        return SharedPreferencesUtils.getParam(MyApplication.getInstance(), "KEY_BIRTHDAY", "").toString();
    }
}
