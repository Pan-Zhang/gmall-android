package com.guotai.mall.activity.help;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;

/**
 * Created by zhangpan on 17/6/27.
 */

public class HelpActivity extends BaseActivity<HelpPresent> implements IHelpactivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        present = new HelpPresent(this);

        initTitle();

        init();
    }

    private void init() {

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
        title.setText(R.string.str_helpcenter);
    }
}
