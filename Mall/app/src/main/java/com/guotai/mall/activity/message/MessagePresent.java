package com.guotai.mall.activity.message;

import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.model.Message;
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

            }

            @Override
            public void onResponse(Call call, String response) {
                List<Message> list = new ArrayList<Message>();
                Message message = new Message();
                message.mess = "尊敬的客户您好，为回馈新老客户支持，本公司“双十一”火爆大促销，全场低至五折，满1000包邮，且有iphone7、话费抽奖、更多惊喜等你来拿!";
                message.date = "2017-06-17 13:34:24";
                list.add(message);

                if(iMessageactivity!=null){
                    iMessageactivity.refresh(list);
                }
            }
        }, tag);
    }
}
