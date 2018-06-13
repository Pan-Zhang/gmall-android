package com.guotai.mall.model;

import java.math.BigDecimal;
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
    private float TotalDiscountAmount;
    public String ProtectCostTypeID;
    public String ProtectCost;
    private float TranportFee;
    private float PayAmount;
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
    public Boolean IsRefund;
    public int RefundStatus;
    public List<OrderDetailEx> OrderDetailList;

    public float getTotalDiscountAmount(){
        float ft = TotalDiscountAmount;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public void setTotalDiscountAmount(float TotalDiscountAmount){
        this.TotalDiscountAmount = TotalDiscountAmount;
    }

    public float getTranportFee(){
        float ft = TranportFee;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public void setTranportFee(float TranportFee){
        this.TranportFee = TranportFee;
    }

    public float getPayAmount(){
        float ft = PayAmount;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public void setPayAmount(float PayAmount){
        this.PayAmount = PayAmount;
    }
}
