package com.guotai.mall.activity.message;

import com.guotai.mall.model.Message;
import com.guotai.mall.model.OrderMsg;
import com.guotai.mall.model.Sale;
import com.guotai.mall.model.SystemMsg;

import java.util.List;

/**
 * Created by zhangpan on 17/6/28.
 */

public interface IMessageactivity {

    void refresh(List<Message> list, boolean Success);

    void gotoSale(List<Sale> sales, boolean sucess);

    void getOrders(List<OrderMsg> list, boolean success);

    void getSystemMsg(List<SystemMsg> list, boolean success);
}
