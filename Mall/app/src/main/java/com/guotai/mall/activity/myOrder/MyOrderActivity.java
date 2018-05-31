package com.guotai.mall.activity.myOrder;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.Adapter.MyOrderAdapter;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.fragment.myOrder.MyOrderPresent;
import com.guotai.mall.widget.MyIndicator;

/**
 * Created by zhangpan on 17/7/25.
 */

public class MyOrderActivity extends BaseActivity<MyOrderPresent> implements IMyOrderactivity {

    MyIndicator indicator;
    ViewPager viewPager;
    MyOrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        initTitle();
        int index = getIntent().getFlags();

        indicator = (MyIndicator) findViewById(R.id.indicator);
        String[] titles = new String[]{"待付款", "待发货", "待收货", "售后/退款", "已完成", "已取消"};
        indicator.setTitles(titles);

        viewPager = (ViewPager) findViewById(R.id.order_pager);

        mAdapter = new MyOrderAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        indicator.setViewPager(viewPager);

        viewPager.setCurrentItem(index);
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
        title.setText(R.string.str_myorder);
    }
}
