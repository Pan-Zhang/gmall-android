package com.guotai.mall.activity.sale;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Promotion;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import okhttp3.Call;

public class SalePresent implements IBasePresent {

    ISaleActivity iSaleActivity;

    public SalePresent(ISaleActivity iSaleActivity){
        this.iSaleActivity = iSaleActivity;
    }

    public void getPromotionDetail(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iSaleActivity!=null){
                    iSaleActivity.getPromotion(null, false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Promotion promotion = Common.parseJsonWithGson(response, Promotion.class);
                if(iSaleActivity!=null){
                    iSaleActivity.getPromotion(promotion, true);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iSaleActivity = null;
    }
}
