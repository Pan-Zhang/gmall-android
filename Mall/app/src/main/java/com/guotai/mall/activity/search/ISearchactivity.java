package com.guotai.mall.activity.search;

import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by zhangpan on 17/7/26.
 */

public interface ISearchactivity {
    void searchSuccess(List<ProductEx> list, boolean success);
    void GotoDetail(ProductEx productEx);
}
