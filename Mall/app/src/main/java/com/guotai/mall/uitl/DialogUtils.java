package com.guotai.mall.uitl;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guotai.mall.R;

/**
 * Created by ez on 2017/6/19.
 */

public class DialogUtils {

    Dialog loadingDialog;
    ImageView spaceshipImage;
    Animation hyperspaceJumpAnimation;
    TextView tipTextView;

    public void showWaitDialog(Context context){
        this.showWaitDialog(context, context.getResources().getString(R.string.str_loading_data));
    }

    public void showWaitDialog(Context context, String msg){
        this.showWaitDialog(context, msg, false, true);
    }

    public void showWaitDialog(Context context, String msg, boolean isTransBg, boolean isCancelable) {
        if(loadingDialog==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.layout_loading, null);             // 得到加载view
            LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局

            // main.xml中的ImageView
            spaceshipImage = (ImageView) v.findViewById(R.id.img);
            tipTextView = (TextView) v.findViewById(R.id.tipTextView);   // 提示文字
            // 加载动画
            hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_animation);
            // 使用ImageView显示动画

            loadingDialog = new Dialog(context, isTransBg ? R.style.TransDialogStyle : R.style.WhiteDialogStyle);    // 创建自定义样式dialog
            loadingDialog.setContentView(layout);
            loadingDialog.setCancelable(isCancelable);
            loadingDialog.setCanceledOnTouchOutside(false);

            Window window = loadingDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setAttributes(lp);
            window.setWindowAnimations(R.style.PopWindowAnimStyle);
        }
        tipTextView.setText(msg);// 设置加载信息
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        loadingDialog.show();
    }

    public void disMiss(){
        if(loadingDialog!=null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    /**
     * 关闭dialog
     */
    public void closeDialog() {
        if(spaceshipImage!=null){
            spaceshipImage.clearAnimation();
        }
    }
}
