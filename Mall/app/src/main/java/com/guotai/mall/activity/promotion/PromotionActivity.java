package com.guotai.mall.activity.promotion;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.PromotionAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.product.ProductActivity;
import com.guotai.mall.activity.subPromotion.SubPromotionActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.model.Promotion;
import com.guotai.mall.model.SubPromotion;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.ProductBigImage;
import com.guotai.mall.widget.PromotionImage2;
import com.squareup.picasso.Picasso;

public class PromotionActivity extends BaseActivity<PromotionPresent> implements IPromotionActivity {

    ListView list;
    public static Promotion promotion;
    PromotionAdapter promotionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        initTitle();

        present = new PromotionPresent(this);

        list = (ListView) findViewById(R.id.list);
        promotionAdapter = new PromotionAdapter(this, promotion);
        promotionAdapter.setImageClick(new PromotionAdapter.ImageClick() {
            @Override
            public void onClick(View imageView) {
                dialogUtils.showWaitDialog(PromotionActivity.this);
                int productId = (int)imageView.getTag();
                present.GetDetail(Common.getProductDetailURL(String.valueOf(productId), Common.getUserID()), getClass().getSimpleName());
            }

            @Override
            public void onClickPro(View view) {
                dialogUtils.showWaitDialog(PromotionActivity.this);
                int SubID = (int)view.getTag();
                present.getSubPromotion("api/NotifyMsg/GetSubPromotionDetail?SubID="+SubID + "&idxPage=1&sizePage=20", PromotionActivity.this.getClass().getSimpleName());
            }
        });
        list.setAdapter(promotionAdapter);
        View header = LayoutInflater.from(this).inflate(R.layout.layout_promotion_head, null);
        ProductBigImage head = (ProductBigImage) header.findViewById(R.id.head);
        Picasso.with(this).load(promotion.PromotionImages.get(0).ImagePath).fit().into(head);

        View tail = LayoutInflater.from(this).inflate(R.layout.layout_promotion_tail, null);
        PromotionImage2 tail_iv = (PromotionImage2) tail.findViewById(R.id.tail_iv);
        Picasso.with(this).load(promotion.PromotionImages.get(1).ImagePath).fit().into(tail_iv);

        list.addHeaderView(header);
        list.addFooterView(tail);

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
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("活动详情");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        promotion = null;
    }

    @Override
    public void gotoSubDetail(SubPromotion subPromotion, boolean success) {
        dialogUtils.disMiss();
        if(success){
            SubPromotionActivity.subPromotion = subPromotion;
            startActivity(new Intent(this, SubPromotionActivity.class));
        }
        else{
            Common.showToastLong("加载失败");
        }
    }

    @Override
    public void GotoDetail(ProductEx productEx) {
        dialogUtils.disMiss();
        if(productEx!=null){
            ProductActivity.product = productEx;
            startActivity(new Intent(this, ProductActivity.class));
        }
    }
}
