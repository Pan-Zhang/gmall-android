package com.guotai.mall.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.guotai.mall.Adapter.HomeAdapter;
import com.guotai.mall.Adapter.HomePagerAdapter;
import com.guotai.mall.R;
import com.guotai.mall.activity.login.LoginActivity;
import com.guotai.mall.activity.message.MessageActivity;
import com.guotai.mall.activity.product.ProductActivity;
import com.guotai.mall.activity.qucode.QrCodeActivity;
import com.guotai.mall.activity.search.SearchActivity;
import com.guotai.mall.base.BaseFragment;
import com.guotai.mall.model.News;
import com.guotai.mall.model.ProductEx;
import com.guotai.mall.uitl.Common;
import com.guotai.mall.widget.ViewPagerIndicator;
import com.guotai.mall.widget.VpSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ez on 2017/6/15.
 */

public class HomeFragment extends BaseFragment<HomePresent> implements IHomeFragment, ViewPager.OnPageChangeListener, View.OnClickListener, AbsListView.OnScrollListener {

    ListView home_lv;
    HomeAdapter homeAdapter;
    VpSwipeRefreshLayout refreshLayout;
    List<ProductEx> list = new ArrayList<ProductEx>();
    ViewPager viewPager;
    ViewPagerIndicator viewPagerIndicator;
    HomePagerAdapter homePagerAdapter;
    List<News> list_news;
    Button scan, message_btn;
    GoCategory goCategory;
    float HALF_WIDTH;
    TextView home_title;
    Button search_btn;
    TextView loadmore;
    int currentIndex = 2;
    LinearLayout head_ll;
    Handler handler = new Handler(new android.os.Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            if(list_news.size()>0){
                int index = viewPager.getCurrentItem()+1;
                viewPager.setCurrentItem(index, true);
            }
            handler.sendEmptyMessageDelayed(0, 5000);
            return false;
        }
    });


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        present = new HomePresent(this);

        HALF_WIDTH = getResources().getDisplayMetrics().widthPixels/2;
        home_title = (TextView) rootView.findViewById(R.id.home_title);
        home_lv = (ListView) rootView.findViewById(R.id.home_lv);
        search_btn = (Button) rootView.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);

        scan = (Button) rootView.findViewById(R.id.scan);
        scan.setOnClickListener(this);

        message_btn = (Button) rootView.findViewById(R.id.message_btn);
        message_btn.setOnClickListener(this);

        head_ll = (LinearLayout) rootView.findViewById(R.id.head_ll);

        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_header, null);
        viewPager = (ViewPager) header.findViewById(R.id.head_pager);
        viewPagerIndicator = (ViewPagerIndicator) header.findViewById(R.id.indicator);
        list_news = new ArrayList<News>();
        homePagerAdapter = new HomePagerAdapter(getContext(), list_news);
        viewPager.setAdapter(homePagerAdapter);
        viewPager.addOnPageChangeListener(this);
        home_lv.addHeaderView(header);

        View footer = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, null);
        loadmore = (TextView) footer.findViewById(R.id.loadmore);
        loadmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadmore.setText("正在加载...");
                present.loaddata("api/Product/GetProductList?idxPage="+currentIndex+"&sizePage=20", true, getClass().getSimpleName());
            }
        });
        home_lv.addFooterView(footer);

        homeAdapter = new HomeAdapter(this.getContext(), list);
        home_lv.setAdapter(homeAdapter);
        home_lv.setOnScrollListener(this);
        present.loadhead("api/Hotspot/GetHotspotList", getClass().getSimpleName());
        present.loaddata("api/Product/GetProductList?idxPage=1&sizePage=20", false, getClass().getSimpleName());
        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener(){

            @Override
            public void OnClick(View view) {
                int position = Integer.parseInt(view.getTag().toString());
                final ProductEx product = list.get(position);
                dialogUtils.showWaitDialog(getContext());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        present.GetDetail(Common.getProductDetailURL(product.ProductID, Common.getUserID()), getClass().getSimpleName());
                    }
                }, 500);
            }
        });
        refreshLayout = (VpSwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(R.color.colorWhite);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressBackgroundColor(R.color.colorApp);
        refreshLayout.setProgressViewEndTarget(true, 200);
        refreshLayout.setVpSwipDraging(new VpSwipeRefreshLayout.VpSwipDraging() {
            @Override
            public void draging(boolean isDraging) {
                if(isDraging){
                    head_ll.setVisibility(View.INVISIBLE);
                }
                else{
                    head_ll.setVisibility(View.VISIBLE);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                present.loadhead("api/Hotspot/GetHotspotList", getClass().getSimpleName());
                currentIndex = 2;
                present.loaddata("api/Product/GetProductList?idxPage=1&sizePage=20", false, getClass().getSimpleName());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        head_ll.setVisibility(View.VISIBLE);
                    }
                }, 10000);
            }
        });
        handler.sendEmptyMessageDelayed(0, 5000);
        return rootView;
    }

    @Override
    public void refresh(List<ProductEx> list, List<News> list_news, boolean more) {
        refreshLayout.setRefreshing(false);
        head_ll.setVisibility(View.VISIBLE);
        loadmore.setText("点击加载更多");
        if(list!=null){
            if(more){
                ++currentIndex;
                this.list.addAll(list);
            }
            else{
                this.list = list;
            }
            homeAdapter.update(this.list);
        }
        if(list_news!=null){
            this.list_news = list_news;
            if(homePagerAdapter==null){
                homePagerAdapter = new HomePagerAdapter(getContext(), list_news);
                viewPager.setAdapter(homePagerAdapter);
            }else{
                homePagerAdapter.update(list_news);
            }
            viewPagerIndicator.setLength(list_news.size());
        }
    }

    @Override
    public void GotoDetail(ProductEx product) {
        dialogUtils.disMiss();
        if(product!=null){
            ProductActivity.product = product;
            startActivity(new Intent(getContext(), ProductActivity.class));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        viewPagerIndicator.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan:
                startActivity(new Intent(getActivity(), QrCodeActivity.class));
                break;

            case R.id.message_btn:
                if(TextUtils.isEmpty(Common.getToken())){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else{
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                }
                break;

            case R.id.search_btn:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }

    }

    public void setGoCategory(GoCategory goCategory){
        this.goCategory = goCategory;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        int y = getScrollY(absListView);

        float alpha = y/HALF_WIDTH;
        if(alpha<0.9){
            home_title.setAlpha(alpha);
        }
        else{
            home_title.setAlpha((float)0.9);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeMessages(0);
    }

    public int getScrollY(AbsListView absListView) {
        View c = absListView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = absListView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }

    public interface GoCategory{
        void Click();
    }

}
