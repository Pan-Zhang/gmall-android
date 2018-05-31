package com.guotai.mall.activity.CategorySearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.HomeAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.product.ProductActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;

import java.util.List;

/**
 * Created by zhangpan on 2018/4/24.
 */

public class CategorySearchActivity extends BaseActivity<CategorySearchPresent> implements ICategorySearch {

    public static List<ProductEx> list_product;
    public ListView product_lv;
    HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_search);

        present = new CategorySearchPresent(this);

        initTitle();
        product_lv = (ListView) findViewById(R.id.product_lv);
        homeAdapter = new HomeAdapter(this, list_product);
        product_lv.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view) {
                int position = Integer.parseInt(view.getTag().toString());
                final ProductEx product = list_product.get(position);
                dialogUtils.showWaitDialog(CategorySearchActivity.this);
                present.GetDetail(Common.getProductDetailURL(product.ProductID, Common.getUserID()), CategorySearchActivity.this.getClass().getSimpleName());

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

        title.setText(getIntent().getStringExtra("title"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list_product = null;
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
