package com.guotai.mall.activity.complaintDetail;

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

import com.guotai.mall.Adapter.ComplaintDetailAdapter;
import com.guotai.mall.Adapter.SuggestDetailAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.suggestDetail.ISuggestDetail;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Complaint;
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

public class ComplaintDetailAcvitity extends BaseActivity<ComplaintDetailPresent> implements IComplaintDetailActivity{

    public static Complaint complaint;
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
    ComplaintDetailAdapter complaintDetailAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_detail);

        present = new ComplaintDetailPresent(this);
        initTitle();
        initView();
        
    }

    private void initView() {
        avatar = (ImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        undeal = (ImageView) findViewById(R.id.undeal);
        if (complaint.getStatus() == 0) {
            undeal.setBackgroundResource(R.mipmap.undeal);
        } else {
            undeal.setBackgroundResource(R.mipmap.dealed);
        }
        content = (TextView) findViewById(R.id.content);
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
        if(complaint.ComplaintDetails==null || complaint.ComplaintDetails.size()==0){
            reply_ll.setVisibility(View.GONE);
        }

        complaintDetailAdapter = new ComplaintDetailAdapter(this, complaint.ComplaintDetails);
        reply_lv.setAdapter(complaintDetailAdapter);

        name.setText(complaint.getName());
        if(!TextUtils.isEmpty(complaint.getAvatar())){
            Picasso.with(this).load(complaint.getAvatar()).resize(200, 200).transform(new CircleTransform(this)).centerCrop().into(avatar);
        }
        time.setText(complaint.getTime());
        content.setText(complaint.getContent());
        if(complaint.getImages()==null || complaint.getImages().size()==0){
            images.setVisibility(View.GONE);
        }
        else{
            images.setVisibility(View.VISIBLE);
            for(int i = 0; i< complaint.getImages().size(); i++){
                Picasso.with(this).load(complaint.getImages().get(i)).resize(200, 200).centerCrop().into(imageViews[i]);
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
                    map.put("ComplaintID", String.valueOf(complaint.ComplaintID));
                    map.put("Content", suggest_answer.getText().toString());
                    present.uploadComplaint("api/Complaint/AddComplaintDetail", map, ComplaintDetailAcvitity.this.getClass().getSimpleName());
                }
                else{
                    Common.showToastShort("请输入您的投诉");
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
        title.setText(ComplaintDetailAcvitity.title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        complaint = null;
    }

    @Override
    public void uploadRes(Boolean suucess) {
        if(suucess){
            present.getComplaintDetail("api/Complaint/GetComplaintDetail?ComplaintID="+complaint.ComplaintID+"&UserID="+Common.getUserID(), ComplaintDetailAcvitity.this.getClass().getSimpleName());
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
    public void updateComplaint(Boolean success, Complaint complaint) {
        if(success){
            this.complaint = complaint;
            complaintDetailAdapter.update(complaint.ComplaintDetails);
        }
        else{

        }
    }
}
