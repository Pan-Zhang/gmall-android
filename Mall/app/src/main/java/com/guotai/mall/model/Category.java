package com.guotai.mall.model;

import java.util.List;

/**
 * Created by ez on 2017/6/19.
 */

public class Category {

    public String name;
    public boolean isSelected;

    public String CategoryID;
    public String CategoryName;
    public String BelongCategoryID;
    public String ImagePath;
    public List<Category> children;

}
