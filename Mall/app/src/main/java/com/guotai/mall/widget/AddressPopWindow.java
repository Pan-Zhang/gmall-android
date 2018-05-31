package com.guotai.mall.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.guotai.mall.Adapter.AddressAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.addAddress.AddAddressActivty;
import com.guotai.mall.model.Address;
import com.guotai.mall.uitl.Common;

import java.util.List;

/**
 * Created by zhangpan on 17/11/10.
 */

public class AddressPopWindow extends PopupWindow {

    private View conentView;
    OnItemClick onItemClick;
    AddressAdapter addressAdapter;
    List<Address> listAdd;

    public AddressPopWindow(final Activity context, List<Address> list) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.layout_address_pop, null);
        listAdd = list;
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w-20);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(Common.dip2px(context, 400));
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        ImageView close = (ImageView) conentView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressPopWindow.this.dismiss();
            }
        });
        Button cancel = (Button) conentView.findViewById(R.id.cancel);
        ListView address_list = (ListView) conentView.findViewById(R.id.address_list);
        addressAdapter = new AddressAdapter(context, listAdd, false);
        address_list.setAdapter(addressAdapter);
        address_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(onItemClick!=null){
                    onItemClick.ItemClick(position);
                    AddressPopWindow.this.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                context.startActivity(new Intent(context, AddAddressActivty.class));
            }
        });
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 10, 5);
        } else {
            this.dismiss();
        }
    }

    public void update(List<Address> list){
        listAdd = list;
        addressAdapter.notifyDataSetChanged();
    }

    public interface OnItemClick{
        void ItemClick(int position);
    }

}
