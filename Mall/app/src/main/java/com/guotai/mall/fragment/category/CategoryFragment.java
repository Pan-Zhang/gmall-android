package com.guotai.mall.fragment.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.CategoryAdapter;
import com.guotai.mall.Adapter.SecCategoryAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.CategorySearch.CategorySearchActivity;
import com.guotai.mall.base.BaseFragment;
import com.guotai.mall.model.Category;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.DialogUtils;

import java.util.List;

/**
 * Created by ez on 2017/6/15.
 */

public class CategoryFragment extends BaseFragment<CategoryPresent> implements ICategoryfragment{

    ListView category_lv;
    GridView category_gv;
    CategoryAdapter categoryAdapter;
    SecCategoryAdapter secCategoryAdapter;
    List<Category> list;
    List<Category> sec_list;
    int Position = 0;
    String mytitle;
    String url = "api/Product/GetCategoryList";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        present = new CategoryPresent(this);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(R.string.str_category);
        category_lv = (ListView) rootView.findViewById(R.id.category_lv);
        categoryAdapter = new CategoryAdapter(getContext(), list);
        category_lv.setAdapter(categoryAdapter);

        category_gv = (GridView) rootView.findViewById(R.id.category_gv);
        secCategoryAdapter = new SecCategoryAdapter(getContext(), sec_list);
        category_gv.setAdapter(secCategoryAdapter);


        category_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.get(Position).isSelected = false;
                list.get(position).isSelected = true;
                Position = position;
                categoryAdapter.update(list);
                secCategoryAdapter.update(list.get(position).children);
            }
        });

        category_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogUtils.showWaitDialog(getContext());
                mytitle = list.get(Position).CategoryName + " " + list.get(Position).children.get(position).CategoryName;
                present.search("api/Product/GetProductList?idxPage=1&sizePage=20&cateID="+list.get(Position).children.get(position).CategoryID, CategoryFragment.this.getClass().getSimpleName());
            }
        });
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden && (list==null || list.size()==0)){
            present.loaddata(url, getClass().getSimpleName());
        }
    }

    @Override
    public void refresh(List<Category> list) {
        categoryAdapter.update(list);
        secCategoryAdapter.update(list.get(0).children);
        this.list = list;
    }

    @Override
    public void searchSuccess(List<ProductEx> list, boolean success) {
        dialogUtils.disMiss();
        if(success){
            if(list==null || list.size()==0){
                Common.showToastLong("查询到0条数据");
            }
            else{
                CategorySearchActivity.list_product = list;
                Intent intent = new Intent(getContext(), CategorySearchActivity.class);
                intent.putExtra("title", mytitle);
                startActivity(intent);
            }
        }
        else{
            Common.showToastLong("查询失败！");
        }
    }
}
