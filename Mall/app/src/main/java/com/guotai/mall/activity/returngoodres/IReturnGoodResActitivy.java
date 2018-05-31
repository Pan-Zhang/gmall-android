package com.guotai.mall.activity.returngoodres;

import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.ReturnReason;

import java.util.List;

/**
 * Created by zhangpan on 2018/4/26.
 */

public interface IReturnGoodResActitivy {

    void LogisticRes(Boolean success, Logistics logistics);

    void doNext(Boolean res, String mess);

    void updateReason(List<ReturnReason> list);

    void requestBackRes(Boolean success);
}
