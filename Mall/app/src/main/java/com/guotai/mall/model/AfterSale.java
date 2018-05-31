package com.guotai.mall.model;

import java.util.List;

/**
 * Created by zhangpan on 2018/3/26.
 */

public class AfterSale {

    public String AfterSaleOrderID;
    public String OrderID;
    public String OrderSubID;
    public int AfterSaleTypeID;
    public int AfterSaleQty;
    public float OriginalPrice;
    public float DiscountAmount;
    public float ReturnAmount;
    public float ReturnTranportFee;
    public int AfterSaleStatusID;
    public String AfterSaleRemark;
    public String CreateTime;
    public int ReturnReasonID;
    public int ReturnAddressID;
    public String DetailStatusName;
    public String PromtMessage;
    public String AfterSaleStatusName;
    public String ReturnReasonName;
    public String AfterSaleTypeName;
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
    public List<OrderDetailEx> OrderDetailList;
    public List<AfterSalePic> ImageList;

    public static class AfterSalePic{
        public String ImageID;
        public String AfterSaleOrderID;
        public String ImagePath;
    }

}
