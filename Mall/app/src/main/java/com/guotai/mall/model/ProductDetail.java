package com.guotai.mall.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangpan on 17/9/28.
 */

public class ProductDetail {

    private String ProductSubID;
    private String ProductID;
    private String ProductSN;
    private String BarCode;
    private String QRCode;
    private String ColorID;
    private String ColorName;
    private String MaterialID;
    private String MaterialName;
    private String ClarityID;
    private String ClarityName;
    private String RingSizeID;
    private String RingSizeName;
    private String CuttingID;
    private String CuttingName;
    private String ShapeID;
    private String ShareName;
    private String Length;
    private String Width;
    private String Height;
    private String Qty;
    private String Weight;
    private float CostPrice;
    private float SuggestPrice;
    private float Price;
    private String UnitID;
    private String UnitName;
    private String CreateTime;
    private String LastUpdateTime;
    private String DiscountTypeID;
    private String DetailDiscount;
    private String WeightText;
    private List<ProductImage> ProductImage;

    public String getProductSubID() {
        return ProductSubID;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getProductSN() {
        return ProductSN;
    }

    public String getBarCode() {
        return BarCode;
    }

    public String getQRCode() {
        return QRCode;
    }

    public String getColorID() {
        return ColorID;
    }

    public String getColorName() {
        return ColorName;
    }

    public String getMaterialID() {
        return MaterialID;
    }

    public String getMaterialName() {
        return MaterialName;
    }

    public String getClarityID() {
        return ClarityID;
    }

    public String getClarityName() {
        return ClarityName;
    }

    public String getRingSizeID() {
        return RingSizeID;
    }

    public String getRingSizeName() {
        return RingSizeName;
    }

    public String getCuttingID() {
        return CuttingID;
    }

    public String getCuttingName() {
        return CuttingName;
    }

    public String getShapeID() {
        return ShapeID;
    }

    public String getShareName() {
        return ShareName;
    }

    public String getLength() {
        return Length;
    }

    public String getWidth() {
        return Width;
    }

    public String getHeight() {
        return Height;
    }

    public String getQty() {
        return Qty;
    }

    public String getWeight() {
        return Weight;
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

    public float getSuggestPrice(){
        float ft = SuggestPrice;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public float getPrice(){
        float ft = Price;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public String getUnitID() {
        return UnitID;
    }

    public String getUnitName() {
        return UnitName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getLastUpdateTime() {
        return LastUpdateTime;
    }

    public String getDiscountTypeID() {
        return DiscountTypeID;
    }

    public String getDetailDiscount() {
        return DetailDiscount;
    }

    public List<com.guotai.mall.model.ProductImage> getProductImage() {
        return ProductImage;
    }

    public void setProductSubID(String productSubID) {
        ProductSubID = productSubID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public void setProductSN(String productSN) {
        ProductSN = productSN;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public void setColorID(String colorID) {
        ColorID = colorID;
    }

    public void setColorName(String colorName) {
        ColorName = colorName;
    }

    public void setMaterialID(String materialID) {
        MaterialID = materialID;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public void setClarityID(String clarityID) {
        ClarityID = clarityID;
    }

    public void setClarityName(String clarityName) {
        ClarityName = clarityName;
    }

    public void setRingSizeID(String ringSizeID) {
        RingSizeID = ringSizeID;
    }

    public void setRingSizeName(String ringSizeName) {
        RingSizeName = ringSizeName;
    }

    public void setCuttingID(String cuttingID) {
        CuttingID = cuttingID;
    }

    public void setCuttingName(String cuttingName) {
        CuttingName = cuttingName;
    }

    public void setShapeID(String shapeID) {
        ShapeID = shapeID;
    }

    public void setShareName(String shareName) {
        ShareName = shareName;
    }

    public void setLength(String length) {
        Length = length;
    }

    public void setWidth(String width) {
        Width = width;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public void setCostPrice(float costPrice) {
        CostPrice = costPrice;
    }

    public void setSuggestPrice(float suggestPrice) {
        SuggestPrice = suggestPrice;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        LastUpdateTime = lastUpdateTime;
    }

    public void setDiscountTypeID(String discountTypeID) {
        DiscountTypeID = discountTypeID;
    }

    public void setDetailDiscount(String detailDiscount) {
        DetailDiscount = detailDiscount;
    }

    public void setProductImage(List<com.guotai.mall.model.ProductImage> productImage) {
        ProductImage = productImage;
    }

    public String getWeightText() {
        return WeightText;
    }

    public void setWeightText(String weightText) {
        WeightText = weightText;
    }
}
