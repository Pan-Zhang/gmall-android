package com.guotai.mall.activity.complaintDetail;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Complaint;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/6/25.
 */

public class ComplaintDetailPresent implements IBasePresent {

    IComplaintDetailActivity iComplaintDetailActivity;

    public ComplaintDetailPresent(IComplaintDetailActivity iComplaintDetailActivity){
        this.iComplaintDetailActivity = iComplaintDetailActivity;
    }

    public void uploadComplaint(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iComplaintDetailActivity!=null){
                    iComplaintDetailActivity.uploadRes(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iComplaintDetailActivity!=null){
                    iComplaintDetailActivity.uploadRes(true);
                }
            }
        }, tag);
    }

    public void getComplaintDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iComplaintDetailActivity!=null){
                    iComplaintDetailActivity.updateComplaint(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Complaint complaint = Common.parseJsonWithGson(response, Complaint.class);
                if(iComplaintDetailActivity!=null){
                    iComplaintDetailActivity.updateComplaint(true, complaint);
                }
            }
        }, tag);
    }


    @Override
    public void destroy() {
        iComplaintDetailActivity = null;
    }
}
