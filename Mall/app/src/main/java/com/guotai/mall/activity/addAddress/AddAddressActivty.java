package com.guotai.mall.activity.addAddress;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.City;
import com.guotai.mall.model.County;
import com.guotai.mall.model.Province;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.FullDialog;
import com.guotai.mall.widget.PickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ez on 2017/6/20.
 */

public class AddAddressActivty extends BaseActivity<AddAddressPresent> implements IAddAddressactivity, View.OnClickListener {

    TextView title;
    public static Address address;
    public Button province, submit;
    public EditText name, telphone, detail;
    public CheckBox isDefault;
    public List<Province> province_list;
    public List<City> city_list;
    public List<County> county_list;
    public List<String> provins, cities, districts;
    public Province current_province;
    public City current_city;
    public County current_county;
    private Dialog dialog;
    PickerView provincePicker, cityPicker, districtPicker;
    County tem_county = null;
    Province tem_province=null;
    City tem_city=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        present = new AddAddressPresent(this);

        initTitle();
        name = (EditText) findViewById(R.id.name);
        telphone = (EditText) findViewById(R.id.telephone);
        detail = (EditText) findViewById(R.id.detail);
        province = (Button) findViewById(R.id.province);
        province.setOnClickListener(this);
//        city = (Button) findViewById(R.id.city);
//        city.setOnClickListener(this);
//        county = (Button) findViewById(R.id.county);
//        county.setOnClickListener(this);
        submit = (Button) findViewById(R.id.submit);
        isDefault = (CheckBox) findViewById(R.id.isDefault);
        initPicker();
        if(address!=null){
            name.setText(address.ReceiverName);
            province.setText(address.ProvinceName + " " + address.CityName + " " + address.DistrictName);
//            city.setText(address.CityName);
//            present.loadCity("api/UserReceiver/GetCityList?ProvinceID=" + address.ProvinceID, AddAddressActivty.this.getClass().getSimpleName());
//            county.setText(address.DistrictName);
//            present.loadCounty("api/UserReceiver/GeDistrictList?CityID=" + address.CityID, AddAddressActivty.this.getClass().getSimpleName());
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
                if(province.getText().toString().equals("选择区县市信息")){
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
        present.loadProvince("api/UserReceiver/GetProvinceList", getClass().getSimpleName());
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

    public void initPicker(){
        dialog = new FullDialog(AddAddressActivty.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_choose_address);
        provincePicker = (PickerView) dialog.findViewById(R.id.province);
        provincePicker.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                for(Province province:province_list){
                    if(province.ProvinceName.equals(text)){
                        tem_province = province;
                    }
                }
                if(tem_province!=null)
                present.loadCity("api/UserReceiver/GetCityList?ProvinceID=" + tem_province.ProvinceID, AddAddressActivty.this.getClass().getSimpleName());
            }
        });
        cityPicker = (PickerView) dialog.findViewById(R.id.city);
        cityPicker.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                for(City city:city_list){
                    if(city.CityName.equals(text)){
                        tem_city = city;
                    }
                }
                if(tem_city!=null)
                present.loadCounty("api/UserReceiver/GeDistrictList?CityID=" + tem_city.CityID, AddAddressActivty.this.getClass().getSimpleName());
            }
        });
        districtPicker = (PickerView) dialog.findViewById(R.id.district);
        districtPicker.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                for(County county:county_list){
                    if(county.DistrictName.equals(text)){
                        tem_county = county;
                    }
                }
            }
        });
        TextView cancel_btn = (TextView) dialog.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView ensure_btn = (TextView) dialog.findViewById(R.id.ensure_btn);
        ensure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tem_province==null || tem_county==null || tem_city==null){
                    Common.showToastShort("请选择区县市信息");
                    return;
                }
                current_county = tem_county;
                current_city = tem_city;
                current_province = tem_province;
                province.setText(current_province.ProvinceName + " " + current_city.CityName + " " + current_county.DistrictName);
//                city.setText(current_city.CityName);
//                county.setText(current_county.DistrictName);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.province:
//            case R.id.city:
//            case R.id.county:
                if(provins!=null){
                    provincePicker.setData(provins);
                }
                if(cities!=null){
                    cityPicker.setData(cities);
                }
                if(districts!=null){
                    districtPicker.setData(districts);
                }
                dialog.show();
                break;
        }
    }

    @Override
    public void refreshProvince(List<Province> list) {
        if(list!=null){
            province_list = list;
            if(provins==null){
                provins = new ArrayList<>();
            }
            else{
                provins.clear();
            }
            for(int i=0; i<province_list.size(); i++){
                provins.add(province_list.get(i).ProvinceName);
            }
            provincePicker.setData(provins);
            provincePicker.performSelect();
        }
    }

    @Override
    public void refreshCity(List<City> list) {
        if(list!=null) {
            city_list = list;
            if (cities == null) {
                cities = new ArrayList<>();
            } else {
                cities.clear();
            }
            for (int i = 0; i < city_list.size(); i++) {
                cities.add(city_list.get(i).CityName);
            }
            cityPicker.setData(cities);
            cityPicker.performSelect();
        }
    }

    @Override
    public void refreshCounty(List<County> list) {
        if(list!=null){
            county_list = list;
            if (districts == null) {
                districts = new ArrayList<>();
            } else {
                districts.clear();
            }
            for (int i = 0; i < county_list.size(); i++) {
                districts.add(county_list.get(i).DistrictName);
            }
            districtPicker.setData(districts);
            districtPicker.performSelect();
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
