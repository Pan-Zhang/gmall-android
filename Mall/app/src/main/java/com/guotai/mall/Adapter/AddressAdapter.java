package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Address;

import java.util.List;

/**
 * Created by ez on 2017/6/20.
 */

public class AddressAdapter extends MyAdapter<Address> implements View.OnClickListener{

    OnClick onClick;
    boolean showButton = true;

    public AddressAdapter(Context context, List<Address> list){
        super(context, list);
    }

    public AddressAdapter(Context context, List<Address> list, boolean showButton){
        super(context, list);
        this.showButton = showButton;
    }

    public void setOnClick(OnClick onClick){
        this.onClick = onClick;
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_address_item, null);
        }
        Address address = list.get(position);
        TextView address_name = (TextView) convertView.findViewById(R.id.address_name);
        address_name.setText(address.ReceiverName);
        TextView address_tel = (TextView) convertView.findViewById(R.id.address_tel);
        address_tel.setText(address.ReceiverMobile);
        TextView address_addr = (TextView) convertView.findViewById(R.id.address_addr);
        if(address.ProvinceName.equals(address.CityName)){
            address_addr.setText(address.ProvinceName+address.DistrictName+address.ReceiverAddress);
        }
        else{
            address_addr.setText(address.ProvinceName+address.CityName+address.DistrictName+address.ReceiverAddress);
        }
        TextView default_address = (TextView) convertView.findViewById(R.id.default_address);
        if(address.IsDefault){
            default_address.setVisibility(View.VISIBLE);
        }else{
            default_address.setVisibility(View.INVISIBLE);
        }

        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setTag(position);
        delete.setOnClickListener(this);
        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        edit.setTag(position);
        edit.setOnClickListener(this);
        if(!showButton){
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete:
                if(onClick!=null){
                    onClick.delete(v);
                }
                break;

            case R.id.edit:
                if(onClick!=null){
                    onClick.edit(v);
                }
                break;
        }
    }

    public interface OnClick{
        void edit(View view);
        void delete(View view);
    }
}
