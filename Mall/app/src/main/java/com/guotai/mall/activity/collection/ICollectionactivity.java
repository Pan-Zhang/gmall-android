package com.guotai.mall.activity.collection;

import com.guotai.mall.model.CollectPro;
import com.guotai.mall.model.Product;
import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by ez on 2017/6/19.
 */

public interface ICollectionactivity {

    void refresh(List<CollectPro> list);

    void GotoDetail(ProductEx product);

    void delete(boolean success, int position);

}
