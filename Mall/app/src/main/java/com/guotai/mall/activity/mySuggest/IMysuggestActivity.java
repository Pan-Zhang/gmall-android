package com.guotai.mall.activity.mySuggest;

import com.guotai.mall.model.Complaint;
import com.guotai.mall.model.Suggestion;

import java.util.List;

/**
 * Created by zhangpan on 2018/6/22.
 */

public interface IMysuggestActivity {

    void updateSuggest(boolean success, List<Suggestion> list, String mess);
    void updateComplaint(boolean success, List<Complaint> list, String mess);
    void gotoSuggestDetail(boolean success, Suggestion suggestion);
    void gotoComplaint(boolean success, Complaint complaint);
}
