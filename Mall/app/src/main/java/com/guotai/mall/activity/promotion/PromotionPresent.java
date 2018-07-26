package com.guotai.mall.activity.promotion;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.model.SubPromotion;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import okhttp3.Call;

public class PromotionPresent implements IBasePresent {

    IPromotionActivity iPromotionActivity;

    public PromotionPresent(IPromotionActivity iPromotionActivity){
        this.iPromotionActivity = iPromotionActivity;
    }

    public void getSubPromotion(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iPromotionActivity !=null){
                    iPromotionActivity.gotoSubDetail(null, false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                SubPromotion subPromotion = Common.parseJsonWithGson(response, SubPromotion.class);
                if(iPromotionActivity !=null){
                    iPromotionActivity.gotoSubDetail(subPromotion, true);
                }
            }
        }, tag);
    }

    public void GetDetail(String link, String tag) {
        HttpFactory.getInstance().AsyncGet(link, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iPromotionActivity!=null){
                    iPromotionActivity.GotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {

                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);

                if(iPromotionActivity!=null){
                    iPromotionActivity.GotoDetail(productEx);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iPromotionActivity = null;
    }
}
