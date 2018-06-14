package com.guotai.mall.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alipay.sdk.app.EnvUtils;
import com.guotai.mall.MyApplication;
import com.guotai.mall.uitl.DialogUtils;
import com.guotai.mall.uitl.HttpFactory;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by ez on 2017/6/16.
 */

public class BaseActivity<T extends IBasePresent> extends AppCompatActivity {

    public T present;
    public DialogUtils dialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        dialogUtils = new DialogUtils();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if(MyApplication.getInstance().goCar==1){
            finish();
        }
        if(MyApplication.getInstance().goHome){
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(present!=null) {
            present.destroy();
            present = null;
        }
        dialogUtils.closeDialog();
        HttpFactory.getInstance().cancelCall(this.getClass().getSimpleName());
    }
}
