package com.guotai.mall.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.Adapter.MeAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.brower.BrowerActivity;
import com.guotai.mall.activity.collection.CollectionActivity;
import com.guotai.mall.activity.help.HelpActivity;
import com.guotai.mall.activity.login.LoginActivity;
import com.guotai.mall.activity.managerAddress.ManagerActivity;
import com.guotai.mall.activity.myOrder.MyOrderActivity;
import com.guotai.mall.activity.setting.SettingActivity;
import com.guotai.mall.activity.suggest.SuggestActivity;
import com.guotai.mall.base.BaseFragment;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.CircleTransform;
import com.squareup.picasso.Picasso;

/**
 * Created by ez on 2017/6/15.
 */

public class MeFragment extends BaseFragment<MePresent> implements IMefragment, View.OnClickListener{

    GridView me_grid;
    MeAdapter meAdapter;
    ImageView setting, avatar;
    LinearLayout myorder_ll, help_ll;
    Button wait_pay, wait_send, wait_receive, server;
    TextView username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        ImageView avatar = (ImageView) rootView.findViewById(R.id.avatar);
        Picasso.with(getActivity()).load(R.mipmap.head01).resize(200, 200).transform(new CircleTransform(getContext(), 3, R.color.colorWhite)).centerCrop().into(avatar);

        myorder_ll = (LinearLayout) rootView.findViewById(R.id.myorder_ll);
        myorder_ll.setOnClickListener(this);

        help_ll = (LinearLayout) rootView.findViewById(R.id.help_ll);
        help_ll.setOnClickListener(this);

        wait_pay = (Button) rootView.findViewById(R.id.wait_pay);
        wait_send = (Button) rootView.findViewById(R.id.wait_send);
        wait_receive = (Button) rootView.findViewById(R.id.wait_receive);
        server = (Button) rootView.findViewById(R.id.server);
        avatar = (ImageView) rootView.findViewById(R.id.avatar);
        avatar.setOnClickListener(this);
        wait_pay.setOnClickListener(this);
        wait_send.setOnClickListener(this);
        wait_receive.setOnClickListener(this);
        server.setOnClickListener(this);

        username = (TextView) rootView.findViewById(R.id.username);
        username.setOnClickListener(this);

        me_grid = (GridView) rootView.findViewById(R.id.me_grid);
        meAdapter = new MeAdapter(getActivity());
        me_grid.setAdapter(meAdapter);
        me_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), ManagerActivity.class));
                            break;

                        case 1:
                            startActivity(new Intent(getActivity(), SuggestActivity.class));
                            break;

                        case 2:
                            startActivity(new Intent(getActivity(), CollectionActivity.class));
                            break;

                        case 3:
                            startActivity(new Intent(getActivity(), BrowerActivity.class));
                            break;

                        case 4:
                            startActivity(new Intent(getActivity(), HelpActivity.class));
                            break;
                    }
                }

            }
        });

        setting = (ImageView) rootView.findViewById(R.id.setting);
        setting.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(Common.getUser())){
            username.setText("注册/登录");
        }
        else{
            username.setText(Common.getUser());
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        switch (view.getId()){
            case R.id.setting:
            case R.id.avatar:
            case R.id.username:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }

                break;

            case R.id.myorder_ll:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    intent.addFlags(0);
                    startActivity(intent);
                }
                break;

            case R.id.wait_pay:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    intent.addFlags(0);
                    startActivity(intent);
                }
                break;

            case R.id.wait_receive:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    intent.addFlags(2);
                    startActivity(intent);
                }
                break;

            case R.id.wait_send:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    intent.addFlags(1);
                    startActivity(intent);
                }
                break;

            case R.id.server:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    intent.addFlags(3);
                    startActivity(intent);
                }
                break;

            case R.id.help_ll:
                    startActivity(new Intent(getActivity(), HelpActivity.class));
                    break;
        }
    }
}
