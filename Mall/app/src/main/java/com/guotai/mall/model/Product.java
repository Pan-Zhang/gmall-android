package com.guotai.mall.model;

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
    public String price;
    public int type;
    public List<String> images;

    public boolean isChoose;
    public String content;
    public int count;
    public String weight;
    public String date;
}
