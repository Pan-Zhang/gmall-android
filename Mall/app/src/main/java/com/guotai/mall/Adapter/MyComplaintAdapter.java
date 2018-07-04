package com.guotai.mall.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.model.Complaint;
import com.guotai.mall.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangpan on 2018/6/25.
 */

public class MyComplaintAdapter extends MyAdapter<Complaint> {
    MySuggestAdapter.OnItemClickListener onItemClickListener;

    public MyComplaintAdapter(Context context, List<Complaint> list) {
        super(context, list);
    }

    public void setOnItemClickListener(MySuggestAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    View createView(final int position, View convertView, ViewGroup parent) {
        MyComplaintAdapter.HolderView holderView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_mysuggest_item, null);
            holderView = new MyComplaintAdapter.HolderView();
            holderView.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holderView.name = (TextView) convertView.findViewById(R.id.name);
            holderView.time = (TextView) convertView.findViewById(R.id.time);
            holderView.content = (TextView) convertView.findViewById(R.id.content);
            holderView.undeal = (ImageView) convertView.findViewById(R.id.undeal);
            holderView.images = (HorizontalScrollView) convertView.findViewById(R.id.images);
            holderView.imageViews = new ImageView[5];
            holderView.imag1 = (ImageView) convertView.findViewById(R.id.imag1);
            holderView.imageViews[0] = holderView.imag1;
            holderView.imag2 = (ImageView) convertView.findViewById(R.id.imag2);
            holderView.imageViews[1] = holderView.imag2;
            holderView.imag3 = (ImageView) convertView.findViewById(R.id.imag3);
            holderView.imageViews[2] = holderView.imag3;
            holderView.imag4 = (ImageView) convertView.findViewById(R.id.imag4);
            holderView.imageViews[3] = holderView.imag4;
            holderView.imag5 = (ImageView) convertView.findViewById(R.id.imag5);
            holderView.imageViews[4] = holderView.imag5;
            holderView.reply = (TextView) convertView.findViewById(R.id.reply);
            holderView.reply_ll = (LinearLayout) convertView.findViewById(R.id.reply_ll);
            convertView.setTag(holderView);
        }
        holderView = (MyComplaintAdapter.HolderView) convertView.getTag();
        Complaint complaint = list.get(position);

        holderView.name.setText(complaint.getName());
        if(!TextUtils.isEmpty(complaint.getAvatar())){
            Picasso.with(context).load(complaint.getAvatar()).resize(200, 200).transform(new CircleTransform(context)).placeholder(R.mipmap.head01).centerCrop().into(holderView.avatar);
        }
        holderView.time.setText(complaint.getTime());
        holderView.content.setText(complaint.getContent());
        if(complaint.getStatus()==0){
            holderView.undeal.setVisibility(View.VISIBLE);
        }
        else{
            holderView.undeal.setVisibility(View.INVISIBLE);
        }

        if (complaint.getImages() == null || complaint.getImages().size() == 0) {
            holderView.images.setVisibility(View.GONE);
        } else {
            holderView.images.setVisibility(View.VISIBLE);
            for (int i = 0; i < complaint.getImages().size(); i++) {
                holderView.imageViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.click(position);
                        }
                    }
                });
                Picasso.with(context).load(complaint.getImages().get(i)).resize(200, 200).centerCrop().into(holderView.imageViews[i]);
            }
        }

        if(TextUtils.isEmpty(complaint.getReply())){
            holderView.reply_ll.setVisibility(View.GONE);
        }
        else{
            holderView.reply_ll.setVisibility(View.VISIBLE);
            holderView.reply.setText(complaint.getReply());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.click(position);
                }
            }
        });
        return convertView;
    }

    public static class HolderView {
        ImageView avatar;
        TextView name;
        TextView time;
        ImageView undeal;
        TextView content;
        HorizontalScrollView images;
        ImageView imag1, imag2, imag3, imag4, imag5;
        ImageView[] imageViews;
        TextView reply;
        LinearLayout reply_ll;
    }

    public interface OnItemClickListener {
        void click(int position);
    }
}