package com.guotai.mall.activity.product;


import com.guotai.mall.model.Address;
import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by ez on 2017/6/16.
 */

public interface IProductactivity {

    void refresh(ProductEx product);

    void addCar(boolean success);

    void updateAddress(List<Address> list);

}
