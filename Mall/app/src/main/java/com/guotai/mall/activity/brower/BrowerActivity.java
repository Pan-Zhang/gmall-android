package com.guotai.mall.activity.brower;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.CollectionAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.collection.CollectionActivity;
import com.guotai.mall.activity.product.ProductActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.CollectPro;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.VpSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpan on 17/6/27.
 */

public class BrowerActivity extends BaseActivity<BrowerPresent> implements IBroweractivity {

    ListView collection_lv;
    CollectionAdapter collectionAdapter;
    List<CollectPro> list;
    String url = "api/Product/GetRecentProuct";
    TextView title;
    LinearLayout no_collect;
    SwipeRefreshLayout refreshLayout;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        present = new BrowerPresent(this);

        initTitle();

        no_collect = (LinearLayout) findViewById(R.id.no_collect);
        no_collect.setVisibility(View.INVISIBLE);

        refreshLayout = (VpSwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorWhite);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressBackgroundColor(R.color.colorApp);
        refreshLayout.setProgressViewEndTarget(true, 200);
        url = url + "?UserID=" + Common.getUserID() + "&idxPage=1&sizePage=20";
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                present.loaddata(getClass().getSimpleName(), url);
            }
        });

        collection_lv = (ListView) findViewById(R.id.collection_lv);
        list = new ArrayList<>();
        collectionAdapter = new CollectionAdapter(this, list);
        collection_lv.setAdapter(collectionAdapter);
        collectionAdapter.setDeleteListener(new CollectionAdapter.DeleteListener() {
            @Override
            public void Delete(View view) {
                int tag = (int)view.getTag();
                list.remove(tag);
                collectionAdapter.update(list);
                if (list.size() == 0) {
                    no_collect.setVisibility(View.VISIBLE);
                }else{
                    no_collect.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void OnItemClick(View view) {
                int position = (int)view.getTag();
                CollectPro collectPro = list.get(position);
                present.GetDetail(Common.getProductDetailURL(collectPro.ProductID, Common.getUserID()), CollectionActivity.class.getSimpleName());
                dialogUtils.showWaitDialog(BrowerActivity.this);
            }
        });

        present.loaddata(getClass().getSimpleName(), url);
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
        title = (TextView) findViewById(R.id.title);
        title.setText(R.string.str_brower);
    }

    @Override
    public void refresh(List<CollectPro> list) {
        this.list = list;
        refreshLayout.setRefreshing(false);
        if (list==null || list.size() == 0) {
            no_collect.setVisibility(View.VISIBLE);
        }else{
            no_collect.setVisibility(View.INVISIBLE);
        }
        collectionAdapter.update(list);
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
