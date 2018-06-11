package com.guotai.mall.model;

import java.util.List;

/**
 * Created by zhangpan on 17/11/1.
 */

public class OrderEx extends Order {

    public String OrderID;
    public String OrderSN;
    public String UserID;
    public String OrderTime;
    public String TotalPrice;
    public String TotalDiscountAmount;
    public String ProtectCostTypeID;
    public String ProtectCost;
    public String TranportFee;
    public String PayAmount;
    public String TotalQty;
    public String TotalWeight;
    public int OrderStatusID;
    public String UserReceiverID;
    public String LogisticsID;
    public String LogisticsOrderID;
    public String PayWayID;
    public String PayID;
    public String Remark;
    public String DiscountTypeID;
    public String IsApplyAfterSale;
    public String ConfirmHandler;
    public String ConfirmTime;
    public String PackingHandler;
    public String PackingTime;
    public String SendHandler;
    public String SendTime;
    public String ConfirmReceiptTime;
    public String InvoiceFlag;
    public String InvoiceName;
    public String DeleteTime;
    public String IsDelete;
    public String OrderStatusName;
    public String PayWayName;
    public String ReceiverName;
    public String StateName;
    public String ProvinceID;
    public String ProvinceName;
    public String CityID;
    public String CityName;
    public String DistrictID;
    public String DistrictName;
    public String ReceiverAddress;
    public String NationCode;
    public String ReceiverMobile;
    public int IsReturnExpired;
    public int IsExchangeExpired;
    public int IsAllowRefund;
    public List<OrderDetailEx> OrderDetailList;
}
