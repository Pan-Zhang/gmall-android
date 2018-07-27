package com.guotai.mall.activity.orderMsg;

import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderEx;

public interface IOrderMsgActivity {

    void getDetail(boolean success, OrderEx orderEx);
    void getLogistics(boolean success, Logistics logistics, int position, OrderEx orderEx);

}
