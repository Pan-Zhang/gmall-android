package com.guotai.mall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.guotai.mall.R;
import com.guotai.mall.model.Promotion;
import com.guotai.mall.model.PromotionDetails;
import com.squareup.picasso.Picasso;

public class PromotionAdapter extends BaseAdapter implements View.OnClickListener {

    public Context context;
    public Promotion promotion;
    ImageClick imageClick;

    public PromotionAdapter(Context context, Promotion promotion) {
        this.context = context;
        this.promotion = promotion;
    }

    public void setImageClick(ImageClick imageClick){
        this.imageClick = imageClick;
    }

    @Override
    public int getCount() {
        return promotion.PromotionDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return promotion.PromotionDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_promotion_detail_item, null);
        }
        ImageView top = (ImageView) convertView.findViewById(R.id.top);
        Picasso.with(context).load(promotion.PromotionDetails.get(position).ImagePath).resize(1096, 400).centerCrop().into(top);
        top.setOnClickListener(this);
        top.setTag(promotion.PromotionDetails.get(position).SubID);
        ImageView center_left = (ImageView) convertView.findViewById(R.id.center_left);
        center_left.setOnClickListener(this);
        ImageView center_right = (ImageView) convertView.findViewById(R.id.center_right);
        center_right.setOnClickListener(this);
        ImageView bottom_left = (ImageView) convertView.findViewById(R.id.bottom_left);
        bottom_left.setOnClickListener(this);
        ImageView bottom_right = (ImageView) convertView.findViewById(R.id.bottom_right);
        bottom_right.setOnClickListener(this);
        PromotionDetails promotionDetails = promotion.PromotionDetails.get(position);
        for(int i=0; i<promotionDetails.PromotionImages.size(); i++){
            if(i==0){
                center_left.setTag(promotionDetails.PromotionImages.get(i).ProductID);
                Picasso.with(context).load(promotionDetails.PromotionImages.get(i).ImagePath).resize(540, 720).centerCrop().into(center_left);
            }
            else if(i==1){
                center_right.setTag(promotionDetails.PromotionImages.get(i).ProductID);
                Picasso.with(context).load(promotionDetails.PromotionImages.get(i).ImagePath).resize(540, 720).centerCrop().into(center_right);
            }
            else if(i==2){
                bottom_left.setTag(promotionDetails.PromotionImages.get(i).ProductID);
                Picasso.with(context).load(promotionDetails.PromotionImages.get(i).ImagePath).resize(540, 720).centerCrop().into(bottom_left);
            }
            else if(i==3){
                bottom_right.setTag(promotionDetails.PromotionImages.get(i).ProductID);
                Picasso.with(context).load(promotionDetails.PromotionImages.get(i).ImagePath).resize(540, 720).centerCrop().into(bottom_right);
            }
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top:
                if(imageClick!=null){
                    imageClick.onClickPro(v);
                }
                break;

            case R.id.center_left:
            case R.id.center_right:
            case R.id.bottom_left:
            case R.id.bottom_right:
                if(imageClick!=null){
                    imageClick.onClick(v);
                }
                break;
        }
    }

    public interface ImageClick{
        void onClick(View imageView);
        void onClickPro(View view);
    }
}
