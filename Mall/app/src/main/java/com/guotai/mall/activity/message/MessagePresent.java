package com.guotai.mall.activity.message;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Message;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/6/28.
 */

public class MessagePresent implements IBasePresent {

    IMessageactivity iMessageactivity;

    public MessagePresent(IMessageactivity iMessageactivity){
        this.iMessageactivity = iMessageactivity;
    }

    @Override
    public void destroy() {
        iMessageactivity = null;
    }

    public void getData(String tag, String url) {
        HttpFactory.getInstance().AsyncGet(url, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMessageactivity!=null){
                    iMessageactivity.refresh(null, false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                List<Message> list = Common.parseJsonArrayWithGson(response, Message.class);

                if(iMessageactivity!=null){
                    iMessageactivity.refresh(list, true);
                }
            }
        }, tag);
    }
}
