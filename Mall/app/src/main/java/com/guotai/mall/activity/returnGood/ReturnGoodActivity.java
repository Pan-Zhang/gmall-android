package com.guotai.mall.activity.returnGood;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.activity.returngoodres.ReturnGoodResActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.model.ReturnReason;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;
import com.guotai.mall.widget.BackMoneyReasonDialog;
import com.guotai.mall.widget.MultyPicView;
import com.guotai.mall.widget.ProductView2;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/11/2.
 */


public class ReturnGoodActivity extends BaseActivity<ReturnGoodPresent> implements View.OnClickListener, IReturnGoodActivity {

    public static OrderEx orderEx;
    List<String> list;
    String mFilePath, mFile;
    int i=0;
    MultyPicView multyPicView;
    ProductView2 products;
    TextView amount, del, add, return_tv, barter_tv, return_line, barter_line, returnOrBarter, reason_text;
    TextView reason_tv;
    EditText reason;
    boolean type = false;
    private static final int REQUEST_CAMERA = 0;
    private static final int FROM_PIC = 1;
    String ReturnReasonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        present = new ReturnGoodPresent(this);

        initTitle();

        TextView orderId = (TextView) findViewById(R.id.orderId);
        orderId.setText(orderEx.OrderSN);

        mFilePath = Environment.getExternalStorageDirectory().getPath()+ "/test";// 获取SD卡路径

        products = (ProductView2) findViewById(R.id.products);
        products.setData(orderEx);

        reason_tv = (TextView) findViewById(R.id.reason_tv);
        reason_tv.setOnClickListener(this);

        reason = (EditText) findViewById(R.id.reason);

        list = new ArrayList<>();
        list.add("default");
        multyPicView = (MultyPicView) findViewById(R.id.multyPicView);
        multyPicView.setImagesData(list, new MultyPicView.OnClick() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                if(list.get(position).equals("default")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(ReturnGoodActivity.this)
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
            }

            @Override
            public void onLongClick(View v) {
                final int position = (int)v.getTag();
                if(!list.get(position).equals("default")){
                    LinearLayout view = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_prompt, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReturnGoodActivity.this)
                            .setView(view);
                    final AlertDialog dialog = builder.create();
                    TextView title_prompt = (TextView) view.findViewById(R.id.title_prompt);
                    title_prompt.setText(R.string.str_sure_delete_pic);
                    Button submit_btn = (Button) view.findViewById(R.id.submit_btn);
                    submit_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            list.remove(position);
                            if(!list.get(list.size()-1).equals("default") && list.size()<6){
                                list.add("default");
                            }
                            multyPicView.setImagesData(list, null);
                            dialog.dismiss();
                        }
                    });
                    Button cancel_btn = (Button) view.findViewById(R.id.cancel_btn);
                    cancel_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            }
        });

        amount = (TextView) findViewById(R.id.amount);
        del = (TextView) findViewById(R.id.del);
        add = (TextView) findViewById(R.id.add);
        del.setOnClickListener(this);
        add.setOnClickListener(this);

        return_tv = (TextView) findViewById(R.id.return_tv);
        return_tv.setOnClickListener(this);
        barter_tv = (TextView) findViewById(R.id.barter_tv);
        barter_tv.setOnClickListener(this);
        return_line = (TextView) findViewById(R.id.return_line);
        barter_line = (TextView) findViewById(R.id.barter_line);
        returnOrBarter = (TextView) findViewById(R.id.returnOrBarter);
        reason_text = (TextView) findViewById(R.id.reason_text);

        if(orderEx.IsReturnExpired==0 && orderEx.IsExchangeExpired==1){
            return_tv.setTextColor(getResources().getColor(R.color.colorTextBlack));
            return_line.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            barter_tv.setTextColor(getResources().getColor(R.color.colorApp));
            barter_line.setBackgroundColor(getResources().getColor(R.color.colorApp));
            returnOrBarter.setText(R.string.str_barter_amount);
            reason_tv.setText(R.string.str_barter_reason);
            type = true;
        }
        else if(orderEx.IsReturnExpired==1 && orderEx.IsExchangeExpired==0){
            return_tv.setTextColor(getResources().getColor(R.color.colorApp));
            return_line.setBackgroundColor(getResources().getColor(R.color.colorApp));
            barter_tv.setTextColor(getResources().getColor(R.color.colorTextBlack));
            barter_line.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            returnOrBarter.setText(R.string.str_return_amount);
            reason_tv.setText(R.string.str_return_reason);
            type = false;
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
        title.setText(R.string.str_after_sale);
        TextView right_text = (TextView) findViewById(R.id.right_text);
        right_text.setText(R.string.str_submit);
        right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()==1){
                    Common.showToastShort("请选择上传图片");
                    return;
                }
                dialogUtils.showWaitDialog(ReturnGoodActivity.this);
                Map<String, String> map = new HashMap<>();
                map.put("OrderID", orderEx.OrderID);
                map.put("UserID", Common.getUserID());
                map.put("ReturnReasonID", ReturnReasonID);
                map.put("AfterSaleRemark", reason.getText().toString());
                map.put("OrderSubID", orderEx.OrderDetailList.get(0).OrderSubID);
                if(type){
                    map.put("AfterSaleType", "1");
                }
                else{
                    map.put("AfterSaleType", "2");
                }
                map.put("AfterSaleQty", orderEx.TotalQty);

                Map<String, String> map_file = new HashMap<>();
                for(int i=0; i<list.size(); i++){
                    if(!list.get(i).equals("default")){
                        map_file.put("Imag"+i, list.get(i));
                    }
                }
                HttpFactory.getInstance().UploadImage("api/Order/AftersaleOrderFormData", map, map_file, new ResultBack() {
                    @Override
                    public void onFailure(Call call, String e) {
                        Common.showToastShort(e);
                        dialogUtils.disMiss();
                    }

                    @Override
                    public void onResponse(Call call, String response) {
                        Common.showToastShort(R.string.str_upload_success);
                        dialogUtils.disMiss();
                        finish();
                    }
                }, ReturnGoodActivity.this.getClass().getSimpleName());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA) {
                File file = new File(mFile);
                try{
                    if(getFileSize(file)>10){
                        Common.showToastLong("图片大小超过10M");
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(file.exists()){
                    if(list.size()<6){
                        list.add(0, mFile);
                    }
                    else{
                        list.add(0, mFile);
                        list.remove(list.size()-1);
                    }
                    multyPicView.setImagesData(list, null);
                }
            }
            else if(requestCode==FROM_PIC && data!=null){
                Uri uri=data.getData();
                //要返回的列名
                Cursor cursor=getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if(cursor.moveToFirst()){
                    String ImgPath=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    if(list.size()<6){
                        list.add(0, ImgPath);
                    }
                    else{
                        list.add(0, ImgPath);
                        list.remove(list.size()-1);
                    }
                    multyPicView.setImagesData(list, null);
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
    protected void onDestroy() {
        super.onDestroy();
        orderEx = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.reason_tv:
                present.getReason("api/OrderExtra/GetReturnReasonList?AfterSaleType="+(type?"2":"1"), ReturnGoodActivity.this.getClass().getSimpleName());
                break;

            case R.id.add:
                int value = Integer.parseInt(amount.getText().toString());
                if(value < Integer.parseInt(orderEx.TotalQty)){
                    amount.setText(String.valueOf(++value));
                }
                break;

            case R.id.del:
                int value1 = Integer.parseInt(amount.getText().toString());
                if(value1 > 0){
                    amount.setText(String.valueOf(--value1));
                }
                break;

            case R.id.barter_tv:
                if(orderEx.IsExchangeExpired==0){
                    return_tv.setTextColor(getResources().getColor(R.color.colorTextBlack));
                    return_line.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    barter_tv.setTextColor(getResources().getColor(R.color.colorApp));
                    barter_line.setBackgroundColor(getResources().getColor(R.color.colorApp));
                    returnOrBarter.setText(R.string.str_barter_amount);
                    reason_tv.setText(R.string.str_barter_reason);
                    type = true;
                }
                else{
                    Common.showToastShort("已超出换货期限");
                }
                break;

            case R.id.return_tv:
                if(orderEx.IsReturnExpired==0){
                    return_tv.setTextColor(getResources().getColor(R.color.colorApp));
                    return_line.setBackgroundColor(getResources().getColor(R.color.colorApp));
                    barter_tv.setTextColor(getResources().getColor(R.color.colorTextBlack));
                    barter_line.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    returnOrBarter.setText(R.string.str_return_amount);
                    reason_tv.setText(R.string.str_return_reason);
                    type = false;
                }
                else{
                    Common.showToastShort("已超出退货期限");
                }
                break;
        }
    }

    @Override
    public void updateReason(final List<ReturnReason> list) {
        if(list==null){
            Common.showToastLong("获取数据失败");
        }
        else {
            list.get(0).choosed = true;
            BackMoneyReasonDialog dialog = new BackMoneyReasonDialog(this, list);
            dialog.setEnsureClickListener(new BackMoneyReasonDialog.EnsureClickListener() {
                @Override
                public void ensure(int position) {
                    Map<String, String> map = new HashMap<>();
                    ReturnReasonID = list.get(position).ReturnReasonID;
                    reason_tv.setText(list.get(position).ReturnReasonName);
                }
            });
            dialog.show();
        }
    }
}
