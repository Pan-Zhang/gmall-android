package com.guotai.mall.activity.changePw;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/11/21.
 */

public class ChangePwPresent implements IBasePresent {

    IChangePwActivity iChangePwActivity;

    public ChangePwPresent(IChangePwActivity iChangePwActivity){
        this.iChangePwActivity = iChangePwActivity;
    }

    public void changePw(String url, Map<String, String> map, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iChangePwActivity!=null){
                    iChangePwActivity.changePw(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iChangePwActivity!=null){
                    iChangePwActivity.changePw(true);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iChangePwActivity = null;
    }
}
