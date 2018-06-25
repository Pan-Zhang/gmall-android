package com.guotai.mall.activity.complaintDetail;

import com.guotai.mall.model.Complaint;

/**
 * Created by zhangpan on 2018/6/25.
 */

public interface IComplaintDetailActivity {

    void uploadRes(Boolean suucess);
    void updateComplaint(Boolean success, Complaint complaint);
}
