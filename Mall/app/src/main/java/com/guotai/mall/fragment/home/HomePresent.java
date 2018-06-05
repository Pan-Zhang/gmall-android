package com.guotai.mall.fragment.home;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.HotSpot;
import com.guotai.mall.model.HotspotInfoList;
import com.guotai.mall.model.News;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;
import com.guotai.mall.uitl.Common;

import java.util.List;

import okhttp3.Call;

/**
 * Created by ez on 2017/6/16.
 */

public class HomePresent implements IBasePresent {

    IHomeFragment iHomeFragment;

    public HomePresent(IHomeFragment fragment){
        iHomeFragment = fragment;
    }

    public void loadhead(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort("获取数据失败，请重新刷新");
            }

            @Override
            public void onResponse(Call call, String response) {
                List<HotSpot> list = Common.parseJsonArrayWithGson(response, HotSpot.class);
                if(iHomeFragment!=null)iHomeFragment.refresh(null, list, false);
            }
        }, tag);
    }

    public void loaddata(String url, final boolean more, String tag) {

        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort("获取数据失败，请重新刷新");
                if(iHomeFragment!=null)iHomeFragment.refresh(null, null, more);
            }

            @Override
            public void onResponse(Call call, String response) {

                try{
                    List<ProductEx> list = Common.parseJsonArrayWithGson(response, ProductEx.class);

                    if(iHomeFragment!=null)iHomeFragment.refresh(list, null, more);
                }catch (Exception e){

                }

            }
        }, tag);
    }

    public void GetDetail(String link, String tag) {
        HttpFactory.getInstance().AsyncGet(link, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iHomeFragment!=null){
                    iHomeFragment.GotoDetail(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {

                ProductEx productEx = Common.parseJsonWithGson(response, ProductEx.class);

                if(iHomeFragment!=null){
                   iHomeFragment.GotoDetail(productEx);
                }
            }
        }, tag);
    }

    public void getData(String url, final HotspotInfoList hotspotInfoList, String tag) {

        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                Common.showToastShort("获取数据失败，请重新刷新");
                if(iHomeFragment!=null)iHomeFragment.refresh(null, false, hotspotInfoList);
            }

            @Override
            public void onResponse(Call call, String response) {

                try{
                    List<ProductEx> list = Common.parseJsonArrayWithGson(response, ProductEx.class);

                    if(iHomeFragment!=null)iHomeFragment.refresh(list, true, hotspotInfoList);
                }catch (Exception e){

                }

            }
        }, tag);
    }


    @Override
    public void destroy() {
        iHomeFragment = null;
    }
}
