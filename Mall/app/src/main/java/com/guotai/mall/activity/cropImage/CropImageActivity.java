package com.guotai.mall.activity.cropImage;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 2018/6/28.
 */

public class CropImageActivity extends BaseActivity {

    CropImageView cropImageView;
    public static Uri uri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        initTitle();

        cropImageView = findViewById(R.id.cropImageView);
        cropImageView.setImageUriAsync(uri);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uri = null;
    }

    public void initTitle(){
        ImageView left_iv = (ImageView) findViewById(R.id.left_iv);
        left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        left_iv.setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("编辑图片");
        TextView right_text = (TextView) findViewById(R.id.right_text);
        right_text.setVisibility(View.VISIBLE);
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cropImageView==null){
                    Intent i=new Intent();
                    setResult(-1,i);
                }
                else{
                    Bitmap cropped = cropImageView.getCroppedImage();
                    File file = Common.compressImage(cropped);
                    Intent i=new Intent();
                    i.putExtra("CROP_PATH", file.getAbsolutePath());
                    setResult(2,i);
                }
                finish();
            }
        });
        right_text.setText("确定");
    }
}
