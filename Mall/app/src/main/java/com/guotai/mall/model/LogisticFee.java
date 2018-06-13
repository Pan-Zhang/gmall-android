package com.guotai.mall.model;

import java.math.BigDecimal;

/**
 * Created by zhangpan on 2018/4/30.
 */

public class LogisticFee {

    public String LogisticsID;
    public String LogisticsName;
    private float TranportFee;
    public String IsDefault;

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
}
