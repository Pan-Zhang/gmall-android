package com.guotai.mall.activity.accountSafe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.AccountAdapter;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpan on 17/8/3.
 */

public class AccountSafeActivity extends BaseActivity<AccountSafePresent> implements IAccountSafeactivity {

    ListView account_lv;
    AccountAdapter accountAdapter;
    List<AccountAdapter.AccountSafe> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);

        present = new AccountSafePresent(this);

        initTitle();

        list = new ArrayList<>();
        AccountAdapter.AccountSafe accountSafe = new AccountAdapter.AccountSafe();
        accountSafe.img = R.mipmap.me_setting_safe_phone;
        accountSafe.title = "手机号";
        accountSafe.content = "13988785443";
        list.add(accountSafe);

        accountSafe = new AccountAdapter.AccountSafe();
        accountSafe.img = R.mipmap.me_setting_safe_qq;
        accountSafe.title = "QQ账号";
        accountSafe.content = "马上绑定";
        list.add(accountSafe);

        accountSafe = new AccountAdapter.AccountSafe();
        accountSafe.img = R.mipmap.me_setting_safe_weixin;
        accountSafe.title = "微信账号";
        accountSafe.content = "已绑定";
        list.add(accountSafe);

        accountSafe = new AccountAdapter.AccountSafe();
        accountSafe.img = R.mipmap.me_setting_safe_weibo;
        accountSafe.title = "微博账号";
        accountSafe.content = "马上绑定";
        list.add(accountSafe);

        account_lv = (ListView) findViewById(R.id.account_lv);
        accountAdapter = new AccountAdapter(this, list);
        account_lv.setAdapter(accountAdapter);

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
        title.setText(R.string.str_account_safe);
    }
}
