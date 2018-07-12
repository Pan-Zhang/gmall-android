package com.guotai.mall.model;

/**
 * Created by zhangpan on 17/9/28.
 */

public class ProductImage {

    private String ImageID;
    private String ProductID;
    private String ProductSubID;
    private int ImageCategoryID;
    private String ImagePath;
    private String ImageTitle;
    private String DisplayStartTime;
    private String DisplayEndTime;
    private String DisplayFlag;
    private String UpdateTime;
    private String ImageCategoryName;

    public ProductImage() {
    }

    public ProductImage(String imageID, String productID, String productSubID, int imageCategoryID, String imagePath, String imageTitle, String displayStartTime, String displayEndTime, String displayFlag, String updateTime, String imageCategoryName) {
        ImageID = imageID;
        ProductID = productID;
        ProductSubID = productSubID;
        ImageCategoryID = imageCategoryID;
        ImagePath = imagePath;
        ImageTitle = imageTitle;
        DisplayStartTime = displayStartTime;
        DisplayEndTime = displayEndTime;
        DisplayFlag = displayFlag;
        UpdateTime = updateTime;
        ImageCategoryName = imageCategoryName;
    }

    public String getImageID() {
        return ImageID;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getProductSubID() {
        return ProductSubID;
    }

    public int getImageCategoryID() {
        return ImageCategoryID;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getImageTitle() {
        return ImageTitle;
    }

    public String getDisplayStartTime() {
        return DisplayStartTime;
    }

    public String getDisplayEndTime() {
        return DisplayEndTime;
    }

    public String getDisplayFlag() {
        return DisplayFlag;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public String getImageCategoryName() {
        return ImageCategoryName;
    }

    public void setImageID(String imageID) {
        ImageID = imageID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public void setProductSubID(String productSubID) {
        ProductSubID = productSubID;
    }

    public void setImageCategoryID(int imageCategoryID) {
        ImageCategoryID = imageCategoryID;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public void setImageTitle(String imageTitle) {
        ImageTitle = imageTitle;
    }

    public void setDisplayStartTime(String displayStartTime) {
        DisplayStartTime = displayStartTime;
    }

    public void setDisplayEndTime(String displayEndTime) {
        DisplayEndTime = displayEndTime;
    }

    public void setDisplayFlag(String displayFlag) {
        DisplayFlag = displayFlag;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public void setImageCategoryName(String imageCategoryName) {
        ImageCategoryName = imageCategoryName;
    }
}
