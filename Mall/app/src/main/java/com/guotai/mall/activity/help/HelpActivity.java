package com.guotai.mall.activity.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.activity.WebView.WebViewActivity;
import com.guotai.mall.base.BaseActivity;

/**
 * Created by zhangpan on 17/6/27.
 */

public class HelpActivity extends BaseActivity<HelpPresent> implements IHelpactivity, View.OnClickListener {

    LinearLayout transfee, transtype, paytype;
    TextView weixin_tv, email_tv, phone_tv;
    String transFee, transType, payType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        present = new HelpPresent(this);

        initTitle();

        init();
        present.getData("api/Store/GetStoreHelpInfo", this.getClass().getSimpleName());
    }

    private void init() {
        weixin_tv = (TextView) findViewById(R.id.weixin_tv);
        email_tv = (TextView) findViewById(R.id.email_tv);
        phone_tv = (TextView) findViewById(R.id.phone_tv);
        phone_tv.setOnClickListener(this);
        transfee = (LinearLayout)findViewById(R.id.transfee);
        transfee.setOnClickListener(this);
        transtype = (LinearLayout)findViewById(R.id.transtype);
        transtype.setOnClickListener(this);
        paytype = (LinearLayout)findViewById(R.id.paytype);
        paytype.setOnClickListener(this);
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
        title.setText(R.string.str_helpcenter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.transfee:
                openWebView(transFee);
                break;

            case R.id.transtype:
                openWebView(transType);
                break;

            case R.id.paytype:
                openWebView(payType);
                break;

            case R.id.phone_tv:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone_tv.getText().toString()));
                startActivity(intent);
                break;
        }
    }

    public void openWebView(String url){
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void update(Boolean success, HelpPresent.HelpData mess) {
        weixin_tv.setText(mess.WXNumber);
        email_tv.setText(mess.KfEmai);
        phone_tv.setText(mess.KfTelephone);
        transFee = mess.TransferDesc;
        transType = mess.LogisticsDesc;
        payType = mess.PaymentDesc;
    }
}
