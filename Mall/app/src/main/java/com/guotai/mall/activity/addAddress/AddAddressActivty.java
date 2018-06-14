package com.guotai.mall.activity.addAddress;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.CityAdapter;
import com.guotai.mall.Adapter.CountyAdapter;
import com.guotai.mall.Adapter.ProvinceListAdapter;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.City;
import com.guotai.mall.model.County;
import com.guotai.mall.model.Province;
import com.guotai.mall.uitl.Common;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by ez on 2017/6/20.
 */

public class AddAddressActivty extends BaseActivity<AddAddressPresent> implements IAddAddressactivity, View.OnClickListener {

    TextView title;
    public static Address address;
    public Button province, city, county, submit;
    public EditText name, telphone, detail;
    public CheckBox isDefault;
    public List<Province> province_list;
    public List<City> city_list;
    public List<County> county_list;
    public ProvinceListAdapter provinceListAdapter;
    public CityAdapter cityAdapter;
    public CountyAdapter countyAdapter;
    public Province current_province;
    public City current_city;
    public County current_county;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        present = new AddAddressPresent(this);

        initTitle();
        present.loadProvince("api/UserReceiver/GetProvinceList", getClass().getSimpleName());
        name = (EditText) findViewById(R.id.name);
        telphone = (EditText) findViewById(R.id.telephone);
        detail = (EditText) findViewById(R.id.detail);
        province = (Button) findViewById(R.id.province);
        province.setOnClickListener(this);
        city = (Button) findViewById(R.id.city);
        city.setOnClickListener(this);
        county = (Button) findViewById(R.id.county);
        county.setOnClickListener(this);
        submit = (Button) findViewById(R.id.submit);
        isDefault = (CheckBox) findViewById(R.id.isDefault);
        if(address!=null){
            name.setText(address.ReceiverName);
            province.setText(address.ProvinceName);
            city.setText(address.CityName);
            present.loadCity("api/UserReceiver/GetCityList?ProvinceID=" + address.ProvinceID, AddAddressActivty.this.getClass().getSimpleName());
            county.setText(address.DistrictName);
            present.loadCounty("api/UserReceiver/GeDistrictList?CityID=" + address.CityID, AddAddressActivty.this.getClass().getSimpleName());
            telphone.setText(address.ReceiverMobile);
            detail.setText(address.ReceiverAddress);
            if(address.IsDefault){
                isDefault.setChecked(true);
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Common.isMobile(telphone.getText().toString())){
                    Common.showToastShort("请输入正确的联系方式");
                    return;
                }
                if(province.getText().toString().equals("请选择省份") || city.getText().toString().equals("请选择城市") || county.getText().toString().equals("请选择区县")){
                    Common.showToastShort("省市区信息不能为空！");
                    return;
                }
                Map<String, String> map = new ArrayMap<String, String>();
                map.put("UserID", Common.getUserID());
                if(address!=null){
                    map.put("UserReceiverID", address.UserReceiverID);
                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
                    String date = sDateFormat.format(new java.util.Date());
                    map.put("LastUpdateTime", date);
                }

                if(TextUtils.isEmpty(name.getText().toString())){
                    Common.showToastShort("请填写收货人姓名");
                    return;
                }
                map.put("ReceiverName", name.getText().toString());
                if(current_province==null){
                    if(address!=null){
                        map.put("ProvinceID", address.ProvinceID);
                        map.put("ProvinceName", address.ProvinceName);
                    }
                    else{
                        Common.showToastShort("请选择省");
                        return;
                    }
                }
                else{
                    map.put("ProvinceID", current_province.ProvinceID);
                    map.put("ProvinceName", current_province.ProvinceName);
                }
                if(current_city==null){
                    if(address!=null){
                        map.put("CityID", address.CityID);
                        map.put("CityName", address.CityName);
                    }
                    else{
                        Common.showToastShort("请选择市");
                        return;
                    }
                }
                else{
                    map.put("CityID", current_city.CityID);
                    map.put("CityName", current_city.CityName);
                }
                if(current_county==null){
                    if(address!=null){
                        map.put("DistrictID", address.DistrictID);
                        map.put("DistrictName", address.DistrictName);
                    }
                    else{
                        Common.showToastShort("请选择区县");
                        return;
                    }
                }
                else{
                    map.put("DistrictID", current_county.DistrictID);
                    map.put("DistrictName", current_county.DistrictName);
                }
                if(TextUtils.isEmpty(detail.getText().toString())){
                    Common.showToastShort("请填写详细地址");
                    return;
                }
                map.put("ReceiverAddress", detail.getText().toString());
                if(TextUtils.isEmpty(telphone.getText().toString())){
                    Common.showToastShort("请填写电话号码");
                    return;
                }
                map.put("ReceiverMobile", telphone.getText().toString());
                map.put("IsDefault", isDefault.isChecked()?"true":"false");
                dialogUtils.showWaitDialog(AddAddressActivty.this, "正在提交数据...");
                if(address!=null){
                    present.editAddress("api/UserReceiver/EditUserReceiver", map, AddAddressActivty.this.getClass().getSimpleName());
                }
                else{
                    present.addAddress("api/UserReceiver/AddUserReceiver", map, AddAddressActivty.this.getClass().getSimpleName());
                }
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
        if(address==null){
            title.setText(R.string.str_add_address);
        }else{
            title.setText(R.string.str_edit_address);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        address = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.province:
                final Dialog dialog = new Dialog(AddAddressActivty.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_choose_address);
                ListView listView = (ListView) dialog.findViewById(R.id.address_list);
                provinceListAdapter = new ProvinceListAdapter(AddAddressActivty.this, province_list);
                listView.setAdapter(provinceListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        current_province = province_list.get(position);
                        province.setText(current_province.ProvinceName);
                        city.setText("请选择城市");
                        county.setText("请选择区县");
                        present.loadCity("api/UserReceiver/GetCityList?ProvinceID=" + current_province.ProvinceID, AddAddressActivty.this.getClass().getSimpleName());
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;

            case R.id.city:
                final Dialog dialog1 = new Dialog(AddAddressActivty.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.layout_choose_address);
                ListView listView1 = (ListView) dialog1.findViewById(R.id.address_list);
                cityAdapter = new CityAdapter(AddAddressActivty.this, city_list);
                listView1.setAdapter(cityAdapter);
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        current_city = city_list.get(position);
                        city.setText(current_city.CityName);
                        county.setText("请选择区县");
                        present.loadCounty("api/UserReceiver/GeDistrictList?CityID=" + current_city.CityID, AddAddressActivty.this.getClass().getSimpleName());
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
                break;

            case R.id.county:
                final Dialog dialog2 = new Dialog(AddAddressActivty.this);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.layout_choose_address);
                ListView listView2 = (ListView) dialog2.findViewById(R.id.address_list);
                countyAdapter = new CountyAdapter(AddAddressActivty.this, county_list);
                listView2.setAdapter(countyAdapter);
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        current_county = county_list.get(position);
                        county.setText(current_county.DistrictName);
                        dialog2.dismiss();
                    }
                });
                dialog2.show();
                break;
        }
    }

    @Override
    public void refreshProvince(List<Province> list) {
        if(list!=null){
            province_list = list;
        }
    }

    @Override
    public void refreshCity(List<City> list) {
        if(list!=null){
            city_list = list;
        }
    }

    @Override
    public void refreshCounty(List<County> list) {
        if(list!=null){
            county_list = list;
        }
    }

    @Override
    public void addSuccess(boolean isSuccess) {
        dialogUtils.disMiss();
        if(isSuccess){
            finish();
        }
    }
}
