package com.guotai.mall.activity.splash;

import com.guotai.mall.base.IBasePresent;

/**
 * Created by zhangpan on 17/10/16.
 */

public class SplashPresent implements IBasePresent{

    ISplashActivity iSplashActivity;

    public SplashPresent(ISplashActivity iSplashActivity){
        this.iSplashActivity = iSplashActivity;
    }

    @Override
    public void destroy() {
        iSplashActivity = null;
    }
}
