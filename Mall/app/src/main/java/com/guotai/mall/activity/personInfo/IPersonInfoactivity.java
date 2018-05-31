package com.guotai.mall.activity.personInfo;

/**
 * Created by zhangpan on 17/8/3.
 */

public interface IPersonInfoactivity {

    void refreshSuccess(Boolean success, int position, String mess);
    void sendCode(boolean success, String mess);

}
