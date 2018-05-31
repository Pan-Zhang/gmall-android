package com.guotai.mall.activity.managerAddress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.AddressAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.addAddress.AddAddressActivty;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Address;
import com.guotai.mall.uitl.Common;

import java.util.List;
import java.util.Map;


/**
 * Created by ez on 2017/6/20.
 */

public class ManagerActivity extends BaseActivity<ManagerPresent> implements IManageractivity{

    TextView noaddress_tv;
    ListView address_lv;
    AddressAdapter addressAdapter;
    List<Address> list;
    TextView title;
    SwipeRefreshLayout refreshLayout;
    String url = "api/UserReceiver/GetUserReceiverList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        present = new ManagerPresent(this);

        initTitle();

        noaddress_tv = (TextView) findViewById(R.id.noaddress_tv);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorWhite);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressBackgroundColor(R.color.colorApp);
        refreshLayout.setProgressViewEndTarget(true, 200);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                present.loaddata(url, getClass().getSimpleName());
            }
        });
        address_lv = (ListView) findViewById(R.id.address_lv);
        addressAdapter = new AddressAdapter(this, list);
        address_lv.setAdapter(addressAdapter);
        url = url + "?UserID=" + Common.getUserID();
        addressAdapter.setOnClick(new AddressAdapter.OnClick() {
            @Override
            public void edit(View view) {
                int position = (int) view.getTag();
                Address address = list.get(position);
                AddAddressActivty.address = address;
                startActivity(new Intent(ManagerActivity.this, AddAddressActivty.class));
            }

            @Override
            public void delete(View view) {
                dialogUtils.showWaitDialog(ManagerActivity.this, "正在删除...");
                int position = (int) view.getTag();
                Map<String, String> map = new ArrayMap<String, String>();
                map.put("UserID", Common.getUserID());
                map.put("ReceiverIDS", "["+list.get(position).UserReceiverID+"]");
                present.deleteAddress("api/UserReceiver/DeleteUserReceiver", map, position, ManagerActivity.this.getClass().getSimpleName());
            }
        });
        dialogUtils.showWaitDialog(this);

        Button add_address = (Button) findViewById(R.id.add_address);
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerActivity.this, AddAddressActivty.class));
            }
        });
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
        title.setText(R.string.str_manage_address);
    }

    @Override
    protected void onResume() {
        super.onResume();
        present.loaddata(url, getClass().getSimpleName());
    }

    @Override
    public void refresh(List<Address> list) {
        dialogUtils.disMiss();
        refreshLayout.setRefreshing(false);
        if(list!=null){
            this.list = list;
            addressAdapter.update(list);
            if(list!=null && list.size()>0){
                noaddress_tv.setVisibility(View.INVISIBLE);
            }else{
                noaddress_tv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void deleteSuccess(boolean success, int position) {
        dialogUtils.disMiss();
        if(success){
            list.remove(position);
            addressAdapter.update(list);
        }
    }
}
