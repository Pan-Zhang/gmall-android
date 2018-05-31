package com.guotai.mall.activity.find;

import com.guotai.mall.base.IBasePresent;

/**
 * Created by zhangpan on 17/6/28.
 */

public class FindpassPresent implements IBasePresent {

    IFindpassactivity iFindpassactivity;

    public FindpassPresent(IFindpassactivity iFindpassactivity){
        this.iFindpassactivity = iFindpassactivity;
    }

    @Override
    public void destroy() {
        iFindpassactivity = null;
    }
}
