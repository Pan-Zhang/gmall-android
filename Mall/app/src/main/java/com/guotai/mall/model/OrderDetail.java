package com.guotai.mall.model;

import java.math.BigDecimal;

/**
 * Created by zhangpan on 17/10/31.
 */

public class OrderDetail {

    public String ProductID;
    public String ProductSubID;
    private float Price;
    public int Qty;

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
