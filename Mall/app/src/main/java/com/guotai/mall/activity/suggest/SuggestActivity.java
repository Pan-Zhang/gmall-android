package com.guotai.mall.activity.suggest;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.activity.returnGood.ReturnGoodActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Complaint;
import com.guotai.mall.uitl.Common;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ez on 2017/6/20.
 */

public class SuggestActivity extends BaseActivity<SuggestPresent> implements ISuggestactivity, View.OnClickListener, View.OnLongClickListener{

    TextView advice_tv, advice_line, complain_tv, complain_line;
    EditText advice_et, complain_et, email;
    String mFilePath, mFile;
    Button submit;
    ImageView image1, image2, image3, choose_pic;
    List<String> list_pic;
    int i=0;
    int type = 0;
    private static final int REQUEST_CAMERA = 0;
    private static final int FROM_PIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        mFilePath = Environment.getExternalStorageDirectory().getPath()+ "/test";// 获取SD卡路径

        present = new SuggestPresent(this);
        initTitle();
        init();
    }

    private void init() {
        advice_tv = (TextView) findViewById(R.id.advice_tv);
        advice_tv.setOnClickListener(this);
        advice_line = (TextView) findViewById(R.id.advice_line);
        complain_tv = (TextView) findViewById(R.id.complain_tv);
        complain_tv.setOnClickListener(this);
        complain_line = (TextView) findViewById(R.id.complain_line);
        advice_et = (EditText) findViewById(R.id.advice_et);
        complain_et = (EditText) findViewById(R.id.complain_et);
        email = (EditText) findViewById(R.id.email_tv);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);

        list_pic = new ArrayList<>();

        image1 = (ImageView) findViewById(R.id.image1);
        image1.setOnLongClickListener(this);
        image2 = (ImageView) findViewById(R.id.image2);
        image2.setOnLongClickListener(this);
        image3 = (ImageView) findViewById(R.id.image3);
        image3.setOnLongClickListener(this);
        choose_pic = (ImageView) findViewById(R.id.choose_pic);
        choose_pic.setOnClickListener(this);
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
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.str_suggest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(list_pic==null || list_pic.size()==0){
            image1.setVisibility(View.GONE);
        }
        for(int i=0; i<list_pic.size(); i++){
            if(i==0){
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);
                Picasso.with(this).load(new File(list_pic.get(i))).resize(400, 400).centerCrop().into(image1);
            }
            if(i==1){
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.GONE);
                Picasso.with(this).load(new File(list_pic.get(i))).resize(400, 400).centerCrop().into(image2);
            }
            if(i==2){
                image3.setVisibility(View.VISIBLE);
                Picasso.with(this).load(new File(list_pic.get(i))).resize(400, 400).centerCrop().into(image3);
            }
        }
        if(list_pic.size()==3){
            choose_pic.setVisibility(View.GONE);
        }
        else{
            choose_pic.setVisibility(View.VISIBLE);
        }
    }

    private void pickPicFromCam(){
        File file = new File(mFilePath);
        if(!file.exists()){
            file.mkdirs();
        }
        mFile = mFilePath + "/temp"+i+".png";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        Uri photoUri = Uri.fromFile(new File(mFile)); // 传递路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
        startActivityForResult(intent, REQUEST_CAMERA);
        i++;
    }
    private void pickPicFromPic(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, FROM_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA) {
                File file = new File(mFile);
//                try{
//                    if(getFileSize(file)>10){
//                        Common.showToastLong("图片大小超过10M");
//                        return;
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

                if(file.exists()){
                    if(list_pic.size()<3){
                        list_pic.add(0, mFile);
                    }
                    else{
                        list_pic.add(0, mFile);
                        list_pic.remove(list_pic.size()-1);
                    }
                }
            }
            else if(requestCode==FROM_PIC && data!=null){
                Uri uri=data.getData();
                //要返回的列名
                Cursor cursor=getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if(cursor.moveToFirst()){
                    String ImgPath=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    if(list_pic.size()<3){
                        list_pic.add(0, ImgPath);
                    }
                    else{
                        list_pic.add(0, ImgPath);
                        list_pic.remove(list_pic.size()-1);
                    }
                }
            }
        }
    }

    public double getFileSize(File f) throws Exception{

        long l=0;

        if ( f.exists() ){

            FileInputStream mFIS = new FileInputStream(f);

            l= mFIS.available();

        }

        return (double) l/104875 ;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.advice_tv:
                advice_tv.setTextColor(getResources().getColor(R.color.colorApp));
                advice_line.setBackgroundColor(getResources().getColor(R.color.colorApp));
                complain_tv.setTextColor(getResources().getColor(R.color.colorTextBlack));
                complain_line.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                advice_et.setVisibility(View.VISIBLE);
                complain_et.setVisibility(View.INVISIBLE);
                type = 0;
                break;

            case R.id.complain_tv:
                advice_tv.setTextColor(getResources().getColor(R.color.colorTextBlack));
                advice_line.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                complain_tv.setTextColor(getResources().getColor(R.color.colorApp));
                complain_line.setBackgroundColor(getResources().getColor(R.color.colorApp));
                advice_et.setVisibility(View.INVISIBLE);
                complain_et.setVisibility(View.VISIBLE);
                type = 1;
                break;

            case R.id.submit:
                if(type==0){//建议
                    if(TextUtils.isEmpty(advice_et.getText().toString())){
                        Common.showToastShort("请输入建议内容");
                        return;
                    }
                }
                else{
                    if(TextUtils.isEmpty(complain_et.getText().toString())){
                        Common.showToastShort("请输入投诉内容");
                        return;
                    }
                }
                dialogUtils.showWaitDialog(this, "正在提交...");
                Map<String, String> map = new HashMap<>();
                map.put("UserID", Common.getUserID());
                map.put("Contact", email.getText().toString());

                Map<String, String> map_file = new HashMap<>();
                for(int i=0; i<list_pic.size(); i++){
                    map_file.put("Imag"+i, list_pic.get(i));
                }
                if(type==0){//建议
                    map.put("Content", advice_et.getText().toString());
                    present.Request("api/Suggestion/AddSuggestion", map, map_file, getClass().getSimpleName());
                }
                else{//投诉
                    map.put("Content", complain_et.getText().toString());
                    present.Request("api/Complaint/AddComplaint", map, map_file, getClass().getSimpleName());
                }
                break;

            case R.id.choose_pic:
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(SuggestActivity.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("选择图片：")
                        //设置两个item
                        .setItems(new String[]{"相机","图库"}, new android.content.DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        pickPicFromCam();
                                        break;
                                    case 1:
                                        pickPicFromPic();
                                        break;
                                    default:
                                        break;
                                }

                            }});
                builder.create().show();
            }
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.image1:
                list_pic.remove(0);
                break;

            case R.id.image2:
                list_pic.remove(1);
                break;

            case R.id.image3:
                list_pic.remove(2);
                break;
        }
        onResume();
        return false;
    }

    @Override
    public void updateSuccess(boolean success) {
        dialogUtils.disMiss();
        if(success){
            Common.showToastShort("提交成功");
            finish();
        }
        else{
            Common.showToastShort("提交失败");
        }
    }
}
