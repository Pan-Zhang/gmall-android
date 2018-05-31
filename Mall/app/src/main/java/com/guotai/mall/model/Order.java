package com.guotai.mall.model;

import java.util.List;

/**
 * Created by zhangpan on 17/7/26.
 */

public class Order {

    public static final int PAY = 0;
    public static final int CANCELED = 1;
    public static final int BUY_AGAIN = 2;
    public static final int ENSURE_RECEIVE = 3;
    public static final int APPLY_RETURN = 4;
    public static final int DELETE = 5;

    public String orderNum;
    public ORDER_STATUS status;
    public List<CarPro> products;

    public enum ORDER_STATUS{
        UNPAY,  //未付款
        PAYED,  //已付款
        ENSURE_ORDER,  //确认订单
        PACKAGING_GOODS,  //商品打包中
        WAIT_LOGISTICS_RECEIPT, //等待物流收件
        WAIT_RECEIVE, //等待收获
        FINISH, //交易完成
        APPLY_EXHCANGE, //申请换货，等待审核
        WAIT_INSPECTION, //等待验货
        INSPECTING, //正在验货
        SELLER_WAIT_RECEIVE, //商家待收货
        APPLY_RETURN,  //申请退货，等待审核
        AGREEN_EXCHANGE, //换货审核通过
        AGREEN_RETURN, //退货审核通过
        SELLER_RECEIVED, //卖家已收货
        WAIT_REFUND, //等待退款
        FINISH_EXCHANGE, //换货已完成
        FINISH_RETURN //退货已完成
    }
}
