package com.guotai.mall.model;

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
    public String CostPrice;
    public String SuggestPrice;
    public String Price;
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
    
}
