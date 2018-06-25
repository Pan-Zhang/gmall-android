package com.guotai.mall.activity.suggestDetail;

import com.guotai.mall.model.Suggestion;

/**
 * Created by zhangpan on 2018/6/22.
 */

public interface ISuggestDetail {

    void uploadRes(boolean success);
    void updateSuggestDetail(boolean success, Suggestion suggestion);
}
