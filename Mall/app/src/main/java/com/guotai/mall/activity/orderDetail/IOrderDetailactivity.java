package com.guotai.mall.activity.orderDetail;

import com.guotai.mall.model.Address;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.model.ReturnReason;
import com.guotai.mall.model.WxParam;

import java.util.List;

/**
 * Created by zhangpan on 17/7/27.
 */

public interface IOrderDetailactivity {

    void wxPay(boolean sucess, WxParam wxParam, String reason);
    void alPay(boolean success, String param);
    void updateAddress(List<Address> list);
    void updateReason(List<ReturnReason> list);
    void requestBackRes(Boolean success);
    void alPayRes(boolean success, String reason);
    void ensureReceive(boolean success);
    void cancelBack(Boolean success, String mess);
    void GotoDetail(ProductEx productEx);
}
