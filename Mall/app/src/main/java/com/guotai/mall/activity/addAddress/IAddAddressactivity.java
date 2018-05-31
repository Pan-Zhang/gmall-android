package com.guotai.mall.activity.addAddress;

import com.guotai.mall.model.City;
import com.guotai.mall.model.County;
import com.guotai.mall.model.Province;

import java.util.List;

/**
 * Created by ez on 2017/6/20.
 */

public interface IAddAddressactivity {

    void refreshProvince(List<Province> list);
    void refreshCity(List<City> list);
    void refreshCounty(List<County> list);
    void addSuccess(boolean isSuccess);
}
