package com.guotai.mall.activity.splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.guotai.mall.MainActivity;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.uitl.SharedPreferencesUtils;

/**
 * Created by zhangpan on 17/10/16.
 */

public class SplashActivity extends BaseActivity<SplashPresent> implements ISplashActivity {

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        present = new SplashPresent(this);
        String version="";
        PackageManager pm = getPackageManager();//context为当前Activity上下文
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(this.getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(TextUtils.isEmpty(version) || !version.equals(SharedPreferencesUtils.getParam(this, "versionName", ""))){
            SharedPreferencesUtils.setParam(this, "versionName", version);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, DirectActivity.class));
                    finish();
                }
            }, 200);
        }
        else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 200);
        }
    }
}
