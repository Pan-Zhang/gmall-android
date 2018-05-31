package com.guotai.mall.activity.makeOrder;

import com.guotai.mall.model.Address;
import com.guotai.mall.model.LogisticFee;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.OrderResult;
import com.guotai.mall.model.Product;

import java.util.List;

/**
 * Created by zhangpan on 17/7/25.
 */

public interface IMakeOrderactivity {

    void uploadRes(boolean res, OrderEx result);
    void updateLogisticFee(boolean res, LogisticFee logisticFee);
    void updateAddress(List<Address> list);
}
