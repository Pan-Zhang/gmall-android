package com.guotai.mall.fragment.home;

import com.guotai.mall.model.News;
import com.guotai.mall.model.Product;
import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by ez on 2017/6/16.
 */

public interface IHomeFragment {

    void refresh(List<ProductEx> list, List<News> list_news, boolean more);

    void GotoDetail(ProductEx product);
}
