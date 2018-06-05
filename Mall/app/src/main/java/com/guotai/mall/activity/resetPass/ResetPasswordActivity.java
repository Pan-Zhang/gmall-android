package com.guotai.mall.activity.resetPass;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.uitl.Common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangpan on 17/10/26.
 */

public class ResetPasswordActivity extends BaseActivity<ResetPassPresent> implements IResetPasswordActivity, View.OnClickListener {

    Button submit, getCode;
    EditText phonNum, newPw, verify_code;
    int seconds;
    private static final int GET_CODE = 0;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case GET_CODE:
                    if(seconds>0){
                        seconds--;
                        getCode.setText("重新获取" + seconds);
                        getCode.setClickable(false);
                        handler.sendEmptyMessageDelayed(GET_CODE, 1000);
                    }
                    else{
                        getCode.setText("获取验证码");
                        getCode.setClickable(true);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = new ResetPassPresent(this);

        setContentView(R.layout.activity_reset_password);
        initTitle();
        init();
    }

    private void init() {
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        getCode = (Button) findViewById(R.id.getCode);
        getCode.setOnClickListener(this);
        phonNum = (EditText) findViewById(R.id.phoneNum);
        newPw = (EditText) findViewById(R.id.newPw);
        verify_code = (EditText) findViewById(R.id.verify_code);
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
        title.setText(R.string.str_reset_pass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                if(TextUtils.isEmpty(phonNum.getText().toString())){
                    Common.showToastShort("电话号码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(newPw.getText().toString())){
                    Common.showToastShort("请填写新密码");
                    return;
                }
                if(TextUtils.isEmpty(verify_code.getText().toString())){
                    Common.showToastShort("请输入验证码");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("Mobile", phonNum.getText().toString());
                map.put("Password", Common.md5(newPw.getText().toString()));
                map.put("SMSCode", verify_code.getText().toString());
                present.reset("api/Users/ForgetPassword", map, ResetPasswordActivity.this.getClass().getSimpleName());
                break;

            case R.id.getCode:
                if(TextUtils.isEmpty(phonNum.getText().toString())){
                    Common.showToastShort("请输入电话号码");
                }else {
                    seconds = 59;
                    handler.sendEmptyMessage(GET_CODE);
                    present.getCode(phonNum.getText().toString(), ResetPasswordActivity.this.getClass().getSimpleName());
                }
                break;
        }
    }

    @Override
    public void reset(Boolean success) {
        if(success){
            Common.showToastShort("重置密码成功");
            finish();
        }
        else{
            Common.showToastShort("重置密码失败");
        }
    }

    @Override
    public void GetSms(Boolean success) {
        if(success){
            Common.showToastShort("短信发送成功，请注意接收");
        }
        else{
            seconds = 0;
            handler.sendEmptyMessage(GET_CODE);
        }
    }
}
