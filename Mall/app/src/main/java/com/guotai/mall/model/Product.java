package com.guotai.mall.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ez on 2017/6/16.
 */

public class Product {

    public static final int HOT =1;
    public static final int NEW =2;

    public String link;
    public String image;
    public String name;
    private float price;
    public int type;
    public List<String> images;

    public boolean isChoose;
    public String content;
    public int count;
    public String weight;
    public String date;

    public float getPrice(){
        float ft = price;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)ft);
        bd = bd.setScale(scale,roundingMode);
        ft = bd.floatValue();
        return ft;
    }

    public void setPrice(float Price){
        this.price = Price;
    }
}
