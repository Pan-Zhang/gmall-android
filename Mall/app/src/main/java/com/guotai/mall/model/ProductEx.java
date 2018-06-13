package com.guotai.mall.model;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpan on 17/9/28.
 */

public class ProductEx {

    public String ProductID;
    public String ProductName;
    public String Description;
    private float CostPrice;
    private float SuggestPrice;
    private float Price;
    public String UnitID;
    public String UnitName;
    public String CategoryID;
    public String TotalQty;
    public String SoldQty;
    public int ProductStatusID;
    public String ProductStatusName;
    public String CreateTime;
    public String LastUpdatedTime;
    public String AllowStatus;
    public String IsPromoting;
    public String SortID;
    public String SellStartTime;
    public String SellEndTime;
    public String DiscountTypeID;
    public String BrandID;
    public String ListImage;
    public String IsHost;
    public String ViewQty;
    public String ProductCategoryName;
    public String ProductBrandName;
    public String FirstImage;
    public String ProductDiscount;
    public Map<String, String> ProductPubAttr;
    public Map<String, String> ProductAttrNames;
    public LinkedHashMap<String, Object> ProductFlexAttr;
    public List<ProductImage> ProductImage;
    public List<Map<String, Object>> ProductDetail;

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

    public float getCostPrice(){
        float ft = CostPrice;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public void setCostPrice(float CostPrice){
        this.CostPrice = CostPrice;
    }

    public float getSuggestPrice(){
        float ft = SuggestPrice;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public void setSuggestPrice(float SuggestPrice){
        this.SuggestPrice = SuggestPrice;
    }
}
