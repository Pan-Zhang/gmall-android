package com.guotai.mall.activity.search;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/7/26.
 */

public class SearchPresent implements IBasePresent {

    ISearchactivity iSearchactivity;

    public SearchPresent(ISearchactivity iSearchactivity){
        this.iSearchactivity = iSearchactivity;
    }

    public void search(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iSearchactivity!=null){
                    iSearchactivity.searchSuccess(null, false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<ProductEx> list = Common.parseJsonArrayWithGson(response, ProductEx.class);
                if(iSearchactivity!=null){
                    iSearchactivity.searchSuccess(list, true);
                }
            }
        }, tag);
    }

    public void GetDetail(String link, String tag) {
        HttpFactory.getInstance().AsyncGet(link, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if (iSearchactivity != null) {
                    iSearchactivity.GotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {

                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);

                if (iSearchactivity != null) {
                    iSearchactivity.GotoDetail(productEx);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iSearchactivity = null;
    }
}
