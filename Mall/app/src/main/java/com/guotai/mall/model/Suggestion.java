package com.guotai.mall.model;

import com.guotai.mall.uitl.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpan on 2018/6/25.
 */

public class Suggestion {

    public int SuggestionID;
    public int UserID;
    public int SuggestionTypeID;
    public String Content;
    public String CreateTime;
    public int SuggestionCount;
    public int ReplyFlag;
    public int ReplyCount;
    public int LastReplySrc;
    public String LastReplyContent;
    public String LastReplyTime;
    public int IsDelete;
    public List<Suggestion.SuggestionImage> SuggestionImages;
    public List<Suggestion.SuggestionDetail> SuggestionDetails;

    public static class SuggestionDetail{
        public int DetailID;
        public int SuggestionID;
        public int ReplySrc;
        public int UserID;
        public int MangerID;
        public String Content;
        public String CreateTime;
        public int IsDelete;
    }

    public static class SuggestionImage{
        public int ImageID;
        public int SuggestionID;
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
        for(int i=0; i<SuggestionImages.size(); i++){
            list.add(SuggestionImages.get(i).ImagePath);
        }
        return list;
    }

    public String getReply(){
        return LastReplyContent;
    }
}
