package com.guotai.mall.fragment.category;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Category;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;

import okhttp3.Call;

/**
 * Created by ez on 2017/6/19.
 */

public class CategoryPresent implements IBasePresent {

    ICategoryfragment iCategoryfragment;

    public CategoryPresent(ICategoryfragment iCategoryfragment){
        this.iCategoryfragment = iCategoryfragment;
    }

    @Override
    public void destroy() {
        iCategoryfragment = null;
    }

    public void loaddata(String url, String tag) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {

            }

            @Override
            public void onResponse(Call call, String response) {
                List<Category> list = Common.parseJsonArrayWithGson(response, Category.class);
                list.get(0).isSelected = true;

                if(iCategoryfragment!=null){
                    iCategoryfragment.refresh(list);
                }
            }
        }, tag);
    }

    public void search(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iCategoryfragment!=null){
                    iCategoryfragment.searchSuccess(null, false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<ProductEx> list = Common.parseJsonArrayWithGson(response, ProductEx.class);
                if(iCategoryfragment!=null){
                    iCategoryfragment.searchSuccess(list, true);
                }
            }
        }, tag);
    }
}
