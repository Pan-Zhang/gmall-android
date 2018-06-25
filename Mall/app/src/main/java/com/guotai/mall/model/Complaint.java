package com.guotai.mall.model;

import com.guotai.mall.uitl.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpan on 2018/6/22.
 */

public class Complaint {

    public int ComplaintID;
    public int UserID;
    public int ComplaintTypeID;
    public String Content;
    public String CreateTime;
    public int ComplaintCount;
    public int ReplyFlag;
    public int ReplyCount;
    public int LastReplySrc;
    public String LastReplyContent;
    public String LastReplyTime;
    public int IsDelete;
    public List<ComplaintImage> ComplaintImages;
    public List<ComplaintDetail> ComplaintDetails;

    public static class ComplaintDetail{
        public int DetailID;
        public int ComplaintID;
        public int ReplySrc;
        public int UserID;
        public int MangerID;
        public String Content;
        public String CreateTime;
        public int IsDelete;
    }

    public static class ComplaintImage{
        public int ImageID;
        public int ComplaintID;
        public int DetailID;
        public int ImagetypeID;
        public String ImagePath;
    }

    public String getName(){
        return Common.getUser();
    }

    public String getAvatar(){
        return Common.getAvatar();
    }

    public String getTime(){
        return CreateTime;
    }

    public int getStatus(){
        return ReplyFlag;
    }

    public String getContent(){
        return Content;
    }

    public List<String> getImages(){
        List<String> list = new ArrayList<>();
        for(int i=0; i<ComplaintImages.size(); i++){
            list.add(ComplaintImages.get(i).ImagePath);
        }
        return list;
    }

    public String getReply(){
        return LastReplyContent;
    }
}
