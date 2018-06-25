package com.guotai.mall.activity.mySuggest;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Complaint;
import com.guotai.mall.model.Suggestion;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/6/22.
 */

public class MySuggestPresent implements IBasePresent {

    IMysuggestActivity iMysuggestActivity;

    public MySuggestPresent(IMysuggestActivity iMysuggestActivity){
        this.iMysuggestActivity = iMysuggestActivity;
    }

    public void getSuggest(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMysuggestActivity!=null){
                    iMysuggestActivity.updateSuggest(false, null, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Suggestion> list = Common.parseJsonArrayWithGson(response, Suggestion.class);
                if(iMysuggestActivity!=null){
                    iMysuggestActivity.updateSuggest(true, list, "");
                }
            }
        }, tag);
    }

    public void getComplaint(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMysuggestActivity!=null){
                    iMysuggestActivity.updateComplaint(false, null, e);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Complaint> list = Common.parseJsonArrayWithGson(response, Complaint.class);
                if(iMysuggestActivity!=null){
                    iMysuggestActivity.updateComplaint(true, list, "");
                }
            }
        }, tag);
    }

    public void getSuggestDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMysuggestActivity!=null){
                    iMysuggestActivity.gotoSuggestDetail(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Suggestion suggestion = Common.parseJsonWithGson(response, Suggestion.class);
                if(iMysuggestActivity!=null){
                    iMysuggestActivity.gotoSuggestDetail(true, suggestion);
                }
            }
        }, tag);
    }

    public void getComplaintDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMysuggestActivity!=null){
                    iMysuggestActivity.gotoComplaint(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Complaint complaint = Common.parseJsonWithGson(response, Complaint.class);
                if(iMysuggestActivity!=null){
                    iMysuggestActivity.gotoComplaint(true, complaint);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iMysuggestActivity = null;
    }
}
