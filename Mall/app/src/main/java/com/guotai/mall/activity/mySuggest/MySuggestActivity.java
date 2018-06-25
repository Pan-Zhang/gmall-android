package com.guotai.mall.activity.mySuggest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.MyComplaintAdapter;
import com.guotai.mall.Adapter.MySuggestAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.complaintDetail.ComplaintDetailAcvitity;
import com.guotai.mall.activity.suggest.SuggestActivity;
import com.guotai.mall.activity.suggestDetail.SuggestDetailAcvitity;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.model.Complaint;
import com.guotai.mall.model.Suggestion;
import com.guotai.mall.uitl.Common;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpan on 2018/6/22.
 */

public class MySuggestActivity extends BaseActivity<MySuggestPresent> implements IMysuggestActivity {

    PullToRefreshListView complaint_lv, suggest_lv;
    MySuggestAdapter suggestAdapter;
    MyComplaintAdapter myComplaintAdapter;
    List<Complaint> complaintList;
    List<Suggestion> suggestionList;
    LinearLayout complaint_ll, suggest_ll;
    ImageView left_triangle, right_triangle;
    TextView complaint_tv, suggest_tv;
    int suggestPage = 1;
    int complaintPage = 1;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysuggest);

        present = new MySuggestPresent(this);
        initTitle();
        initTab();
        initSuggest();
        initComplaint();

        getComplaint();
        getSuggest();

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MySuggestActivity.this, SuggestActivity.class));
            }
        });
    }

    public void getSuggest(){
        present.getSuggest("api/Suggestion/GetSuggestionList?idxPage=" + suggestPage + "&sizePage=20&UserID="+ Common.getUserID(), this.getClass().getSimpleName());

    }

    public void getComplaint(){
        present.getComplaint("api/Complaint/GetComplaintList?idxPage="+ complaintPage +"&sizePage=20&UserID="+ Common.getUserID(), this.getClass().getSimpleName());
    }

    private void initTab() {
        suggest_ll = (LinearLayout) findViewById(R.id.suggest_ll);
        left_triangle = (ImageView) findViewById(R.id.left_triangle);
        right_triangle = (ImageView) findViewById(R.id.right_triangle);
        suggest_tv = (TextView) findViewById(R.id.suggest_tv);
        complaint_tv = (TextView) findViewById(R.id.complaint_tv);
        suggest_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left_triangle.setVisibility(View.INVISIBLE);
                right_triangle.setVisibility(View.VISIBLE);
                suggest_tv.setTextColor(getResources().getColor(R.color.colorAppBg));
                complaint_tv.setTextColor(getResources().getColor(R.color.colorWhite));
                suggest_lv.setVisibility(View.VISIBLE);
                complaint_lv.setVisibility(View.INVISIBLE);
            }
        });
        complaint_ll = (LinearLayout) findViewById(R.id.complaint_ll);
        complaint_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left_triangle.setVisibility(View.VISIBLE);
                right_triangle.setVisibility(View.INVISIBLE);
                suggest_tv.setTextColor(getResources().getColor(R.color.colorWhite));
                complaint_tv.setTextColor(getResources().getColor(R.color.colorAppBg));
                suggest_lv.setVisibility(View.INVISIBLE);
                complaint_lv.setVisibility(View.VISIBLE);
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
        title.setText("投诉建议");
    }

    public void initSuggest(){
        suggest_lv = (PullToRefreshListView) findViewById(R.id.suggest_lv);
        suggest_lv.setVisibility(View.INVISIBLE);
        suggestionList = new ArrayList<>();
        suggestAdapter = new MySuggestAdapter(this, suggestionList);
        suggest_lv.setAdapter(suggestAdapter);
        suggestAdapter.setOnItemClickListener(new MySuggestAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                Suggestion suggestion = suggestionList.get(position);
                present.getSuggestDetail("api/Suggestion/GetSuggestionDetail?SuggestionID="+suggestion.SuggestionID+"&UserID="+Common.getUserID(), MySuggestActivity.this.getClass().getSimpleName());
            }
        });
        suggest_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                suggestPage = 1;
                suggestionList.clear();
                getSuggest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                suggestPage = suggestPage+1;
                getSuggest();
            }
        });
    }

    public void initComplaint(){
        complaint_lv = (PullToRefreshListView) findViewById(R.id.complaint_lv);
        complaintList = new ArrayList<>();
        myComplaintAdapter = new MyComplaintAdapter(this, complaintList);
        complaint_lv.setAdapter(myComplaintAdapter);
        myComplaintAdapter.setOnItemClickListener(new MySuggestAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                Complaint complaint = complaintList.get(position);
                present.getComplaintDetail("api/Complaint/GetComplaintDetail?ComplaintID="+complaint.ComplaintID+"&UserID="+Common.getUserID(), MySuggestActivity.this.getClass().getSimpleName());
            }
        });
        complaint_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                complaintPage = 1;
                complaintList.clear();
                getComplaint();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                complaintPage = complaintPage + 1;
                getComplaint();
            }
        });
    }

    @Override
    public void updateSuggest(boolean success, List<Suggestion> list, String mess) {
        suggest_lv.onRefreshComplete();
        if(success){//请求成功
            if(list==null || list.size()==0){
                if(suggestPage>1){
                    suggestPage = suggestPage - 1;
                }
            }
            else{
                suggestionList.addAll(list);
                suggestAdapter.update(suggestionList);
            }
        }
        else{//请求失败
            if(suggestPage>1){
                suggestPage = suggestPage - 1;
            }
        }
    }

    @Override
    public void updateComplaint(boolean success, List<Complaint> list, String mess) {
        complaint_lv.onRefreshComplete();
        if(success){
            if(list==null || list.size()==0){
                if(complaintPage>1){
                    complaintPage = complaintPage - 1;
                }
            }
            else{
                complaintList.addAll(list);
                myComplaintAdapter.update(complaintList);
            }
        }
        else{
            if(complaintPage>1){
                complaintPage = complaintPage - 1;
            }
        }

    }

    @Override
    public void gotoSuggestDetail(boolean success, Suggestion suggestion) {
        if(success && suggestion!=null){
            SuggestDetailAcvitity.suggestion = suggestion;
            SuggestDetailAcvitity.title = "建议详情";
            startActivity(new Intent(MySuggestActivity.this, SuggestDetailAcvitity.class));
        }
        else{
            Common.showToastShort("获取数据失败");
        }
    }

    @Override
    public void gotoComplaint(boolean success, Complaint complaint) {
        if(success && complaint!=null){
            ComplaintDetailAcvitity.complaint = complaint;
            ComplaintDetailAcvitity.title = "投诉详情";
            startActivity(new Intent(MySuggestActivity.this, ComplaintDetailAcvitity.class));
        }
        else{
            Common.showToastShort("获取数据失败");
        }
    }
}
