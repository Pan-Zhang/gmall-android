package com.guotai.mall.activity.logisticsDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.LogisticsAdapter;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Logistics;

/**
 * Created by zhangpan on 17/11/17.
 */

public class LogisticsDetailActivity extends BaseActivity {

    ListView listview;
    TextView number, company, status, title;
    LogisticsAdapter logisticsAdapter;
    public static Logistics logistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_detail);

        initTitle();

        number = (TextView) findViewById(R.id.number);
        company = (TextView) findViewById(R.id.company);
        status = (TextView) findViewById(R.id.status);

        listview = (ListView) findViewById(R.id.listview);
        logisticsAdapter = new LogisticsAdapter(this, logistics.Traces);
        listview.setAdapter(logisticsAdapter);

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
        title = (TextView) findViewById(R.id.title);
        title.setText(R.string.str_logistics_detail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logistics = null;
    }
}
