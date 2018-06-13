package com.guotai.mall.activity.help;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/6/27.
 */

public class HelpPresent implements IBasePresent {

    IHelpactivity iHelpactivity;

    public HelpPresent(IHelpactivity iHelpactivity){
        this.iHelpactivity = iHelpactivity;
    }

    public void getData(String url, String tag){
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iHelpactivity!=null){
                    iHelpactivity.update(false, null);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                HelpData helpData = Common.parseJsonWithGson(response, HelpData.class);
                if(iHelpactivity!=null){
                    iHelpactivity.update(false, helpData);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {
        iHelpactivity = null;
    }

    public static class HelpData{
        public String WXNumber;
        public String KfEmai;
        public String KfTelephone;
        public String TransferDesc;
        public String LogisticsDesc;
        public String PaymentDesc;

    }
}
