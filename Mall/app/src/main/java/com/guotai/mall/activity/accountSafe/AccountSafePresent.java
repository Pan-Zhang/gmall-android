package com.guotai.mall.activity.accountSafe;

import com.guotai.mall.base.IBasePresent;

/**
 * Created by zhangpan on 17/8/3.
 */

public class AccountSafePresent implements IBasePresent {

    IAccountSafeactivity iAccountSafeactivity;

    public AccountSafePresent(IAccountSafeactivity iAccountSafeactivity){
        this.iAccountSafeactivity = iAccountSafeactivity;
    }

    @Override
    public void destroy() {
        iAccountSafeactivity = null;
    }
}
