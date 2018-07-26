package com.guotai.mall.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.MessAdapter;
import com.guotai.mall.Adapter.SaleAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.orderMsg.OrderMsgActivity;
import com.guotai.mall.activity.sale.SaleActivity;
import com.guotai.mall.activity.systemMsg.SystemMsgActivity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Message;
import com.guotai.mall.model.OrderMsg;
import com.guotai.mall.model.Sale;
import com.guotai.mall.model.SystemMsg;
import com.guotai.mall.uitl.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpan on 17/6/28.
 */

public class MessageActivity extends BaseActivity<MessagePresent> implements IMessageactivity {

    ListView mess_lv;
    MessAdapter messAdapter;
    List<Message> list;
    String url = "api/NotifyMsg/GetNotifyMsgTypeList?UserID=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        present = new MessagePresent(this);

        initTitle();
        mess_lv = (ListView) findViewById(R.id.mess_lv);
        list = new ArrayList<Message>();
        messAdapter = new MessAdapter(this, list);
        mess_lv.setAdapter(messAdapter);
        mess_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogUtils.showWaitDialog(MessageActivity.this);
                Message message = list.get(position);
                if(message.MsgTypeID==1){
                    present.getSysMsg("api/NotifyMsg/GetSysNotifyMsgList", MessageActivity.this.getClass().getSimpleName());
                }
                else if(message.MsgTypeID==2){
                    present.getSale("api/NotifyMsg/GetPromotionPage?UserID="+Common.getUserID()+"&idxPage=1&sizePage=20", MessageActivity.this.getClass().getSimpleName());
                }
                else if(message.MsgTypeID==3){
                    present.getOrders("api/NotifyMsg/GetOrderMsgPage?UserID="+Common.getUserID()+"&idxPage=1&sizePage=20", MessageActivity.this.getClass().getSimpleName());
                }
            }
        });
        present.getData(getClass().getSimpleName(), url + (TextUtils.isEmpty(Common.getUserID())?"0":Common.getUserID()));
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
        title.setText(R.string.str_message);
    }

    @Override
    public void refresh(List<Message> list, boolean Sucess) {
        if(Sucess){
            this.list = list;
            messAdapter.update(list);
        }
        else{
            Common.showToastShort("加载失败");
        }
    }

    @Override
    public void gotoSale(List<Sale> sales, boolean sucess) {
        dialogUtils.disMiss();
        if(sucess){
            SaleActivity.list = sales;
            startActivity(new Intent(this, SaleActivity.class));
        }
        else{
            Common.showToastShort("获取数据失败");
        }
    }

    @Override
    public void getOrders(List<OrderMsg> list, boolean success) {
        dialogUtils.disMiss();
        if(success){
            OrderMsgActivity.list = list;
            startActivity(new Intent(this, OrderMsgActivity.class));
        }
        else{
            Common.showToastShort("获取数据失败");
        }
    }

    @Override
    public void getSystemMsg(List<SystemMsg> list, boolean success) {
        dialogUtils.disMiss();
        if(success){
            SystemMsgActivity.list = list;
            startActivity(new Intent(this, SystemMsgActivity.class));
        }
        else{
            Common.showToastShort("获取数据失败");
        }
    }
}
