package com.guotai.mall.model;

import java.util.List;

public class Promotion {

    public int PromotionID;
    public int PromotionTypeID;
    public String Title;
    public String Description;
    public String Createtime;
    public String StartTime;
    public String EndTime;
    public String ImagePath;
    public List<PromotionImages> PromotionImages;
    public List<PromotionDetails> PromotionDetails;
}
