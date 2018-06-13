package com.guotai.mall.model;

import java.math.BigDecimal;

/**
 * Created by zhangpan on 17/11/1.
 */

public class OrderResult {

    public String OrderID;
    public String OrderSN;
    public String UserID;
    public String OrderTime;
    public String TotalPrice;
    public String TotalDiscountAmount;
    public String ProtectCostTypeID;
    public String ProtectCost;
    public String TranportFee;
    private float PayAmount;
    public String TotalQty;
    public String TotalWeight;
    public String OrderStatusID;
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
    public boolean IsDelete;

    public void setPayAmount(float PayAmount){
        this.PayAmount = PayAmount;
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
}
