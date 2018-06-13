package com.guotai.mall.model;

import java.math.BigDecimal;

/**
 * Created by zhangpan on 17/11/1.
 */

public class OrderDetailEx {

    public String OrderSubID;
    public String OrderSN;
    public String OrderID;
    public String OrderSubSN;
    public String ProductID;
    public String ProductSubID;
    private float Price;
    public int Qty;
    public String DiscountAmount;
    public String Remark;
    public String DiscountTypeID;
    public String ProductName;
    public String ProductDescription;
    public String ProductCostPrice;
    public String ProductSuggestPrice;
    public String ProductPrice;
    public String ProductUnitID;
    public String ProductUnitName;
    public String ProductCategoryID;
    public String ProductTotalQty;
    public String ProductSoldQty;
    public String ProductStatusID;
    public String ProductStatusName;
    public String ProductCreateTime;
    public String ProductLastUpdatedTime;
    public String ProductAllowStatus;
    public String ProductIsPromoting;
    public String ProductSortID;
    public String ProductSellStartTime;
    public String ProductSellEndTime;
    public String ProductDiscountTypeID;
    public String ProductBrandID;
    public String ProductIsHost;
    public String ProductViewQty;
    public String ProductCategoryName;
    public String ProductDiscountTypeName;
    public String ProductBrandName;
    public String FirstImage;
    public String ProductSN;
    public String BarCode;
    public String QRCode;
    public String MaterialID;
    public String MaterialName;
    public String ColorID;
    public String ColorName;
    public String ClarityID;
    public String ClarityName;
    public String RingSizeID;
    public String RingSizeName;
    public String CuttingID;
    public String CuttingName;
    public String ShapeID;
    public String ShapeName;
    public String Length;
    public String Width;
    public String Height;
    public String StoreQty;
    public String Weight;
    public String CostPrice;
    public String SuggestPrice;
    public String ProductDetailPrice;
    public String UnitID;
    public String UnitName;
    public String CreateTime;
    public String LastUpdatedTime;
    public String AfterSaleOrderID;
    public String AfterSaleStatusName;
    public int IsAfterSale;
    public int IsReturnExpired;
    public int IsExchangeExpired;

    public float getPrice(){
        float ft = Price;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public void setPrice(float Price){
        this.Price = Price;
    }

}
