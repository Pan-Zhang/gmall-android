package com.guotai.mall.activity.regist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.uitl.Common;

/**
 * Created by zhangpan on 17/6/28.
 */

public class RegistActivity extends BaseActivity<RegistPresent> implements IRegistactivity, View.OnClickListener {

    private static final int GET_CODE = 0;
    EditText telephone, username, password, confirm_pass, verify_code;
    Button getCode, submit;
    ImageView isHidepwd, isHidepwd1;
    Boolean isHide=true, isHide1=true;
    int seconds;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case GET_CODE:
                    if(seconds>0){
                        handler.sendEmptyMessageDelayed(GET_CODE, 1000);
                        getCode.setText("重新获取（"+seconds+"秒）");
                        seconds--;
                    }
                    else{
                        getCode.setClickable(true);
                        getCode.setText("获取验证码");
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        present = new RegistPresent(this);

        initTitle();
        initView();
    }

    private void initView() {
        telephone = (EditText) findViewById(R.id.telephone);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirm_pass = (EditText) findViewById(R.id.confirm_pass);
        verify_code = (EditText) findViewById(R.id.verify_code);

        isHidepwd = (ImageView) findViewById(R.id.isHidepwd);
        isHidepwd.setOnClickListener(this);
        isHidepwd1 = (ImageView) findViewById(R.id.isHidepwd1);
        isHidepwd1.setOnClickListener(this);

        getCode = (Button) findViewById(R.id.getCode);
        getCode.setOnClickListener(this);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
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
        title.setText(R.string.str_regist);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getCode:
                if(!Common.isMobile(telephone.getText().toString())){
                    Common.showToastShort("输入手机号格式有误");
                    return;
                }
                if(TextUtils.isEmpty(telephone.getText().toString())){
                    Common.showToastLong(R.string.str_input_telephone);
                }
                else {
                    seconds = 59;
                    handler.sendEmptyMessage(GET_CODE);
                    getCode.setClickable(false);
                    present.GetCode(RegistActivity.this.getClass().getSimpleName(), telephone.getText().toString());

                }
                break;

            case R.id.submit:
                if(!Common.isMobile(telephone.getText().toString())){
                    Common.showToastShort("请输入正确的手机号");
                }
                else if (TextUtils.isEmpty(username.getText().toString())){
                    Common.showToastShort("请输入用户名");
                }
                else if(!Common.ispsd(password.getText().toString())){
                    Common.showToastShort("密码必须为6～20位的字母和数字组合");
                }
                else if(TextUtils.isEmpty(confirm_pass.getText().toString())){
                    Common.showToastShort("请输入确认密码");
                }
                else if(!password.getText().toString().equals(confirm_pass.getText().toString())){
                    Common.showToastShort("两次密码不一致");
                }
                else if(TextUtils.isEmpty(verify_code.getText().toString())){
                    Common.showToastShort("请输入验证码");
                }
                else{
                    dialogUtils.showWaitDialog(RegistActivity.this, "正在注册");
                    present.Login(RegistActivity.this.getClass().getSimpleName(), username.getText().toString(), password.getText().toString(), telephone.getText().toString(), verify_code.getText().toString());
                }
                break;

            case R.id.isHidepwd:
                if(isHide){
                    isHidepwd.setBackgroundResource(R.mipmap.seepwd);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isHide = false;
                }
                else{
                    isHidepwd.setBackgroundResource(R.mipmap.hidepwd);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    isHide = true;
                }
                break;

            case R.id.isHidepwd1:
                if(isHide1){
                    isHidepwd1.setBackgroundResource(R.mipmap.seepwd);
                    confirm_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isHide1 = false;
                }
                else{
                    isHidepwd1.setBackgroundResource(R.mipmap.hidepwd);
                    confirm_pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    isHide1 = true;
                }
                break;
        }
    }

    @Override
    public void SendSuccess(Boolean succ) {
        if(succ){
            Common.showToastShort("验证码发送成功");
        }
        else{
            seconds = 0;
        }
    }

    @Override
    public void registSucc(Boolean succ, String mess) {
        dialogUtils.disMiss();
        if(succ){
            finish();
        }
        Common.showToastLong(mess);
    }
}
