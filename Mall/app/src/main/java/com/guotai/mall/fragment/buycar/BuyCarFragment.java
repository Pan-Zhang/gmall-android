package com.guotai.mall.fragment.buycar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.CarAdapter;
import com.guotai.mall.Interface.RereshNumListener;
import com.guotai.mall.MyApplication;
import com.guotai.mall.R;
import com.guotai.mall.activity.addAddress.AddAddressActivty;
import com.guotai.mall.activity.brower.BrowerActivity;
import com.guotai.mall.activity.collection.CollectionActivity;
import com.guotai.mall.activity.login.LoginActivity;
import com.guotai.mall.activity.makeOrder.MakeOrderActivity;
import com.guotai.mall.activity.product.ProductActivity;
import com.guotai.mall.base.BaseFragment;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ez on 2017/6/15.
 */

public class BuyCarFragment extends BaseFragment<BuycarPresent> implements IBuycarfragment, View.OnClickListener
{

    LinearLayout no_product, have_product;
    PullToRefreshListView car_lv;
    CarAdapter carAdapter;
    List<CarPro> list, choose_list;
    CheckBox choose_all;
    TextView delete, pay;
    BigDecimal all_money;
    int all_product, choose_product;
    Button make_order, collect, brower;
    String url = "api/ShopCart/GetShopCartList";
    RereshNumListener rereshNumListener;
    List<CarPro> tem;
    Handler handler = new Handler(new android.os.Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy_car, container, false);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(R.string.str_car);

        present = new BuycarPresent(this);

        list = new ArrayList<CarPro>();
        no_product = (LinearLayout) rootView.findViewById(R.id.no_product);
        no_product.setVisibility(View.GONE);
        collect = (Button) rootView.findViewById(R.id.collect);
        collect.setOnClickListener(this);
        brower = (Button) rootView.findViewById(R.id.brower);
        brower.setOnClickListener(this);
        have_product = (LinearLayout) rootView.findViewById(R.id.have_product);

        choose_all = (CheckBox) rootView.findViewById(R.id.choose_all);
        choose_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    for(int i=0; i<list.size(); i++){
                        CarPro product = list.get(i);
                        product.isChoose = true;
                    }
                }
                else{
                    for(int i=0; i<list.size(); i++){
                        CarPro product = list.get(i);
                        product.isChoose = false;
                    }
                }
                carAdapter.update(list);
                calcute();
            }
        });

        pay = (TextView) rootView.findViewById(R.id.pay);
        delete = (TextView) rootView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String param = "[";
                tem = new ArrayList<CarPro>();
                for(int i=0; i<list.size(); i++){
                    CarPro product = list.get(i);
                    if(!product.isChoose){
                        tem.add(product);
                    }
                    else{
                        param = param+product.ShopCartID+",";
                    }
                }
                if(param.length()==1){
                    param = param +"]";
                }
                else{
                    param = param.substring(0, param.length()-1) + "]";
                }
                if(param.length()==2) {
                    Common.showToastLong("请选择想要删除的商品");
                    return;
                }
                dialogUtils.showWaitDialog(BuyCarFragment.this.getContext(), "正在删除...");

                present.deletePro("api/ShopCart/DeleteShopCart", param, BuyCarFragment.class.getSimpleName());

            }
        });

        car_lv = (PullToRefreshListView) rootView.findViewById(R.id.car_lv);
        list = new ArrayList<CarPro>();
        carAdapter = new CarAdapter(getContext(), list);
        car_lv.setAdapter(carAdapter);
        car_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                present.getData(getClass().getSimpleName(), url);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        carAdapter.setOnClick(new CarAdapter.OnClick() {
            @Override
            public void Add(View view) {
                int pos = (int) view.getTag();
                CarPro product = list.get(pos);
                if(product.Qty<99){
                    String url = "api/ShopCart/UpdateShopCartQty?ShopCartID=" + product.ShopCartID + "&Qty=" + (product.Qty+1);
                    present.updateCount(url, BuyCarFragment.class.getSimpleName(), product, true);
                }
            }

            @Override
            public void Del(View view) {
                int pos = (int) view.getTag();
                CarPro product = list.get(pos);
                if(product.Qty>0){
                    String url = "api/ShopCart/UpdateShopCartQty?ShopCartID=" + product.ShopCartID + "&Qty=" + (product.Qty-1);
                    present.updateCount(url, BuyCarFragment.class.getSimpleName(), product, false);
                }
            }

            @Override
            public void Choose(View view) {
                int pos = (int) view.getTag();
                CarPro product = list.get(pos);
                if(product.isChoose){
                    product.isChoose = false;
                }else {
                    product.isChoose = true;
                }
                carAdapter.update(list);
                calcute();
            }

            @Override
            public void OnItemClick(int position) {
                dialogUtils.showWaitDialog(BuyCarFragment.this.getContext());
                CarPro product = list.get(position);
                present.GetDetail(Common.getProductDetailURL(product.ProductID, Common.getUserID()), getClass().getSimpleName());
            }
        });

        make_order = (Button) rootView.findViewById(R.id.make_order);
        make_order.setOnClickListener(this);

        url = "api/ShopCart/GetShopCartList";
        url = url + "?idxPage=0&sizePage=0&UserID=" + Common.getUserID();
        present.getData(getClass().getSimpleName(), url);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(Common.getToken())){
            calcute();
        }
        else{
            car_lv.setRefreshing(true);
            present.getData(getClass().getSimpleName(), url);
        }

    }

    public void setRereshNumListener(RereshNumListener rereshNumListener){
        this.rereshNumListener = rereshNumListener;
    }

    public void calcute(){
        if(choose_list!=null){
            choose_list.clear();
        }
        else{
            choose_list = new ArrayList<>();
        }
        all_money = new BigDecimal("0");
        choose_product = 0;
        all_product = 0;
        for(int i=0; i<list.size(); i++){
            CarPro product = list.get(i);
            if(product.isChoose) {
                choose_list.add(product);
                BigDecimal b1 = new BigDecimal(product.getProductPrice());
                BigDecimal d = b1.multiply(new BigDecimal(product.Qty));
                all_money = all_money.add(d);
                choose_product = choose_product + product.Qty;
            }
            all_product = all_product + product.Qty;
        }
        if(all_money.floatValue()==0){
            pay.setText("¥0.00");
        }
        else{
            pay.setText("¥"+Common.get2Digital(all_money.floatValue()));
        }
        make_order.setText("去结算(" + String.valueOf(choose_product) + ")");
        if(list==null ||list.size()==0){
            no_product.setVisibility(View.VISIBLE);
            have_product.setVisibility(View.GONE);
        }else{
            no_product.setVisibility(View.GONE);
            have_product.setVisibility(View.VISIBLE);
        }
        MyApplication.getInstance().number = all_product;
        if(rereshNumListener!=null){
            rereshNumListener.refreshNum();
        }
    }

    @Override
    public void refresh(List<CarPro> list) {
        car_lv.onRefreshComplete();
        if(list!=null){
            if(choose_list!=null){
                for(int i=0; i<choose_list.size(); i++){
                    CarPro carPro = choose_list.get(i);
                    for(int j=0; j<list.size(); j++){
                        CarPro tem = list.get(j);
                        if(carPro.ShopCartID.equals(tem.ShopCartID)){
                            tem.isChoose = carPro.isChoose;
                        }
                    }
                }
            }
            carAdapter.update(list);
            this.list = list;
            calcute();
        }
    }

    @Override
    public void updateCount(boolean sucess, CarPro carPro, boolean isAdd) {
        dialogUtils.disMiss();
        if(sucess){
            if(isAdd){
                carPro.Qty = carPro.Qty+1;
            }
            else{
                carPro.Qty = carPro.Qty-1;
            }
            carAdapter.update(list);
            calcute();
        }
    }

    @Override
    public void delete(boolean delSuccess) {
        dialogUtils.disMiss();
        if(delSuccess){
            list = tem;
            tem = null;
            carAdapter.update(list);
            choose_all.setChecked(false);
            calcute();
        }
    }

    @Override
    public void GotoDetail(ProductEx productEx) {
        dialogUtils.disMiss();
        if(productEx!=null){
            ProductActivity.product = productEx;
            startActivity(new Intent(getContext(), ProductActivity.class));
        }
    }

    @Override
    public void updateAddress(List<Address> list_address) {
        dialogUtils.disMiss();
        if(list_address==null){
            Common.showToastShort("获取地址失败");
        }
        else if(list_address.size()==0){
            startActivity(new Intent(getContext(), AddAddressActivty.class));
        }
        else{
            MakeOrderActivity.list_pro = choose_list;
            MakeOrderActivity.list_address = list_address;
            startActivity(new Intent(getActivity(), MakeOrderActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.make_order:
                if(choose_list==null || choose_list.size()==0){
                    Common.showToastShort("请选择商品");
                    return;
                }
                dialogUtils.showWaitDialog(BuyCarFragment.this.getContext());
                present.getAddress("api/UserReceiver/GetUserReceiverList?UserID="+Common.getUserID(), BuyCarFragment.this.getClass().getSimpleName());
                break;

            case R.id.collect:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                else{
                    startActivity(new Intent(getActivity(), CollectionActivity.class));
                }
                break;

            case R.id.brower:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                else{
                    startActivity(new Intent(getActivity(), BrowerActivity.class));
                }
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            present.getData(getClass().getSimpleName(), url);
        }
    }
}
