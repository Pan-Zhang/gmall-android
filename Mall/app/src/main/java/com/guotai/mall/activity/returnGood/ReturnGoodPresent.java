package com.guotai.mall.activity.returnGood;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.ReturnReason;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/5/21.
 */

public class ReturnGoodPresent implements IBasePresent {

    IReturnGoodActivity iReturnGoodActivity;

    public ReturnGoodPresent(IReturnGoodActivity iReturnGoodActivity){
        this.iReturnGoodActivity = iReturnGoodActivity;
    }

    public void getReason(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iReturnGoodActivity!=null){
                    iReturnGoodActivity.updateReason(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<ReturnReason> list = Common.parseJsonArrayWithGson(response, ReturnReason.class);
                if(iReturnGoodActivity!=null){
                    iReturnGoodActivity.updateReason(list);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iReturnGoodActivity = null;
    }
}
