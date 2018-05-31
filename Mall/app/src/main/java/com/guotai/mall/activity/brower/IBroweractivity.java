package com.guotai.mall.activity.brower;

import com.guotai.mall.model.CollectPro;
import com.guotai.mall.model.Product;
import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by zhangpan on 17/6/27.
 */

public interface IBroweractivity {

    void refresh(List<CollectPro> list);

    void GotoDetail(ProductEx product);
}
