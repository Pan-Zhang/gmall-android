package com.guotai.mall.activity.message;

import com.guotai.mall.model.Message;

import java.util.List;

/**
 * Created by zhangpan on 17/6/28.
 */

public interface IMessageactivity {

    void refresh(List<Message> list, boolean Success);
}
