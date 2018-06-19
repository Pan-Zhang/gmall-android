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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ez on 2017/6/16.
 */

public class Common {

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

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

    public static String ObjectToJson(Object object){
        Gson gs = new Gson();
        return gs.toJson(object);
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

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    public static String get2Digital(float number){
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(number);//format 返回的是字符串
    }

    public static boolean ispsd(String psd) {
        Pattern p = Pattern
                .compile("^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,20}$");
        Matcher m = p.matcher(psd);

        return m.matches();
    }
}
