package com.guotai.mall.fragment.buycar;

import com.guotai.mall.model.Address;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.Product;
import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by ez on 2017/6/16.
 */

public interface IBuycarfragment {
    void refresh(List<CarPro> list);
    void updateCount(boolean sucess, CarPro carPro, boolean isAdd);
    void delete(boolean delSuccess);
    void GotoDetail(ProductEx productEx);
    void updateAddress(List<Address> list);
}
