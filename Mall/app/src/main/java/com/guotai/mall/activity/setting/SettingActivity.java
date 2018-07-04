package com.guotai.mall.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.SettingAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.about.AboutActivity;
import com.guotai.mall.activity.accountSafe.AccountSafeActivity;
import com.guotai.mall.activity.changePw.ChangePwActivity;
import com.guotai.mall.activity.personInfo.PersonInfoActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;
import com.guotai.mall.uitl.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by zhangpan on 17/6/28.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    ListView setting_lv;
    SettingAdapter settingAdapter;
    List<String> list;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initTitle();

        setting_lv = (ListView) findViewById(R.id.setting_lv);
        list = new ArrayList<String>();
        list.add("个人资料");
        list.add("修改密码");
        list.add("关于");

        settingAdapter = new SettingAdapter(this, list);
        setting_lv.setAdapter(settingAdapter);
        setting_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(SettingActivity.this, PersonInfoActivity.class));
                        break;

                    case 1:
                        startActivity(new Intent(SettingActivity.this, ChangePwActivity.class));
                        break;

                    case 2:
                        startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                        break;

                    case 3:

                        break;

                    case 4:

                        break;
                }
            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
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
        title.setText(R.string.str_setting);
    }

    @Override
    public void onClick(View view) {
        Common.saveToken("");
        Common.saveUser("");
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserID", Common.getUserID());
        HttpFactory.getInstance().AsyncPost("api/Users/LogOut", map, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {

            }

            @Override
            public void onResponse(Call call, String response) {

            }
        }, getClass().getSimpleName());
        finish();
    }
}
