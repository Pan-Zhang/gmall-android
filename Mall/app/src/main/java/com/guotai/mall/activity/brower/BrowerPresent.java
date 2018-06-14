package com.guotai.mall.activity.brower;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.CollectPro;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;
import java.util.List;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/6/27.
 */

public class BrowerPresent implements IBasePresent {

    IBroweractivity iBroweractivity;

    public BrowerPresent(IBroweractivity iBroweractivity){
        this.iBroweractivity = iBroweractivity;
    }

    @Override
    public void destroy() {
        iBroweractivity = null;
    }

    public void loaddata(String tag, String url) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort("获取阅览列表失败");
                if(iBroweractivity!=null){
                    iBroweractivity.refresh(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<CollectPro> list = Common.parseJsonArrayWithGson(response, CollectPro.class);
                if(iBroweractivity!=null){
                    iBroweractivity.refresh(list);
                }
            }
        }, tag);
    }

    public void GetDetail(String url, String tag) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iBroweractivity!=null){
                    iBroweractivity.GotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);

                if(iBroweractivity!=null){
                    iBroweractivity.GotoDetail(productEx);
                }
            }
        }, tag);
    }
}
