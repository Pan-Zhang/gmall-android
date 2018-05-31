package com.guotai.mall.activity.logisticInput;

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
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.OrderDetailEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.MultyPicView;
import com.guotai.mall.widget.ProductView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.guotai.mall.activity.returngoodres.ReturnGoodResActivity.afterSale;

/**
 * Created by zhangpan on 2018/4/26.
 */

public class LogisticInputActivity extends BaseActivity<LogisticInputPresent> implements ILogisticInputActivity {

    LinearLayout logistic_company;
    EditText logistic_num, telephone;
    MultyPicView multyPicView;
    ProductView products;
    Button ensure;
    List<String> list;
    String mFilePath, mFile;
    int i=0;
    String LogisticsID;
    private static final int REQUEST_CAMERA = 0;
    private static final int FROM_PIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        present = new LogisticInputPresent(this);

        setContentView(R.layout.activity_logisticinput);

        initTitle();

        logistic_company = (LinearLayout) findViewById(R.id.logistic_company);
        logistic_num = (EditText) findViewById(R.id.logistic_num);
        telephone = (EditText) findViewById(R.id.telephone);

        mFilePath = Environment.getExternalStorageDirectory().getPath()+ "/test";// 获取SD卡路径
        multyPicView = (MultyPicView) findViewById(R.id.multyPicView);
        list = new ArrayList<>();
        list.add("default");
        multyPicView.setImagesData(list, new MultyPicView.OnClick() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                if(list.get(position).equals("default")){
                    AlertDialog.Builder builder=new AlertDialog.Builder(LogisticInputActivity.this)
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogisticInputActivity.this)
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

        products = (ProductView) findViewById(R.id.products);
        List<CarPro> list = new ArrayList<>();
        for(OrderDetailEx detail : afterSale.OrderDetailList){
            CarPro carPro = new CarPro();
            carPro.Price = detail.Price;
            carPro.FirstImage = detail.FirstImage;
            carPro.ProductName = detail.ProductName;
            carPro.ProductDescription = detail.ProductDescription;
            carPro.ProductPrice = detail.Price;
            carPro.Qty = detail.Qty;
            list.add(carPro);
        }
        products.setData(list);

        ensure = (Button) findViewById(R.id.ensure);
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("UserID", Common.getUserID());
                map.put("AfterOrderID", afterSale.AfterSaleOrderID);
                map.put("LogisticsID", LogisticsID);
                map.put("LogisticsOrderID", logistic_num.getText().toString());
                present.SubmitLogistic("api/Order/SetAfterOrderLogistics", map, LogisticInputActivity.this.getClass().getSimpleName());
            }
        });

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
        title.setText("填写物流信息");
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
}
