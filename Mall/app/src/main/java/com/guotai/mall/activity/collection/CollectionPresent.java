package com.guotai.mall.activity.collection;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.CollectPro;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by ez on 2017/6/19.
 */

public class CollectionPresent implements IBasePresent {

    ICollectionactivity iCollectionactivity;

    public CollectionPresent(ICollectionactivity iCollectionactivity){
        this.iCollectionactivity = iCollectionactivity;
    }

    @Override
    public void destroy() {
        iCollectionactivity = null;
    }

    public void loaddata(String tag, String url) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort("获取收藏夹列表失败");
                if(iCollectionactivity!=null){
                    iCollectionactivity.refresh(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<CollectPro> list = Common.parseJsonArrayWithGson(response, CollectPro.class);
                if(iCollectionactivity!=null){
                    iCollectionactivity.refresh(list);
                }
            }
        }, tag);
    }

    public void GetDetail(String link, String tag) {
        HttpFactory.getInstance().AsyncGet(link, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort("获取商品信息失败");
                if(iCollectionactivity!=null){
                    iCollectionactivity.GotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);

                if(iCollectionactivity!=null){
                    iCollectionactivity.GotoDetail(productEx);
                }
            }
        }, tag);
    }

    public void delete(String url, String param, final int position, String tag){
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserID", Common.getUserID());
        map.put("CollectIDS", param);
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort("删除失败");
                if(iCollectionactivity!=null){
                    iCollectionactivity.delete(false, 0);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                Common.showToastShort("删除成功");
                if(iCollectionactivity!=null){
                    iCollectionactivity.delete(true, position);
                }
            }
        }, tag);
    }
}
