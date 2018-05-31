package com.guotai.mall.fragment.category;

import com.guotai.mall.model.Category;
import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by ez on 2017/6/19.
 */

public interface ICategoryfragment {

    void refresh(List<Category> list);

    void searchSuccess(List<ProductEx> list, boolean success);
}
