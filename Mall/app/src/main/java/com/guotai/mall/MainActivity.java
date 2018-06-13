package com.guotai.mall;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.guotai.mall.Adapter.TabGridAdapter;
import com.guotai.mall.Interface.RereshNumListener;
import com.guotai.mall.base.BaseActivity;
import com.guotai.mall.fragment.buycar.BuyCarFragment;
import com.guotai.mall.fragment.category.CategoryFragment;
import com.guotai.mall.fragment.home.HomeFragment;
import com.guotai.mall.fragment.me.MeFragment;
import com.guotai.mall.model.PersonInfo;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.uitl.HttpFactory;
import com.guotai.mall.uitl.ResultBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class MainActivity extends BaseActivity {

    GridView bottom_tab;
    TabGridAdapter tabGridAdapter;
    HomeFragment homeFragment;
    CategoryFragment categoryFragment;
    BuyCarFragment buyCarFragment;
    MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        updatePersonInfo();

//        if(!TextUtils.isEmpty(Common.getToken()) && !TextUtils.isEmpty(Common.getRefreshToken())){
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("grant_type", "refresh_token");
//            map.put("refresh_token", Common.getRefreshToken());
//            HttpFactory.getInstance().AsyncPost("token", map, new ResultBack() {
//                @Override
//                public void onFailure(Call call, String e) {
//                    Log.e("zpzpaaa", e);
//                }
//
//                @Override
//                public void onResponse(Call call, String response) {
//                    try {
//                        Log.d("zp", response);
//                        JSONObject jsonObject = new JSONObject(response);
//                        if(jsonObject.has("access_token")){
//                            String access_token = jsonObject.getString("access_token");
//                            String refresh_token = jsonObject.getString("refresh_token");
//                            String expires = jsonObject.getString(".expires");
//                            String userID = jsonObject.getString("UserID");
//                            String userName = jsonObject.getString("UserName");
//                            String mobile = jsonObject.getString("Mobile");
//                            String gender = jsonObject.getString("Gender");
//                            String birthday = jsonObject.getString("Birthday");
//                            Common.saveToken(access_token);
//                            Common.saveRefreshToken(refresh_token);
//                            Common.saveExpire(expires);
//                            Common.saveUser(userName);
//                            Common.saveUserID(userID);
//                            Common.saveMobile(mobile);
//                            Common.saveGender(gender);
//                            Common.saveBirthday(birthday);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, MainActivity.this.getClass().getSimpleName());
//        }
    }

    private void updatePersonInfo(){
        if(!TextUtils.isEmpty(Common.getUserID())){
            HttpFactory.getInstance().AsyncGet("api/Users/GetUserInfoByID?UserID="+Common.getUserID(), new ResultBack() {
                @Override
                public void onFailure(Call call, String e) {

                }

                @Override
                public void onResponse(Call call, String response) {
                    PersonInfo personInfo = Common.parseJsonWithGson(response, PersonInfo.class);
                    Common.saveUser(personInfo.UserName==null?"":personInfo.UserName);
                    Common.saveMobile(personInfo.Mobile==null?"":personInfo.Mobile);
                    Common.saveGender(personInfo.Gender==null?"":personInfo.Gender);
                    Common.saveBirthday(personInfo.Birthday==null?"":personInfo.Birthday);
                }
            }, getClass().getSimpleName());
        }
    }

    private void init() {

        bottom_tab = (GridView) findViewById(R.id.bottom_tab);
        tabGridAdapter = new TabGridAdapter(this);
        bottom_tab.setAdapter(tabGridAdapter);

        initHome();

        bottom_tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                tabGridAdapter.pos = position;
                tabGridAdapter.notifyDataSetChanged();
                switch (position){
                    case 0:
                        initHome();
                        break;

                    case 1:
                        initCategory();
                        break;

                    case 2:
                        initCar();
                        break;

                    case 3:
                        initMe();
                        break;
                }
            }
        });
    }

    private void initHome(){
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(homeFragment == null){
            homeFragment = new HomeFragment();
            homeFragment.setGoCategory(new HomeFragment.GoCategory() {
                @Override
                public void Click() {
                    bottom_tab.performItemClick(bottom_tab.getChildAt(1), 1, 1);
                }
            });
            transaction.add(R.id.fragment, homeFragment);
        }
        hideFragment(transaction);
        transaction.show(homeFragment);

        transaction.commit();
    }

    private void initCategory(){
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(categoryFragment == null){
            categoryFragment = new CategoryFragment();
            transaction.add(R.id.fragment, categoryFragment);
        }
        hideFragment(transaction);
        transaction.show(categoryFragment);

        transaction.commit();
    }

    private void initCar(){
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(buyCarFragment == null){
            buyCarFragment = new BuyCarFragment();
            buyCarFragment.setRereshNumListener(new RereshNumListener() {
                @Override
                public void refreshNum() {
                    tabGridAdapter.notifyDataSetChanged();
                }
            });
            transaction.add(R.id.fragment, buyCarFragment);
        }
        hideFragment(transaction);
        transaction.show(buyCarFragment);

        transaction.commit();
    }

    private void initMe(){
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(meFragment == null){
            meFragment = new MeFragment();
            transaction.add(R.id.fragment, meFragment);
        }
        hideFragment(transaction);
        transaction.show(meFragment);

        transaction.commit();
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(homeFragment != null){
            transaction.hide(homeFragment);
        }
        if(categoryFragment != null){
            transaction.hide(categoryFragment);
        }
        if(buyCarFragment != null){
            transaction.hide(buyCarFragment);
        }
        if(meFragment != null){
            transaction.hide(meFragment);
        }
    }

    @Override
    protected void onResume() {
        if(MyApplication.getInstance().goCar==1){
            bottom_tab.performItemClick(bottom_tab, 2, 2);
            MyApplication.getInstance().goCar = 0;
        }
        tabGridAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
