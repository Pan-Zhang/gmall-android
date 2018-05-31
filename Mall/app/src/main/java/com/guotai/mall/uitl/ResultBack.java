package com.guotai.mall.uitl;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ez on 2017/4/25.
 */

public interface ResultBack {

    void onFailure(Call call, String e);

    void onResponse(Call call, String response);
}
