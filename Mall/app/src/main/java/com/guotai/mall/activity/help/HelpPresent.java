package com.guotai.mall.activity.help;

import com.guotai.mall.base.IBasePresent;

/**
 * Created by zhangpan on 17/6/27.
 */

public class HelpPresent implements IBasePresent {

    IHelpactivity iHelpactivity;

    public HelpPresent(IHelpactivity iHelpactivity){
        this.iHelpactivity = iHelpactivity;
    }

    @Override
    public void destroy() {
        iHelpactivity = null;
    }
}
