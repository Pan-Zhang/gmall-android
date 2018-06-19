package com.guotai.mall.activity.login;

import android.content.Intent;
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
import com.guotai.mall.activity.regist.RegistActivity;
import com.guotai.mall.activity.resetPass.ResetPasswordActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.SegmentLayout;

/**
 * Created by zhangpan on 17/6/28.
 */

public class LoginActivity extends BaseActivity<LoginPresent> implements ILoginactivity, View.OnClickListener {

    Button submit, get_sms;
    TextView regist, forget;
    EditText telephone, password;
    SegmentLayout segmentLayout;
    ImageView isHidepwd;
    int position;
    int seconds;
    boolean isHidePwd=true;
    private static final int GET_CODE = 0;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case GET_CODE:
                    if(seconds>0){
                        seconds--;
                        get_sms.setText("重新获取(" + seconds + "秒)");
                        get_sms.setClickable(false);
                        handler.sendEmptyMessageDelayed(GET_CODE, 1000);
                    }
                    else{
                        get_sms.setText("获取验证码");
                        get_sms.setClickable(true);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        present = new LoginPresent(this);

        initTitle();
        init();

    }

    private void init() {

        segmentLayout = (SegmentLayout) findViewById(R.id.segment);
        String[] strs = new String[]{"密码登录", "验证码登录"};
        segmentLayout.init(strs);
        segmentLayout.setSegmentClickListener(new SegmentLayout.SegmentClickListener() {
            @Override
            public void Click(int index) {
                position = index;
                switch (index){
                    case 0:
                        password.setHint("请输入密码");
                        get_sms.setVisibility(View.GONE);
                        if(isHidePwd){
                            password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码不可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                        }
                        else{
                            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        }

                        isHidepwd.setVisibility(View.VISIBLE);
                        break;

                    case 1:
                        password.setHint("请输入验证码");
                        password.setInputType(InputType.TYPE_CLASS_NUMBER);
                        get_sms.setVisibility(View.VISIBLE);
                        isHidepwd.setVisibility(View.GONE);
                        break;
                }
            }
        });

        submit = (Button) findViewById(R.id.submit);
        get_sms = (Button) findViewById(R.id.get_sms);
        get_sms.setVisibility(View.GONE);
        get_sms.setOnClickListener(this);

        isHidepwd = (ImageView) findViewById(R.id.isHidepwd);
        isHidepwd.setOnClickListener(this);

        regist = (TextView) findViewById(R.id.regist);
        forget = (TextView) findViewById(R.id.forget);

        telephone = (EditText) findViewById(R.id.telephone);
        password = (EditText) findViewById(R.id.password);
        submit.setOnClickListener(this);

        regist.setOnClickListener(this);
        forget.setOnClickListener(this);
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
        title.setText(R.string.str_login);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.submit:
                if(!Common.isMobile(telephone.getText().toString())){
                    Common.showToastShort("输入手机号格式有误");
                    return;
                }
                if(position==0){
                    if(TextUtils.isEmpty(password.getText().toString())){
                        Common.showToastLong(R.string.str_input_password);
                    }
                    else{
                        dialogUtils.showWaitDialog(LoginActivity.this, getResources().getString(R.string.str_logining));
                        present.Login(LoginActivity.this.getClass().getSimpleName(), telephone.getText().toString(), password.getText().toString(), 0);
                    }
                }
                else{
                    if(TextUtils.isEmpty(password.getText().toString())){
                        Common.showToastLong("请输入验证码");
                    }
                    else{
                        dialogUtils.showWaitDialog(LoginActivity.this, getResources().getString(R.string.str_logining));
                        present.Login(LoginActivity.this.getClass().getSimpleName(), telephone.getText().toString(), password.getText().toString(), 1);
                    }
                }
                break;

            case R.id.regist:
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                break;

            case R.id.get_sms:
                if(!Common.isMobile(telephone.getText().toString())){
                    Common.showToastShort("输入手机号格式有误");
                    return;
                }
                if(TextUtils.isEmpty(telephone.getText().toString())){
                    Common.showToastShort("请输入电话号码");
                }else {
                    seconds = 59;
                    handler.sendEmptyMessage(GET_CODE);
                    present.getCode(telephone.getText().toString(), LoginActivity.this.getClass().getSimpleName());
                }
                break;

            case R.id.forget:
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;

            case R.id.isHidepwd:
                if(isHidePwd){
                    isHidepwd.setBackgroundResource(R.mipmap.seepwd);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isHidePwd = false;
                }
                else{
                    isHidepwd.setBackgroundResource(R.mipmap.hidepwd);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    isHidePwd = true;
                }
                break;
        }
    }

    @Override
    public void LoginSucc(boolean success) {
        dialogUtils.disMiss();
        if(success){
            Common.showToastLong(R.string.str_login_success);
            finish();
        }
        else{
            Common.showToastLong(R.string.str_login_failed);
        }
    }

    @Override
    public void GetSms(boolean success) {
        if(success){
            Common.showToastShort("短信发送成功，请注意接收");
        }
        else{
            seconds = 0;
            handler.sendEmptyMessage(GET_CODE);
        }
    }
}
