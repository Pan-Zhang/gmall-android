package com.guotai.mall.fragment.myOrder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.guotai.mall.Adapter.OrderAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.makeOrder.MakeOrderActivity;
import com.guotai.mall.activity.orderDetail.OrderDetailActivity;
import com.guotai.mall.activity.returnGood.ReturnGoodActivity;
import com.guotai.mall.activity.returngoodres.ReturnGoodResActivity;
import com.guotai.mall.base.BaseFragment;
import com.guotai.mall.model.Address;
import com.guotai.mall.model.AfterSale;
import com.guotai.mall.model.CarPro;
import com.guotai.mall.model.Logistics;
import com.guotai.mall.model.OrderDetailEx;
import com.guotai.mall.model.OrderEx;
import com.guotai.mall.uitl.Common;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpan on 17/7/26.
 */

public class MyOrderFragment extends BaseFragment<MyOrderPresent> implements IMyOrderfragment {

    public int position;
    OrderAdapter orderAdapter;
    List<OrderEx> list;
    String url;
    PullToRefreshListView order_ls;
    int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myorder, container, false);
        present = new MyOrderPresent(this);

        getUrl();

        order_ls = (PullToRefreshListView) rootView.findViewById(R.id.order_ls);
        order_ls.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getUrl();
                present.GetData(url, getClass().getSimpleName());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = page + 1;
                getUrl();
                present.GetMoreData(url, getClass().getSimpleName());
            }
        });

        list = new ArrayList<OrderEx>();
        orderAdapter = new OrderAdapter(getContext(), list, position);
        orderAdapter.setOnClickButton(new OrderAdapter.OnClickButton() {
            @Override
            public void ClickButton(View button) {
                dialogUtils.showWaitDialog(getContext());
                int positon = (int)button.getTag();
                int type = (int)button.getTag(R.id.tag_order);
                operateButton(type, positon);
            }

            @Override
            public void BackExchangeClickListener(OrderDetailEx orderDetailEx) {
                dialogUtils.showWaitDialog(getContext());
                if( position==4 || (position==3 && orderDetailEx.IsAfterSale==0)){//退换货
                    present.getSubDetail("api/Order/GetSubOrderDetail?UserID=" + Common.getUserID() + "&OrderID=" + orderDetailEx.OrderID + "&OrderSubID=" + orderDetailEx.OrderSubID, MyOrderFragment.class.getSimpleName());
                }
                else{//跳转售后详情页面
                    present.getAfterSaleDetail("api/Order/GetAfterDetail?UserID=" + Common.getUserID() + "&OrderID=" + orderDetailEx.OrderID + "&OrderSubID=" + orderDetailEx.OrderSubID, MyOrderFragment.class.getSimpleName());
                }
            }
        });
        order_ls.setAdapter(orderAdapter);
        present.GetData(url, getClass().getSimpleName());
        return rootView;
    }

    public void operateButton(int type, int position){
        switch (type){
            case OrderAdapter.CANCEL_ORDER:
                Map<String, String> map = new HashMap<String, String>();
                map.put("UserID", Common.getUserID());
                map.put("OrderID", list.get(position).OrderID);
                present.cancelOrder("api/Order/CancelOrder", map, getClass().getSimpleName());
                break;

            case OrderAdapter.BUY_AGAIN:
                present.getAddress("api/UserReceiver/GetUserReceiverList?UserID="+Common.getUserID(), position, MyOrderFragment.this.getClass().getSimpleName());
                break;

            case OrderAdapter.ENSURE_RECEIVE:
                Map<String, String> map1 = new HashMap<>();
                map1.put("UserID", Common.getUserID());
                map1.put("OrderID", list.get(position).OrderID);
                present.ensureReceive("api/Order/ReceivedOrder", map1, getClass().getSimpleName());
                break;

            case OrderAdapter.DELETE_ORDER:
                Map<String, String> map2 = new HashMap<>();
                map2.put("UserID", Common.getUserID());
                map2.put("OrderIDS", "[" + list.get(position).OrderID + "]");
                present.deleteOrder("api/Order/DeleteOrders", map2, getClass().getSimpleName());
                break;

            case OrderAdapter.REQUEST_BACK:
            case OrderAdapter.DETAIL:
            case OrderAdapter.PAY_NOW:
                OrderEx orderEx = list.get(position);
                present.getDetail("api/Order/GetOrderDetail?UserID=" + Common.getUserID() + "&OrderID=" + orderEx.OrderID, getActivity().getClass().getSimpleName());
//                present.getLogistics("api/Order/QueryLogistics?OrderID=18", position, getActivity().getClass().getSimpleName());
                break;
        }
    }

    @Override
    public void Refresh(List<OrderEx> list) {
        this.list = list;
        order_ls.onRefreshComplete();
        if(list!=null){
            orderAdapter.update(list);
        }
    }

    @Override
    public void RefreshMoreData(List<OrderEx> list) {
        order_ls.onRefreshComplete();
        if(list!=null){
            this.list.addAll(list);
            orderAdapter.update(this.list);
        }
        else{
            page = page - 1;
        }
    }

    @Override
    public void cancelSuccess(Boolean success) {
        dialogUtils.disMiss();
        if(success){
            Common.showToastShort("取消订单成功");
            present.GetData(url, getClass().getSimpleName());
        }
        else{
            Common.showToastShort("取消订单失败");
        }
    }

    @Override
    public void ensureReceive(boolean success) {
        dialogUtils.disMiss();
        if(success){
            Common.showToastShort("确认接收成功");
            present.GetData(url, getClass().getSimpleName());
        }
        else{
            Common.showToastShort("确认接收失败");
        }
    }

    @Override
    public void deleteOrder(boolean success) {
        dialogUtils.disMiss();
        if(success){
            Common.showToastShort("删除订单成功");
            present.GetData(url, getClass().getSimpleName());
        }
        else{
            Common.showToastShort("删除订单失败");
        }
    }

    @Override
    public void getLogistics(boolean success, Logistics logistics, int position, OrderEx orderEx) {
        if(success){
            OrderDetailActivity.detail = orderEx;
            OrderDetailActivity.logistics = logistics;
            Intent intent = new Intent(getContext(), OrderDetailActivity.class);
            intent.putExtra("type", this.position);
            startActivity(intent);
        }
        else{
            OrderDetailActivity.detail = orderEx;
            Logistics logistics1 = new Logistics();
            logistics1.Reason = "无相关物流信息";
            OrderDetailActivity.logistics = logistics;
            Intent intent = new Intent(getContext(), OrderDetailActivity.class);
            intent.putExtra("type", this.position);
            startActivity(intent);
        }
    }

    @Override
    public void getDetail(boolean success, OrderEx orderEx) {
        dialogUtils.disMiss();
        if(success){
            present.getLogistics("api/Order/QueryLogistics?OrderID=" + orderEx.OrderID, orderEx, position, getActivity().getClass().getSimpleName());
        }
        else{
            Common.showToastLong(R.string.str_getdata_failed);
        }
    }

    @Override
    public void getSubDetail(boolean success, OrderDetailEx orderDetailEx) {
        dialogUtils.disMiss();
        if(success){
            OrderEx orderEx = new OrderEx();
            orderEx.OrderID = orderDetailEx.OrderID;
            orderEx.OrderSN = orderDetailEx.OrderSN;
            orderEx.TotalQty = String.valueOf(orderDetailEx.Qty);
            orderEx.OrderDetailList = new ArrayList<>();
            orderEx.OrderDetailList.add(orderDetailEx);
            orderEx.IsReturnExpired = orderDetailEx.IsReturnExpired;
            orderEx.IsExchangeExpired = orderDetailEx.IsExchangeExpired;
            if(orderEx.IsReturnExpired==1 && orderEx.IsExchangeExpired==1){
                Common.showToastShort("该商品已经过了售后期限");
            }
            else{
                ReturnGoodActivity.orderEx = orderEx;
                startActivity(new Intent(getContext(), ReturnGoodActivity.class));
            }
        }
        else{
            Common.showToastLong(R.string.str_getdata_failed);
        }
    }

    @Override
    public void updateAddress(List<Address> list, int position) {
        dialogUtils.disMiss();
        if(list==null){
            Common.showToastShort("获取地址失败");
        }
        else{
            List<CarPro> list_pro = new ArrayList<>();
            OrderEx ex = this.list.get(position);
            for(int i=0; i<ex.OrderDetailList.size(); i++){
                CarPro carPro = new CarPro();
                carPro.Qty = ex.OrderDetailList.get(i).Qty;
                carPro.FirstImage = ex.OrderDetailList.get(i).FirstImage;
                carPro.ProductDescription = ex.OrderDetailList.get(i).ProductDescription;
                carPro.ProductPrice = ex.OrderDetailList.get(i).Price;
                carPro.ProductID = ex.OrderDetailList.get(i).ProductID;
                carPro.ProductSubID = ex.OrderDetailList.get(i).ProductSubID;
                carPro.ProductName = ex.OrderDetailList.get(i).ProductName;
                list_pro.add(carPro);
            }
            MakeOrderActivity.list_pro = list_pro;
            MakeOrderActivity.list_address = list;
            startActivity(new Intent(getActivity(), MakeOrderActivity.class));
        }
    }

    @Override
    public void getAfterSaleDetailRes(boolean succ, AfterSale afterSale) {
        dialogUtils.disMiss();
        if(succ){
            ReturnGoodResActivity.afterSale = afterSale;
            startActivity(new Intent(getContext(), ReturnGoodResActivity.class));
        }
        else{
            Common.showToastShort("获取售后详情失败");
        }

    }

    public void getUrl(){
        switch (position){
            case 0:
                url = "api/Order/GetOrderList?idxPage=" + page + "&sizePage=20&UserID=" + Common.getUserID() + "&OrderState=1";
                break;

            case 1:
                url = "api/Order/GetOrderList?idxPage=" + page + "&sizePage=20&UserID=" + Common.getUserID() + "&OrderState=2";
                break;

            case 2:
                url = "api/Order/GetOrderList?idxPage=" + page + "&sizePage=20&UserID=" + Common.getUserID() + "&OrderState=3";
                break;

            case 3:
                url = "api/Order/GetAfterOrderList?idxPage=" + page + "&sizePage=20&UserID=" + Common.getUserID();
                break;

            case 4:
                url = "api/Order/GetOrderList?idxPage=" + page + "&sizePage=20&UserID=" + Common.getUserID() + "&OrderState=4";
                break;

            case 5:
                url = "api/Order/GetOrderList?idxPage=" + page + "&sizePage=20&UserID=" + Common.getUserID() + "&OrderState=5";
                break;
        }
    }
}
