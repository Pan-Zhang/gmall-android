package com.guotai.mall.activity.message;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.MessAdapter;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpan on 17/6/28.
 */

public class MessageActivity extends BaseActivity<MessagePresent> implements IMessageactivity {

    ListView mess_lv;
    MessAdapter messAdapter;
    List<Message> list;
    String url = "https:www.baidu.com";

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
        present.getData(getClass().getSimpleName(), url);
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
    public void refresh(List<Message> list) {
        this.list = list;
        messAdapter.update(list);
    }
}
