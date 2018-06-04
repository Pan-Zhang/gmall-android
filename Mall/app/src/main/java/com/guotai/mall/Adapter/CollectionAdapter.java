package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.CollectPro;
import com.guotai.mall.model.Product;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.widget.SlidingMenu;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ez on 2017/6/19.
 */

public class CollectionAdapter extends MyAdapter<CollectPro>{

    DeleteListener deleteListener;

    public CollectionAdapter(Context context, List<CollectPro> list){
        super(context, list);
    }

    public void setDeleteListener(DeleteListener deleteListener){
        this.deleteListener = deleteListener;
    }

    @Override
    View createView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_collection_item, null);
        }
        CollectPro product = list.get(position);
        ImageView collec_iv = (ImageView) convertView.findViewById(R.id.collec_iv);
        Picasso.with(context).load(product.FirstImage).fit().into(collec_iv);
        TextView collec_name = (TextView) convertView.findViewById(R.id.collec_name);
        collec_name.setText(product.ProductName);
        TextView collec_price = (TextView) convertView.findViewById(R.id.collec_price);
        collec_price.setText(product.ProductDescription);
        TextView collec_date = (TextView) convertView.findViewById(R.id.collec_date);
        collec_date.setText("¥" + product.Price);
        SlidingMenu slidingMenu = (SlidingMenu) convertView.findViewById(R.id.slide_menu);
        slidingMenu.setOnClickListener(new SlidingMenu.OnClickListener(){

            @Override
            public void ClickLeft(View view) {
                if(deleteListener!=null){
                    view.setTag(position);
                    deleteListener.OnItemClick(view);
                }
            }

            @Override
            public void ClickRight(View view) {
                view.setTag(position);
                if(deleteListener!=null){
                    deleteListener.Delete(view);
                }
            }
        });
        return convertView;
    }

    public interface DeleteListener{
        void Delete(View view);
        void OnItemClick(View view);
    }
}
