package com.guotai.mall.activity.suggest;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by ez on 2017/6/20.
 */

public class SuggestPresent implements IBasePresent {

    ISuggestactivity iSuggestactivity;

    public SuggestPresent(ISuggestactivity iSuggestactivity){
        this.iSuggestactivity = iSuggestactivity;
    }

    public void Request(String url, Map<String, String> map, Map<String, String> map_file, String tag){
        HttpFactory.getInstance().UploadImage(url, map, map_file, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iSuggestactivity!=null){
                    iSuggestactivity.updateSuccess(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iSuggestactivity!=null){
                    iSuggestactivity.updateSuccess(true);
                }
            }
        }, tag);
    }
    @Override
    public void destroy() {
        iSuggestactivity = null;
    }
}
