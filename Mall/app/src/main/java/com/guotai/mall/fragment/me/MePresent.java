package com.guotai.mall.fragment.me;

import com.guotai.mall.activity.cropImage.CropImageActivity;
import com.guotai.mall.base.IBasePresent;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by ez on 2017/6/26.
 */

public class MePresent implements IBasePresent {

    IMefragment iMefragment;

    public MePresent(IMefragment iMefragment){
        this.iMefragment = iMefragment;
    }

    public void uploadAvatar(String url, Map<String, String> map, Map<String, String> map_file, String tag){
        HttpFactory.getInstance().UploadImage(url, map, map_file, new ResultBack() {
            @Override
            public void onFailure(Call call, String e) {
                if(iMefragment!=null){
                    iMefragment.uploadSucce(false);
                }
            }

            @Override
            public void onResponse(Call call, String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if(jsonObject.has("HeadImage")) {
                        String avatar = jsonObject.getString("HeadImage");
                        Common.saveAvatar(avatar);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(iMefragment!=null){
                    iMefragment.uploadSucce(true);
                }
            }
        }, tag);
    }

    @Override
    public void destroy() {

    }
}
