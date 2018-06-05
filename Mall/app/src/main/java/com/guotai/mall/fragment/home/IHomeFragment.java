package com.guotai.mall.fragment.home;

import com.guotai.mall.model.HotSpot;
import com.guotai.mall.model.HotspotInfoList;
import com.guotai.mall.model.News;
import com.guotai.mall.model.Product;
import com.guotai.mall.model.ProductEx;

import java.util.List;

/**
 * Created by ez on 2017/6/16.
 */

public interface IHomeFragment {

    void refresh(List<ProductEx> list, List<HotSpot> list_news, boolean more);

    void refresh(List<ProductEx> list, boolean succ, HotspotInfoList hotspotInfoList);

    void GotoDetail(ProductEx product);
}
