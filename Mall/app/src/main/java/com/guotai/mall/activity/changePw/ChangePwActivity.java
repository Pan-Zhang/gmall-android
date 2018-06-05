package com.guotai.mall.activity.changePw;

import android.os.Bundle;
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
 * Created by zhangpan on 17/11/21.
 */

public class ChangePwActivity extends BaseActivity<ChangePwPresent> implements IChangePwActivity, View.OnClickListener {

    EditText old_pw, new_pw;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepw);

        present = new ChangePwPresent(this);
        initTitle();
        old_pw = (EditText) findViewById(R.id.old_pw);
        new_pw = (EditText) findViewById(R.id.new_pw);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(this);

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
        title.setText(R.string.str_change_pw);

    }

    @Override
    public void changePw(boolean success) {
        dialogUtils.disMiss();
        if(success){
            finish();
        }
        else{
            Common.showToastShort(R.string.str_change_fail);
        }
    }

    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(old_pw.getText().toString())){
            Common.showToastShort("请输入原始密码");
            return;
        }
        if(TextUtils.isEmpty(new_pw.getText().toString())){
            Common.showToastShort("请输入新密码");
            return;
        }
        dialogUtils.showWaitDialog(this);
        Map<String, String> map = new HashMap<>();
        map.put("UserID", Common.getUserID());
        map.put("oldPassword", Common.md5(old_pw.getText().toString()));
        map.put("newPassword", Common.md5(new_pw.getText().toString()));
        present.changePw("api/Users/ModifyPassword", map, getClass().getSimpleName());
    }
}
