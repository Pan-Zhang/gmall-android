package com.guotai.mall.activity.suggest;

import com.guotai.mall.base.IBasePresent;

/**
 * Created by ez on 2017/6/20.
 */

public class SuggestPresent implements IBasePresent {

    ISuggestactivity iSuggestactivity;

    public SuggestPresent(ISuggestactivity iSuggestactivity){
        this.iSuggestactivity = iSuggestactivity;
    }

    @Override
    public void destroy() {
        iSuggestactivity = null;
    }
}
