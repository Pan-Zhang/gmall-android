package com.guotai.mall.activity.managerAddress;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Address;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.List;
import java.util.Map;
import okhttp3.Call;

/**
 * Created by ez on 2017/6/20.
 */

public class ManagerPresent implements IBasePresent {

    IManageractivity iManageractivity;

    public ManagerPresent(IManageractivity iManageractivity){
        this.iManageractivity = iManageractivity;
    }

    @Override
    public void destroy() {
        iManageractivity = null;
    }

    public void loaddata(String url, String tag) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iManageractivity!=null){
                    iManageractivity.refresh(null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Address> list = Common.parseJsonArrayWithGson(response, Address.class);

                if(iManageractivity!=null){
                    iManageractivity.refresh(list);
                }
            }
        }, tag);
    }

    public void deleteAddress(String url, Map<String, String> map, final int position, String tag){
        HttpFactory.getInstance().AsyncPost(url, map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iManageractivity!=null){
                    iManageractivity.deleteSuccess(false, 0);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                if(iManageractivity!=null){
                    iManageractivity.deleteSuccess(true, position);
                }
            }
        }, tag);
    }
}
