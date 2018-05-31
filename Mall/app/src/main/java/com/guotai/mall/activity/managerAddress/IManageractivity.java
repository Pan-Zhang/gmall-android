package com.guotai.mall.activity.managerAddress;

import com.guotai.mall.model.Address;

import java.util.List;

/**
 * Created by ez on 2017/6/20.
 */

public interface IManageractivity {

    void refresh(List<Address> list);
    void deleteSuccess(boolean success, int position);
}
