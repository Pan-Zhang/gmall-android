package com.guotai.mall.activity.systemMsg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.SystemMsgAdapter;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.SystemMsg;

import java.util.List;

public class SystemMsgActivity extends BaseActivity implements ISystemMsgActivity {

    public static List<SystemMsg> list;
    ListView listView;
    SystemMsgAdapter systemMsgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_msg);

        initTitle();

        listView = (ListView) findViewById(R.id.list);
        systemMsgAdapter = new SystemMsgAdapter(this, list);
        listView.setAdapter(systemMsgAdapter);
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
        title.setText("系统消息");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list = null;
    }
}
































