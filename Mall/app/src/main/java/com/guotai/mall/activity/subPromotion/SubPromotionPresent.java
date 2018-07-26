package com.guotai.mall.activity.subPromotion;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import okhttp3.Call;

public class SubPromotionPresent implements IBasePresent {

    ISubPromotion iSubPromotion;

    public SubPromotionPresent(ISubPromotion iSubPromotion){
        this.iSubPromotion = iSubPromotion;
    }

    public void GetDetail(String link, String tag) {
        HttpFactory.getInstance().AsyncGet(link, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iSubPromotion!=null){
                    iSubPromotion.GotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {

                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);

                if(iSubPromotion!=null){
                    iSubPromotion.GotoDetail(productEx);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iSubPromotion = null;
    }
}
