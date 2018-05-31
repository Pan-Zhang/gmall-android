package com.guotai.mall.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.guotai.mall.Adapter.DirectAdapter;
import com.guotai.mall.MainActivity;
import com.guotai.mall.R;

/**
 * Created by zhangpan on 2018/4/23.
 */

public class DirectActivity extends Activity {

    ViewPager direct_pager;
    DirectAdapter directAdapter;
    Button enter_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct);

        int[] pics = new int[]{R.mipmap.direct1, R.mipmap.direct2, R.mipmap.direct3};
        direct_pager = (ViewPager) findViewById(R.id.direct_pager);
        directAdapter = new DirectAdapter(this, pics);
        direct_pager.setAdapter(directAdapter);
        direct_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    enter_main.setClickable(true);
                }
                else{
                    enter_main.setClickable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        enter_main = (Button) findViewById(R.id.enter_main);
        enter_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DirectActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
