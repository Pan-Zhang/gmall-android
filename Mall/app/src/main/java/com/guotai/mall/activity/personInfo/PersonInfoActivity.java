package com.guotai.mall.activity.personInfo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.PersonInfoAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.orderDetail.OrderDetailActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.PersonInfo;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;
import com.guotai.mall.widget.BottomAnimDialog;
import com.guotai.mall.widget.DatePickerDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/8/3.
 */

public class PersonInfoActivity extends BaseActivity<PersonInfoPresent> implements IPersonInfoactivity {

    ListView person_lv;
    PersonInfoAdapter personInfoAdapter;
    List<PersonInfoAdapter.InfoItem> list;
    AlertDialog dialog;
    BottomAnimDialog bottomAnimDialog;
    TextView title;
    EditText content;
    Button submit, cancel, sex_btn;
    DatePickerDialog datePickerDialog;
    LinearLayout smscode_ll;
    EditText smscode_et;
    Button getCode;
    int timeLong = 59;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(timeLong>0){
                getCode.setText("重新获取("+timeLong+"秒)");
                timeLong--;
                handler.sendEmptyMessageDelayed(0, 1000);
            }
            else{
                timeLong = 59;
                getCode.setText("获取验证码");
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        present = new PersonInfoPresent(this);

        initTitle();

        list = new ArrayList<>();
        PersonInfoAdapter.InfoItem infoItem = new PersonInfoAdapter.InfoItem();
        infoItem.title = "修改用户名称";
        infoItem.content = Common.getUser();
        list.add(infoItem);

        infoItem = new PersonInfoAdapter.InfoItem();
        infoItem.title = "手机号改绑";
        infoItem.content = Common.getMobile();
        list.add(infoItem);

        infoItem = new PersonInfoAdapter.InfoItem();
        infoItem.title = "性别";
        infoItem.content = Common.getGender();
        list.add(infoItem);

        infoItem = new PersonInfoAdapter.InfoItem();
        infoItem.title = "出生日期";
        infoItem.content = Common.getBirthday();
        list.add(infoItem);

        person_lv = (ListView) findViewById(R.id.person_lv);
        personInfoAdapter = new PersonInfoAdapter(this, list);
        person_lv.setAdapter(personInfoAdapter);
        person_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(dialog==null){
                    dialog = new AlertDialog.Builder(PersonInfoActivity.this).create();//创建一个AlertDialog对象
                    View _view = getLayoutInflater().inflate(R.layout.layout_person_dialog, null);//自定义布局
                    dialog.setView(_view, 0, 0, 0, 0);//把自定义的布局设置到dialog中，注意，布局设置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
                    dialog.show();//一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
                    int width = getWindowManager().getDefaultDisplay().getWidth();//得到当前显示设备的宽度，单位是像素
                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();//得到这个dialog界面的参数对象
                    params.width = width-(width/6);//设置dialog的界面宽度
                    params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;//设置dialog高度为包裹内容
                    params.gravity = Gravity.CENTER;//设置dialog的重心
                    //dialog.getWindow().setLayout(width-(width/6),  LayoutParams.WRAP_CONTENT);//用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
                    dialog.getWindow().setAttributes(params);//最后把这个参数对象设置进去，即与dialog绑定
                    title = (TextView) dialog.findViewById(R.id.title);
                    content = (EditText) dialog.findViewById(R.id.content);
                    sex_btn = (Button) dialog.findViewById(R.id.sex_btn);
                    sex_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag = Integer.parseInt(v.getTag().toString());
                            if(tag==0){
                                chooseSex();
                            }
                            else{
                                chooseDate();
                            }
                        }
                    });
                    smscode_ll = (LinearLayout) dialog.findViewById(R.id.smscode_ll);

                    smscode_et = (EditText) dialog.findViewById(R.id.smscode_et);

                    getCode = (Button) dialog.findViewById(R.id.getCode);
                    getCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!Common.isMobile(content.getText().toString())){
                                Common.showToastLong("请输入正确的手机号码");
                                return;
                            }
                            getCode.setClickable(false);
                            handler.sendEmptyMessage(0);
                            present.getCode("api/Smsverify/SendVerifyCodeByMobile?Mobile="+content.getText().toString(), PersonInfoActivity.this.getClass().getSimpleName());
                        }
                    });

                    submit = (Button) dialog.findViewById(R.id.submit);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag = Integer.parseInt(v.getTag().toString());
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("UserID", Common.getUserID());
                            switch (tag){
                                case 0:
                                    map.put("UserName", content.getText().toString());
                                    break;

                                case 1:
                                    map.put("Mobile", content.getText().toString());
                                    map.put("SMSCode", smscode_et.getText().toString());
                                    break;

                                case 2:
                                    map.put("Gender", sex_btn.getText().toString());
                                    break;

                                case 3:
                                    map.put("Birthday", sex_btn.getText().toString());
                                    break;
                            }
                            present.editInfo("api/Users/Edit", map, tag, PersonInfoActivity.this.getClass().getSimpleName());
//                            if(tag!=1){
//                                dialog.dismiss();
//                            }
                        }
                    });
                    cancel = (Button) dialog.findViewById(R.id.cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
                dialog.show();
                content.setText("");
                smscode_et.setText("");
                switch (position){
                    case 0:
                        sex_btn.setVisibility(View.GONE);
                        content.setVisibility(View.VISIBLE);
                        smscode_ll.setVisibility(View.GONE);
                        title.setText(R.string.str_change_pw);
                        content.setHint(R.string.str_new_username);
                        content.setInputType(InputType.TYPE_CLASS_TEXT);
                        submit.setTag(0);
                        break;

                    case 1:
                        sex_btn.setVisibility(View.GONE);
                        content.setVisibility(View.VISIBLE);
                        smscode_ll.setVisibility(View.VISIBLE);
                        title.setText(R.string.str_exchange_mobile);
                        content.setHint(R.string.str_new_mobile);
                        content.setInputType(InputType.TYPE_CLASS_NUMBER);
                        submit.setTag(1);
                        break;

                    case 2:
                        sex_btn.setVisibility(View.VISIBLE);
                        sex_btn.setTag(0);
                        content.setVisibility(View.GONE);
                        smscode_ll.setVisibility(View.GONE);
                        title.setText(R.string.str_gender);
                        content.setHint(R.string.str_input_gender);
                        sex_btn.setText(Common.getGender());
                        submit.setTag(2);
                        break;

                    case 3:
                        sex_btn.setVisibility(View.VISIBLE);
                        sex_btn.setTag(1);
                        content.setVisibility(View.GONE);
                        smscode_ll.setVisibility(View.GONE);
                        title.setText(R.string.str_birthday);
                        content.setHint(R.string.str_input_birthday);
                        String bir = Common.getBirthday();
                        sex_btn.setText(Common.getBirthday());
                        submit.setTag(3);
                        break;
                }
            }
        });

        updatePersonInfo();
    }

    private void updatePersonInfo(){
        if(!TextUtils.isEmpty(Common.getUserID())){
            HttpFactory.getInstance().AsyncGet("api/Users/GetUserInfoByID?UserID="+Common.getUserID(), new ResultBack() {
                @Override
                public void onFailure(Call call, String e) {

                }

                @Override
                public void onResponse(Call call, String response) {
                    PersonInfo personInfo = Common.parseJsonWithGson(response, PersonInfo.class);
                    Common.saveUser(personInfo.UserName==null?"":personInfo.UserName);
                    list.get(0).content = personInfo.UserName==null?"":personInfo.UserName;
                    Common.saveMobile(personInfo.Mobile==null?"":personInfo.Mobile);
                    list.get(1).content = personInfo.Mobile==null?"":personInfo.Mobile;
                    Common.saveGender(personInfo.Gender==null?"":personInfo.Gender);
                    list.get(2).content = personInfo.Gender==null?"":personInfo.Gender;
                    personInfo.Birthday = personInfo.Birthday.substring(0, personInfo.Birthday.indexOf(" "));
                    Common.saveBirthday(personInfo.Birthday==null?"":personInfo.Birthday);
                    list.get(3).content = personInfo.Birthday==null?"":personInfo.Birthday;
                }
            }, getClass().getSimpleName());
        }
    }

    private void chooseSex(){
        bottomAnimDialog = new BottomAnimDialog(PersonInfoActivity.this, "男", "女", "取消");
        bottomAnimDialog.setClickListener(new BottomAnimDialog.BottonAnimDialogListener() {

            @Override
            public void onItem1Listener() {
                sex_btn.setText("男");
                bottomAnimDialog.dismiss();
            }

            @Override
            public void onItem2Listener() {
                sex_btn.setText("女");
                bottomAnimDialog.dismiss();
            }

            @Override
            public void onItem3Listener() {
                bottomAnimDialog.dismiss();
            }
        });
        bottomAnimDialog.show();
    }

    private void chooseDate(){
        if(datePickerDialog==null){
            datePickerDialog = new DatePickerDialog(PersonInfoActivity.this, sex_btn.getText().toString());
            datePickerDialog.setClickListener(new DatePickerDialog.DatePickerDialogListener() {
                @Override
                public void ensure(String date) {
                    sex_btn.setText(date);
                    datePickerDialog.dismiss();
                }
            });
        }
        datePickerDialog.show();
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
        title.setText(R.string.str_person_info);
    }

    @Override
    public void refreshSuccess(Boolean success, int i, String mess) {
        if(success){
            Common.showToastShort(R.string.str_upload_success);
            dialog.dismiss();
            switch (i) {
                case 0:
                    Common.saveUser(content.getText().toString());
                    list.get(i).content = Common.getUser();
                    break;

                case 1:
                    Common.saveMobile(content.getText().toString());
                    list.get(i).content = Common.getMobile();
                    break;

                case 2:
                    Common.saveGender(sex_btn.getText().toString());
                    list.get(i).content = Common.getGender();
                    break;

                case 3:
                    Common.saveBirthday(sex_btn.getText().toString());
                    list.get(i).content = Common.getBirthday();
                    break;
            }
            personInfoAdapter.update(list);
        }
        else{
            Common.showToastShort(mess);
        }
    }

    @Override
    public void sendCode(boolean success, String mess) {
        Common.showToastShort(mess);
        getCode.setClickable(true);
        if(success){

        }
        else{
            timeLong = 0;
            getCode.setText("获取验证码");
        }
    }
}
