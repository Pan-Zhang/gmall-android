package com.guotai.mall.activity.suggest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;

/**
 * Created by ez on 2017/6/20.
 */

public class SuggestActivity extends BaseActivity<SuggestPresent> implements ISuggestactivity, View.OnClickListener{

    TextView advice_tv, advice_line, complain_tv, complain_line;
    EditText advice_et, complain_et, email;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        present = new SuggestPresent(this);
        initTitle();
        init();
    }

    private void init() {
        advice_tv = (TextView) findViewById(R.id.advice_tv);
        advice_tv.setOnClickListener(this);
        advice_line = (TextView) findViewById(R.id.advice_line);
        complain_tv = (TextView) findViewById(R.id.complain_tv);
        complain_tv.setOnClickListener(this);
        complain_line = (TextView) findViewById(R.id.complain_line);
        advice_et = (EditText) findViewById(R.id.advice_et);
        complain_et = (EditText) findViewById(R.id.complain_et);
        email = (EditText) findViewById(R.id.email_tv);
        submit = (Button) findViewById(R.id.submit);
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
        title.setText(R.string.str_suggest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.advice_tv:
                advice_tv.setTextColor(getResources().getColor(R.color.colorApp));
                advice_line.setBackgroundColor(getResources().getColor(R.color.colorApp));
                complain_tv.setTextColor(getResources().getColor(R.color.colorTextBlack));
                complain_line.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                advice_et.setVisibility(View.VISIBLE);
                complain_et.setVisibility(View.INVISIBLE);
                break;

            case R.id.complain_tv:
                advice_tv.setTextColor(getResources().getColor(R.color.colorTextBlack));
                advice_line.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                complain_tv.setTextColor(getResources().getColor(R.color.colorApp));
                complain_line.setBackgroundColor(getResources().getColor(R.color.colorApp));
                advice_et.setVisibility(View.INVISIBLE);
                complain_et.setVisibility(View.VISIBLE);
                break;
        }
    }
}
