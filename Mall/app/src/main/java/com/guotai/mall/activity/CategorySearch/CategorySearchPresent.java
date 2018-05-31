package com.guotai.mall.activity.CategorySearch;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.fragment.category.CategoryFragment;
import com.guotai.mall.fragment.category.ICategoryfragment;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/4/24.
 */

public class CategorySearchPresent implements IBasePresent {

     ICategorySearch iCategorySearch;

    public CategorySearchPresent(ICategorySearch iCategoryfragment){
        this.iCategorySearch = iCategoryfragment;
    }

    public void GetDetail(String link, String tag) {
        HttpFactory.getInstance().AsyncGet(link, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iCategorySearch!=null){
                    iCategorySearch.GotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {

                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);

                if(iCategorySearch!=null){
                    iCategorySearch.GotoDetail(productEx);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iCategorySearch = null;
    }
}
