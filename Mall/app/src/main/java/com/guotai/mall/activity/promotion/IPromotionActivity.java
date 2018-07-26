package com.guotai.mall.activity.promotion;

import com.guotai.mall.model.ProductEx;
import com.guotai.mall.model.SubPromotion;

public interface IPromotionActivity {

    void gotoSubDetail(SubPromotion subPromotion, boolean success);
    void GotoDetail(ProductEx productEx);

}
