package com.guotai.mall.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.guotai.mall.fragment.myOrder.MyOrderFragment;

/**
 * Created by zhangpan on 17/7/26.
 */

public class MyOrderAdapter extends FragmentPagerAdapter {

    public MyOrderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        MyOrderFragment fragment = new MyOrderFragment();
        fragment.position = position;
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
