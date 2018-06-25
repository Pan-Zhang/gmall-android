package com.guotai.mall.activity.suggestDetail;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Suggestion;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/6/25.
 */

public class SuggestDetailPresent implements IBasePresent {

    ISuggestDetail iSuggestDetail;

    public SuggestDetailPresent(ISuggestDetail iSuggestDetail){
        this.iSuggestDetail = iSuggestDetail;
    }

    public void uploadSuggest(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iSuggestDetail!=null){
                    iSuggestDetail.uploadRes(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iSuggestDetail!=null){
                    iSuggestDetail.uploadRes(true);
                }
            }
        }, tag);
    }

    public void getSuggestDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iSuggestDetail!=null){
                    iSuggestDetail.updateSuggestDetail(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Suggestion suggestion = Common.parseJsonWithGson(response, Suggestion.class);
                if(iSuggestDetail!=null){
                    iSuggestDetail.updateSuggestDetail(true, suggestion);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iSuggestDetail = null;
    }
}
