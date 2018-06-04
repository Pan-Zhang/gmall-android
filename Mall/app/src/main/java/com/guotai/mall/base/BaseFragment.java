package com.guotai.mall.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.sdk.app.EnvUtils;
import com.guotai.mall.uitl.DialogUtils;
import com.guotai.mall.uitl.HttpFactory;

/**
 * Created by ez on 2017/6/16.
 */

public class BaseFragment<T extends IBasePresent> extends Fragment {
    public T present;
    public DialogUtils dialogUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dialogUtils = new DialogUtils();
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(present!=null) {
            present.destroy();
            present = null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialogUtils.closeDialog();
        HttpFactory.getInstance().cancelCall(this.getClass().getSimpleName());
    }
}
