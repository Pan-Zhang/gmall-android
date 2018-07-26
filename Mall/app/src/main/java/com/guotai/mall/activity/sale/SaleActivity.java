package com.guotai.mall.activity.sale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.SaleAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.promotion.PromotionActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Promotion;
import com.guotai.mall.model.Sale;
import com.guotai.mall.uitl.Common;

import java.util.List;

public class SaleActivity extends BaseActivity<SalePresent> implements ISaleActivity {

    ListView sale_list;
    SaleAdapter saleAdapter;
    public static List<Sale> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        present = new SalePresent(this);
        initTitle();

        sale_list = (ListView) findViewById(R.id.sale_list);
        saleAdapter = new SaleAdapter(this, list);
        sale_list.setAdapter(saleAdapter);
        sale_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogUtils.showWaitDialog(SaleActivity.this);
                present.getPromotionDetail("api/NotifyMsg/GetPromotionDetail?PromotionID="+list.get(position).PromotionID, SaleActivity.this.getClass().getSimpleName());
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
        title.setText("优惠活动");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list = null;
    }

    @Override
    public void getPromotion(Promotion promotion, boolean success) {
        dialogUtils.disMiss();
        if(success){
            PromotionActivity.promotion = promotion;
            startActivity(new Intent(this, PromotionActivity.class));
        }
        else{
            Common.showToastLong("加载失败");
        }
    }
}
