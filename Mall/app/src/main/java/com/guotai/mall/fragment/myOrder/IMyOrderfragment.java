package com.guotai.mall.fragment.myOrder;

import com.guotai.mall.model.Address;
import com.guotai.mall.model.AfterSale;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderDetailEx;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by zhangpan on 17/7/26.
 */

public interface IMyOrderfragment {

    void Refresh(List<OrderEx> list);
    void RefreshMoreData(List<OrderEx> list);
    void cancelSuccess(Boolean success);
    void ensureReceive(boolean success);
    void deleteOrder(boolean success);
    void getLogistics(boolean success, Logistics logistics, int position, OrderEx orderEx);
    void getDetail(boolean success, OrderEx orderEx);
    void getSubDetail(boolean success, OrderDetailEx orderDetailEx);
    void updateAddress(List<Address> list, int position);
    void getAfterSaleDetailRes(boolean succ, AfterSale afterSale);
    void gotoDetail(ProductEx orderDetail);
}
