package com.guotai.mall.activity.suggestDetail;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.SuggestDetailAdapter;
import com.guotai.mall.R;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Suggestion;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangpan on 2018/6/22.
 */

public class SuggestDetailAcvitity extends BaseActivity<SuggestDetailPresent> implements ISuggestDetail{

    public static Suggestion suggestion;
    public static String title;
    public ImageView avatar;
    public TextView name;
    public TextView time;
    public ImageView undeal;
    public TextView content;
    public HorizontalScrollView images;
    public ImageView imag1, imag2, imag3, imag4, imag5;
    public ImageView[] imageViews;
    public ListView reply_lv;
    public LinearLayout reply_ll;
    public EditText suggest_answer;
    public Button suggest_submit;
    SuggestDetailAdapter suggestDetailAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_detail);

        present = new SuggestDetailPresent(this);
        initTitle();
        initView();
        
    }

    private void initView() {
        avatar = (ImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        content = (TextView) findViewById(R.id.content);
        undeal = (ImageView) findViewById(R.id.undeal);
        if (suggestion.getStatus() == 0) {
            undeal.setBackgroundResource(R.mipmap.undeal);
        }
        images = (HorizontalScrollView) findViewById(R.id.images);
        imageViews = new ImageView[5];
        imag1 = (ImageView) findViewById(R.id.imag1);
        imageViews[0] = imag1;
        imag2 = (ImageView) findViewById(R.id.imag2);
        imageViews[1] = imag2;
        imag3 = (ImageView) findViewById(R.id.imag3);
        imageViews[2] = imag3;
        imag4 = (ImageView) findViewById(R.id.imag4);
        imageViews[3] = imag4;
        imag5 = (ImageView) findViewById(R.id.imag5);
        imageViews[4] = imag5;

        reply_lv = (ListView) findViewById(R.id.reply_lv);

        reply_ll = (LinearLayout) findViewById(R.id.reply_ll);
        if(suggestion.SuggestionDetails==null || suggestion.SuggestionDetails.size()==0){
            reply_ll.setVisibility(View.GONE);
        }

        suggestDetailAdapter = new SuggestDetailAdapter(this, suggestion.SuggestionDetails);
        reply_lv.setAdapter(suggestDetailAdapter);

        name.setText(suggestion.getName());
        if(!TextUtils.isEmpty(suggestion.getAvatar())){
            Picasso.with(this).load(suggestion.getAvatar()).resize(200, 200).transform(new CircleTransform(this)).centerCrop().into(avatar);
        }
        time.setText(suggestion.getTime());
        content.setText(suggestion.getContent());
        if(suggestion.getImages()==null || suggestion.getImages().size()==0){
            images.setVisibility(View.GONE);
        }
        else{
            images.setVisibility(View.VISIBLE);
            for(int i = 0; i< suggestion.getImages().size(); i++){
                Picasso.with(this).load(suggestion.getImages().get(i)).resize(200, 200).centerCrop().into(imageViews[i]);
            }
        }

        suggest_answer = (EditText) findViewById(R.id.suggest_answer);

        suggest_submit = (Button) findViewById(R.id.suggest_submit);
        suggest_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(suggest_answer.getText())){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("UserID", Common.getUserID());
                    map.put("SuggestionID", String.valueOf(suggestion.SuggestionID));
                    map.put("Content", suggest_answer.getText().toString());
                    present.uploadSuggest("api/Suggestion/AddSuggestionDetail", map, SuggestDetailAcvitity.this.getClass().getSimpleName());
                }
                else{
                    Common.showToastShort("请输入您的建议");
                }
            }
        });
    }

    public void initTitle(){
        ImageView left_iv = (ImageView) findViewById(R.id.left_iv);
        left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        left_iv.setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(SuggestDetailAcvitity.title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        suggestion = null;
    }

    @Override
    public void uploadRes(boolean success) {
        if(success){
            present.getSuggestDetail("api/Suggestion/GetSuggestionDetail?SuggestionID="+suggestion.SuggestionID+"&UserID="+Common.getUserID(), SuggestDetailAcvitity.this.getClass().getSimpleName());
            suggest_answer.setText("");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            Common.showToastShort("提交成功");
        }
        else{
            Common.showToastShort("提交失败");
        }
    }

    @Override
    public void updateSuggestDetail(boolean success, Suggestion suggestion) {
        if(success){
            this.suggestion = suggestion;
            suggestDetailAdapter.update(suggestion.SuggestionDetails);
        }
        else{

        }
    }
}
