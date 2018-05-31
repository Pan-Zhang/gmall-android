package com.guotai.mall.activity.payed;

import com.guotai.mall.model.LogisticFee;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderEx;

/**
 * Created by zhangpan on 2018/5/10.
 */

public interface IPayedActivity {

    void getDetail(boolean success, OrderEx orderEx);
    void getLogistics(boolean success, Logistics logistics, int position, OrderEx orderEx);

}
