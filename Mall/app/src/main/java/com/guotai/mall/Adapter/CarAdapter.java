package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.Product;
import com.guotai.mall.uitl.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangpan on 17/6/27.
 */

public class CarAdapter extends MyAdapter<CarPro> implements View.OnClickListener{

    OnClick onClick;

    public CarAdapter(Context context, List<CarPro> list){
        super(context, list);
    }

    public void setOnClick(OnClick onClick){
        this.onClick = onClick;
    }

    @Override
    View createView(int position, View convertView, ViewGroup parent) {
        HoldView holdView;
        if(convertView==null){
            holdView = new HoldView();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_car_item, null);
            holdView.car_item = (LinearLayout) convertView.findViewById(R.id.car_item);

            holdView.car_choose = (CheckBox) convertView.findViewById(R.id.car_choose);

            holdView.choose_rl = (RelativeLayout) convertView.findViewById(R.id.choose_rl);

            holdView.car_img = (ImageView) convertView.findViewById(R.id.car_img);

            holdView.car_name = (TextView) convertView.findViewById(R.id.car_name);

            holdView.car_content = (TextView) convertView.findViewById(R.id.car_content);

            holdView.car_weight = (TextView) convertView.findViewById(R.id.car_weight);

            holdView.car_price = (TextView) convertView.findViewById(R.id.car_price);

            holdView.car_add = (TextView) convertView.findViewById(R.id.car_add);
            holdView.car_count = (TextView) convertView.findViewById(R.id.car_count);
            holdView.car_del = (TextView) convertView.findViewById(R.id.car_del);

            holdView.noeffect = (LinearLayout) convertView.findViewById(R.id.noeffect);
            convertView.setTag(holdView);
        }else{
            holdView = (HoldView) convertView.getTag();
        }
        CarPro product = list.get(position);
        holdView.car_item.setTag(R.id.tag_car, position);
        holdView.car_item.setOnClickListener(this);
        holdView.choose_rl.setOnClickListener(this);
        if(product.isChoose){
            holdView.car_choose.setButtonDrawable(R.mipmap.choose);
        }else{
            holdView.car_choose.setButtonDrawable(R.mipmap.unchoose);
        }
        holdView.car_choose.setTag(position);
        holdView.car_choose.setOnClickListener(this);
        Picasso.with(context).load(product.FirstImage).resize(720, 720).centerInside().into(holdView.car_img);
        holdView.car_name.setText(product.ProductName);
        holdView.car_content.setText(product.ProductDescription);
        holdView.car_weight.setText(product.Weight);
        holdView.car_price.setText("¥"+ Common.get2Digital(product.getProductPrice()));
        holdView.car_count.setText(String.valueOf(product.Qty));
        holdView.car_del.setTag(position);
        holdView.car_add.setOnClickListener(this);
        holdView.car_add.setTag(position);
        holdView.car_del.setOnClickListener(this);
        holdView.noeffect.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View view) {
        if(onClick!=null){
            if(view.getId()==R.id.car_add){
                onClick.Add(view);
            }
            else if(view.getId()==R.id.car_del){
                onClick.Del(view);
            }
            else if(view.getId()==R.id.car_choose){
                onClick.Choose(view);
            }
            else if(view.getId()==R.id.car_item){
                onClick.OnItemClick((int)view.getTag(R.id.tag_car));
            }
            else if(view.getId()==R.id.choose_rl || view.getId()==R.id.noeffect){

            }
        }
    }

    static class HoldView{
        LinearLayout car_item, noeffect;
        CheckBox car_choose;
        RelativeLayout choose_rl;
        ImageView car_img;
        TextView car_name;
        TextView car_content;
        TextView car_weight;
        TextView car_price;
        TextView car_add;
        TextView car_count;
        TextView car_del;
    }

    public interface OnClick{
        void Add(View view);
        void Del(View view);
        void Choose(View view);
        void OnItemClick(int position);
    }
}
