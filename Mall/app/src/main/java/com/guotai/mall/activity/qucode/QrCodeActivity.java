package com.guotai.mall.activity.qucode;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.uitl.Common;
import com.yanzhenjie.zbar.camera.CameraPreview;
import com.yanzhenjie.zbar.camera.ScanCallback;

/**
 * Created by zhangpan on 2018/5/29.
 */

public class QrCodeActivity extends BaseActivity {

    private RelativeLayout mScanCropView;
    private ImageView mScanLine;
    private ValueAnimator mScanAnimator;

    TextView title;

    private CameraPreview mPreviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        initTitle();

        mPreviewView = (CameraPreview) findViewById(R.id.capture_preview);
        mScanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        mScanLine = (ImageView) findViewById(R.id.capture_scan_line);

        mPreviewView.setScanCallback(resultCallback);

        if (mScanAnimator == null) {
            int height = Common.dip2px(this, 250);
            mScanAnimator = ObjectAnimator.ofFloat(mScanLine, "translationY", 0F, height).setDuration(3000);
            mScanAnimator.setInterpolator(new LinearInterpolator());
            mScanAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mScanAnimator.setRepeatMode(ValueAnimator.REVERSE);

            startScan();
        }
    }

    private void initTitle() {
        ImageView left_iv = (ImageView) findViewById(R.id.left_iv);
        left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        left_iv.setVisibility(View.VISIBLE);
        title = (TextView) findViewById(R.id.title);
        title.setText(R.string.str_scan);
    }

    /**
     * 打开相机并开始扫描。
     */
    private void startScan() {
        if (mPreviewView.start()) {
            mScanAnimator.start();
        }
    }

    /**
     * 停止扫描并关闭相机。
     */
    private void stopScan() {
        mScanAnimator.cancel();
        mPreviewView.stop();
    }

    /**
     * 监听结果。
     */
    private ScanCallback resultCallback = new ScanCallback() {
        @Override
        public void onScanResult(String result) {
            stopScan();
            new AlertDialog.Builder(QrCodeActivity.this).setTitle(result).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startScan();
                    dialog.dismiss();
                }
            }).create().show();
        }
    };

    @Override
    protected void onPause() {
        // 必须在这里停止扫描释放相机。
        stopScan();
        super.onPause();
    }
}
