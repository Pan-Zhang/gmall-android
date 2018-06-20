package com.guotai.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.guotai.mall.R;

/**
 * Created by zhangpan on 2018/6/20.
 */

public class FullDialog extends Dialog {

    public FullDialog(@NonNull Context context) {
        super(context, R.style.BottomAnimDialogStyle);
    }

    public FullDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected FullDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }
}
