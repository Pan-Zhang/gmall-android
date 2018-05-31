package com.guotai.mall.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.guotai.mall.MainActivity;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;

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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, DirectActivity.class));
                finish();
            }
        }, 200);
    }
}
