package com.guotai.mall.activity.subPromotion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.SubPromotionAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.product.ProductActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.model.SubPromotion;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.HeaderGridView;
import com.guotai.mall.widget.PromotionImage2;
import com.squareup.picasso.Picasso;

public class SubPromotionActivity extends BaseActivity<SubPromotionPresent> implements ISubPromotion {

    HeaderGridView gridView;
    SubPromotionAdapter subPromotionAdapter;
    public static SubPromotion subPromotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_promotion);

        initTitle();
        present = new SubPromotionPresent(this);
        gridView = (HeaderGridView) findViewById(R.id.grid_view);
        View head = LayoutInflater.from(this).inflate(R.layout.layout_promotion_tail, null);
        PromotionImage2 tail_iv = (PromotionImage2) head.findViewById(R.id.tail_iv);
        Picasso.with(this).load(subPromotion.ImagePath).fit().into(tail_iv);
        gridView.addHeaderView(head);
        subPromotionAdapter = new SubPromotionAdapter(this, subPromotion.ProductList);
        subPromotionAdapter.setSaleTag(subPromotion.ProductTag);
        gridView.setAdapter(subPromotionAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(id<0){
                    return;
                }
                dialogUtils.showWaitDialog(SubPromotionActivity.this);
                ProductEx productEx = subPromotion.ProductList.get((int)id);
                present.GetDetail(Common.getProductDetailURL(productEx.ProductID, Common.getUserID()), SubPromotionActivity.this.getClass().getSimpleName());
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
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(subPromotion.Title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subPromotion = null;
    }

    @Override
    public void GotoDetail(ProductEx product) {
        dialogUtils.disMiss();
        if(product!=null){
            ProductActivity.product = product;
            startActivity(new Intent(this, ProductActivity.class));
        }
    }
}
