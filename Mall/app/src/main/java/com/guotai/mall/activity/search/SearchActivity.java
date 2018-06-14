package com.guotai.mall.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.HomeAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.product.ProductActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.FlowLayout;

import java.util.List;

/**
 * Created by zhangpan on 17/7/26.
 */

public class SearchActivity extends BaseActivity<SearchPresent> implements ISearchactivity {

    private String[] hot_lb = {"足金情侣钻戒","项链","女士吊坠","旋转木马戒指"};
    private String[] history_lb = {"足金情侣钻戒","项链","女士吊坠","旋转木马戒指", "十全大金戒指"};
    FlowLayout hot, history;
    TextView cancel;
    EditText keyword;
    ImageView search_img, back;
    ListView result_list;
    List<ProductEx> list;
    HomeAdapter productAdapter;
    LinearLayout keyword_ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        present = new SearchPresent(this);

        hot = (FlowLayout) findViewById(R.id.hot);
        history = (FlowLayout) findViewById(R.id.history);
        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_img = (ImageView) findViewById(R.id.search_img);
        keyword = (EditText) findViewById(R.id.keyword);
        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    keyword_ll.setVisibility(View.VISIBLE);
                }
            }
        });
        keyword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    search();
                }
                return false;
            }
        });
        initLabel();

        result_list = (ListView) findViewById(R.id.result_list);
        productAdapter = new HomeAdapter(this, list);
        productAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view) {
                int position = Integer.parseInt(view.getTag().toString());
                final ProductEx product = list.get(position);
                dialogUtils.showWaitDialog(getApplicationContext());
                present.GetDetail(Common.getProductDetailURL(product.ProductID, Common.getUserID()), getClass().getSimpleName());
            }
        });
        result_list.setAdapter(productAdapter);

        keyword_ll = (LinearLayout) findViewById(R.id.keyword_ll);

    }

    private void search(){
        if(keyword.getText().toString().length()==0){
            Common.showToastShort("请输入关键字");
            return;
        }
        // 先隐藏键盘
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        dialogUtils.showWaitDialog(SearchActivity.this);
        present.search("api/Product/GetProductList?idxPage=1&sizePage=20&searchWord="+keyword.getText().toString(), SearchActivity.this.getClass().getSimpleName());
    }

    // 初始化标签
    private void initLabel() {
        for (int i = 0; i < history_lb.length; i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(
                    R.layout.search_label_tv, history, false);
            tv.setText(history_lb[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加入搜索历史纪录记录
                    keyword.setText(str);
                    search();
                }
            });
            history.addView(tv);
        }

        for (int i = 0; i < hot_lb.length; i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(
                    R.layout.search_label_tv, hot, false);
            tv.setText(hot_lb[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加入搜索历史纪录记录
                    keyword.setText(str);
                    search();
                }
            });
            hot.addView(tv);
        }
    }

    @Override
    public void searchSuccess(List<ProductEx> list, boolean success) {
        dialogUtils.disMiss();
        this.list = list;
        if(list==null || list.size()==0){
            Common.showToastShort("未搜到相关结果");
        }
        if(success){
            productAdapter.update(list);
            keyword_ll.setVisibility(View.GONE);
        }
        else{

        }
    }

    @Override
    public void GotoDetail(ProductEx productEx) {
        dialogUtils.disMiss();
        if(productEx!=null){
            ProductActivity.product = productEx;
            startActivity(new Intent(getApplicationContext(), ProductActivity.class));
        }
    }
}
